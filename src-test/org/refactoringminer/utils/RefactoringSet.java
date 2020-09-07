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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.refactoringminer.api.RefactoringType;
import org.refactoringminer.utils.RefactoringRelationship_RENAMED.GroupKey;

public class RefactoringSet {

    private final String project;
    private final String revision;
    private final Set<RefactoringRelationship_RENAMED> refactorings;
    private final Map<RefactoringRelationship_RENAMED.GroupKey, Set<RefactoringRelationship_RENAMED>> refactoringGroups;

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

    public Set<RefactoringRelationship_RENAMED> getRefactorings() {
        return refactorings;
    }

    public Set<RefactoringRelationship_RENAMED.GroupKey> getRefactoringsGroups() {
        return refactoringGroups.keySet();
    }

    public RefactoringSet add(RefactoringType type, String entityBefore, String entityAfter) {
        return add(new RefactoringRelationship_RENAMED(type, entityBefore, entityAfter));
    }

    public RefactoringSet add(RefactoringRelationship_RENAMED r) {
        this.refactorings.add(r);
        GroupKey groupKey = r.getGroupKey();
        Set<RefactoringRelationship_RENAMED> group = refactoringGroups.get(groupKey);
        if (group == null) {
            group = new HashSet<>();
            refactoringGroups.put(groupKey, group);
        }
        group.add(r);
        return this;
    }

    public RefactoringSet add(Iterable<RefactoringRelationship_RENAMED> rs) {
        for (RefactoringRelationship_RENAMED r : rs) {
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
            .map(r -> new RefactoringRelationship_RENAMED(r.getRefactoringType(), stripParameters(r.getEntityBefore()), stripParameters(r.getEntityAfter())))
            .collect(Collectors.toList()));
        return newSet;
    }

    private static String stripParameters(String entity) {
        int openPar = entity.indexOf('(');
        return openPar != -1 ? entity.substring(0, openPar + 1) + ")" : entity;
    }

    public void printSourceCode(PrintStream pw) {
        pw.printf("new RefactoringSet(\"%s\", \"%s\")", project, revision);
        for (RefactoringRelationship_RENAMED r : refactorings) {
            pw.printf("\n    .add(RefactoringType.%s, \"%s\", \"%s\")", r.getRefactoringType().toString(), r.getEntityBefore(), r.getEntityAfter());
        }
        pw.println(";");
    }

    public void saveToFile(File file) {
        try (PrintStream pw = new PrintStream(file)) {
            for (RefactoringRelationship_RENAMED r : refactorings) {
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

}
