package org.refactoringminer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringType;
import org.refactoringminer.utils.RefactoringRelationship.GroupKey;

import gr.uom.java.xmi.diff.ExtractOperationRefactoring;
import gr.uom.java.xmi.diff.ExtractSuperclassRefactoring;
import gr.uom.java.xmi.diff.InlineOperationRefactoring;
import gr.uom.java.xmi.diff.MoveAttributeRefactoring;
import gr.uom.java.xmi.diff.MoveClassRefactoring;
import gr.uom.java.xmi.diff.MoveOperationRefactoring;
import gr.uom.java.xmi.diff.RenameClassRefactoring;
import gr.uom.java.xmi.diff.RenameOperationRefactoring;

public class RefactoringSet {

    private final String project;
    private final String revision;
    private final Set<RefactoringRelationship> refactorings;
    private final Map<RefactoringRelationship.GroupKey, Set<RefactoringRelationship>> refactoringGroups;

    public RefactoringSet(String project, String revision) {
        super();
        this.project = project;
        this.revision = revision;
        this.refactorings = new HashSet<>();
        this.refactoringGroups = new HashMap<>();
    }

    public String getProject() {
        return project;
    }

    public String getRevision() {
        return revision;
    }

    public Set<RefactoringRelationship> getRefactorings() {
        return refactorings;
    }

    public Set<RefactoringRelationship.GroupKey> getRefactoringsGroups() {
        return refactoringGroups.keySet();
    }

    public RefactoringSet add(RefactoringType type, String entityBefore, String entityAfter) {
        return add(new RefactoringRelationship(type, entityBefore, entityAfter));
    }

    public RefactoringSet add(RefactoringRelationship r) {
        this.refactorings.add(r);
        GroupKey groupKey = r.getGroupKey();
        Set<RefactoringRelationship> group = refactoringGroups.get(groupKey);
        if (group == null) {
            group = new HashSet<>();
            refactoringGroups.put(groupKey, group);
        }
        group.add(r);
        return this;
    }

    public RefactoringSet add(Iterable<RefactoringRelationship> rs) {
        for (RefactoringRelationship r : rs) {
            this.add(r);
        }
        return this;
    }

    public RefactoringSet ignoring(EnumSet<RefactoringType> refTypes) {
        RefactoringSet newSet = new RefactoringSet(project, revision);
        newSet.add(refactorings.stream()
            .filter(r -> !refTypes.contains(r.getRefactoringType()))
            .collect(Collectors.toList()));
        return newSet;
    }
    public RefactoringSet ignoringMethodParameters(boolean active) {
        if (!active) {
            return this;
        }
        RefactoringSet newSet = new RefactoringSet(project, revision);
        newSet.add(refactorings.stream()
            .map(r -> new RefactoringRelationship(r.getRefactoringType(), stripParameters(r.getEntityBefore()), stripParameters(r.getEntityAfter())))
            .collect(Collectors.toList()));
        return newSet;
    }

    private static String stripParameters(String entity) {
        int openPar = entity.indexOf('(');
        return openPar != -1 ? entity.substring(0, openPar + 1) + ")" : entity;
    }

    public void printSourceCode(PrintStream pw) {
        pw.printf("new RefactoringSet(\"%s\", \"%s\")", project, revision);
        for (RefactoringRelationship r : refactorings) {
            pw.printf("\n    .add(RefactoringType.%s, \"%s\", \"%s\")", r.getRefactoringType().toString(), r.getEntityBefore(), r.getEntityAfter());
        }
        pw.println(";");
    }

    public void saveToFile(File file) {
        try (PrintStream pw = new PrintStream(file)) {
            for (RefactoringRelationship r : refactorings) {
                pw.printf("%s\t%s\t%s\n", r.getRefactoringType().getDisplayName(), r.getEntityBefore(), r.getEntityAfter());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void readFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] array = line.split("\t");
                    RefactoringType refactoringType = RefactoringType.fromName(array[0].trim());
                    String entityBefore = array[1].trim();
                    String entityAfter = array[2].trim();
                    add(refactoringType, entityBefore, entityAfter);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	public void handle(String commitId, List<Refactoring> refactorings) {
	    for (Refactoring r : refactorings) {
	      if (r instanceof MoveClassRefactoring) {
	        MoveClassRefactoring ref = (MoveClassRefactoring) r;
	        add(new RefactoringRelationship(r.getRefactoringType(), ref.getOriginalClassName(), ref.getMovedClassName()));
	      } else if (r instanceof RenameClassRefactoring) {
	        RenameClassRefactoring ref = (RenameClassRefactoring) r;
	        add(new RefactoringRelationship(r.getRefactoringType(), ref.getOriginalClassName(), ref.getRenamedClassName()));
	      } else if (r instanceof ExtractSuperclassRefactoring) {
	        ExtractSuperclassRefactoring ref = (ExtractSuperclassRefactoring) r;
	        for (String subclass : ref.getSubclassSet()) {
	          add(new RefactoringRelationship(r.getRefactoringType(), subclass, ref.getExtractedClass().getName()));
	        }
	      } else if (r instanceof MoveOperationRefactoring) {
	        MoveOperationRefactoring ref = (MoveOperationRefactoring) r;
	        add(new RefactoringRelationship(r.getRefactoringType(), ref.getOriginalOperation().getKey(), ref.getMovedOperation().getKey()));
	      } else if (r instanceof RenameOperationRefactoring) {
	        RenameOperationRefactoring ref = (RenameOperationRefactoring) r;
	        add(new RefactoringRelationship(r.getRefactoringType(), ref.getOriginalOperation().getKey(), ref.getRenamedOperation().getKey()));
	      } else if (r instanceof ExtractOperationRefactoring) {
	        ExtractOperationRefactoring ref = (ExtractOperationRefactoring) r;
	        add(new RefactoringRelationship(r.getRefactoringType(), ref.getSourceOperationBeforeExtraction().getKey(), ref.getExtractedOperation().getKey()));
	      } else if (r instanceof InlineOperationRefactoring) {
	        InlineOperationRefactoring ref = (InlineOperationRefactoring) r;
	        add(new RefactoringRelationship(r.getRefactoringType(), ref.getInlinedOperation().getKey(), ref.getTargetOperationAfterInline().getKey()));
	      } else if (r instanceof MoveAttributeRefactoring) {
	        MoveAttributeRefactoring ref = (MoveAttributeRefactoring) r;
	        String attrName = ref.getMovedAttribute().getName();
	        add(new RefactoringRelationship(r.getRefactoringType(), ref.getSourceClassName() + "#" + attrName, ref.getTargetClassName() + "#" + attrName));
	      } else {
	        throw new RuntimeException("refactoring not supported");
	      }
	    }
	  }

}
