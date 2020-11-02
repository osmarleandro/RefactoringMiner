package org.refactoringminer.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.utils.RefactoringRelationship.GroupKey;

import gr.uom.java.xmi.diff.ExtractOperationRefactoring;
import gr.uom.java.xmi.diff.ExtractSuperclassRefactoring;
import gr.uom.java.xmi.diff.InlineOperationRefactoring;
import gr.uom.java.xmi.diff.MoveAttributeRefactoring;
import gr.uom.java.xmi.diff.MoveClassRefactoring;
import gr.uom.java.xmi.diff.MoveOperationRefactoring;
import gr.uom.java.xmi.diff.RenameClassRefactoring;
import gr.uom.java.xmi.diff.RenameOperationRefactoring;

public class RefactoringCollector extends RefactoringHandler {
  private final RefactoringSet rs;
  private Exception ex = null;
  public RefactoringCollector(String cloneUrl, String commitId) {
    rs = new RefactoringSet(cloneUrl, commitId);
  }
  @Override
  public void handle(String commitId, List<Refactoring> refactorings) {
    for (Refactoring r : refactorings) {
      if (r instanceof MoveClassRefactoring) {
        MoveClassRefactoring ref = (MoveClassRefactoring) r;
		RefactoringRelationship r1 = new RefactoringRelationship(r.getRefactoringType(), ref.getOriginalClassName(), ref.getMovedClassName());
		rs.refactorings.add(r1);
		GroupKey groupKey = r1.getGroupKey();
		Set<RefactoringRelationship> group = rs.refactoringGroups.get(groupKey);
		if (group == null) {
		    group = new HashSet<>();
		    rs.refactoringGroups.put(groupKey, group);
		}
		group.add(r1);
        RefactoringSet add = rs;
      } else if (r instanceof RenameClassRefactoring) {
        RenameClassRefactoring ref = (RenameClassRefactoring) r;
		RefactoringRelationship r1 = new RefactoringRelationship(r.getRefactoringType(), ref.getOriginalClassName(), ref.getRenamedClassName());
		rs.refactorings.add(r1);
		GroupKey groupKey = r1.getGroupKey();
		Set<RefactoringRelationship> group = rs.refactoringGroups.get(groupKey);
		if (group == null) {
		    group = new HashSet<>();
		    rs.refactoringGroups.put(groupKey, group);
		}
		group.add(r1);
        RefactoringSet add = rs;
      } else if (r instanceof ExtractSuperclassRefactoring) {
        ExtractSuperclassRefactoring ref = (ExtractSuperclassRefactoring) r;
        for (String subclass : ref.getSubclassSet()) {
          RefactoringRelationship r1 = new RefactoringRelationship(r.getRefactoringType(), subclass, ref.getExtractedClass().getName());
			rs.refactorings.add(r1);
			GroupKey groupKey = r1.getGroupKey();
			Set<RefactoringRelationship> group = rs.refactoringGroups.get(groupKey);
			if (group == null) {
			    group = new HashSet<>();
			    rs.refactoringGroups.put(groupKey, group);
			}
			group.add(r1);
		RefactoringSet add = rs;
        }
      } else if (r instanceof MoveOperationRefactoring) {
        MoveOperationRefactoring ref = (MoveOperationRefactoring) r;
		RefactoringRelationship r1 = new RefactoringRelationship(r.getRefactoringType(), ref.getOriginalOperation().getKey(), ref.getMovedOperation().getKey());
		rs.refactorings.add(r1);
		GroupKey groupKey = r1.getGroupKey();
		Set<RefactoringRelationship> group = rs.refactoringGroups.get(groupKey);
		if (group == null) {
		    group = new HashSet<>();
		    rs.refactoringGroups.put(groupKey, group);
		}
		group.add(r1);
        RefactoringSet add = rs;
      } else if (r instanceof RenameOperationRefactoring) {
        RenameOperationRefactoring ref = (RenameOperationRefactoring) r;
		RefactoringRelationship r1 = new RefactoringRelationship(r.getRefactoringType(), ref.getOriginalOperation().getKey(), ref.getRenamedOperation().getKey());
		rs.refactorings.add(r1);
		GroupKey groupKey = r1.getGroupKey();
		Set<RefactoringRelationship> group = rs.refactoringGroups.get(groupKey);
		if (group == null) {
		    group = new HashSet<>();
		    rs.refactoringGroups.put(groupKey, group);
		}
		group.add(r1);
        RefactoringSet add = rs;
      } else if (r instanceof ExtractOperationRefactoring) {
        ExtractOperationRefactoring ref = (ExtractOperationRefactoring) r;
		RefactoringRelationship r1 = new RefactoringRelationship(r.getRefactoringType(), ref.getSourceOperationBeforeExtraction().getKey(), ref.getExtractedOperation().getKey());
		rs.refactorings.add(r1);
		GroupKey groupKey = r1.getGroupKey();
		Set<RefactoringRelationship> group = rs.refactoringGroups.get(groupKey);
		if (group == null) {
		    group = new HashSet<>();
		    rs.refactoringGroups.put(groupKey, group);
		}
		group.add(r1);
        RefactoringSet add = rs;
      } else if (r instanceof InlineOperationRefactoring) {
        InlineOperationRefactoring ref = (InlineOperationRefactoring) r;
		RefactoringRelationship r1 = new RefactoringRelationship(r.getRefactoringType(), ref.getInlinedOperation().getKey(), ref.getTargetOperationAfterInline().getKey());
		rs.refactorings.add(r1);
		GroupKey groupKey = r1.getGroupKey();
		Set<RefactoringRelationship> group = rs.refactoringGroups.get(groupKey);
		if (group == null) {
		    group = new HashSet<>();
		    rs.refactoringGroups.put(groupKey, group);
		}
		group.add(r1);
        RefactoringSet add = rs;
      } else if (r instanceof MoveAttributeRefactoring) {
        MoveAttributeRefactoring ref = (MoveAttributeRefactoring) r;
        String attrName = ref.getMovedAttribute().getName();
		RefactoringRelationship r1 = new RefactoringRelationship(r.getRefactoringType(), ref.getSourceClassName() + "#" + attrName, ref.getTargetClassName() + "#" + attrName);
		rs.refactorings.add(r1);
		GroupKey groupKey = r1.getGroupKey();
		Set<RefactoringRelationship> group = rs.refactoringGroups.get(groupKey);
		if (group == null) {
		    group = new HashSet<>();
		    rs.refactoringGroups.put(groupKey, group);
		}
		group.add(r1);
        RefactoringSet add = rs;
      } else {
        throw new RuntimeException("refactoring not supported");
      }
    }
  }
  @Override
  public void handleException(String commitId, Exception e) {
    this.ex = e;
  }
  public RefactoringSet assertAndGetResult() {
    if (ex == null) {
      return rs;
    }
    throw new RuntimeException(ex); 
  }
}