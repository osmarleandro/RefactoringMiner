package org.refactoringminer.api;

import java.util.List;

import org.refactoringminer.utils.RefactoringRelationship;

import gr.uom.java.xmi.diff.ExtractOperationRefactoring;
import gr.uom.java.xmi.diff.ExtractSuperclassRefactoring;
import gr.uom.java.xmi.diff.InlineOperationRefactoring;
import gr.uom.java.xmi.diff.MoveAttributeRefactoring;
import gr.uom.java.xmi.diff.MoveClassRefactoring;
import gr.uom.java.xmi.diff.MoveOperationRefactoring;
import gr.uom.java.xmi.diff.RenameClassRefactoring;
import gr.uom.java.xmi.diff.RenameOperationRefactoring;

/**
 * Handler object that works in conjunction with {@link org.refactoringminer.api.GitHistoryRefactoringMiner}.
 * 
 */
public abstract class RefactoringHandler {

	/**
	 * Indicate commits that should be ignored.
	 * You may override this method to implement custom logic.
	 *  
	 * @param commitId The SHA key that identifies the commit.
	 * @return True to skip the commit, false otherwise.
	 */
	public boolean skipCommit(String commitId) {
		return false;
	}

	/**
	 * This method is called after each commit is analyzed.
	 * You should override this method to do your custom logic with the list of detected refactorings.
	 * 
	 * @param commitId The sha of the analyzed commit.
	 * @param refactorings List of refactorings detected in the commit.
	 */
	public void handle(String commitId, List<Refactoring> refactorings) {}

	/**
     * This method is called whenever an exception is thrown during the analysis of the given commit.
     * You should override this method to do your custom logic in the case of exceptions (e.g. skip or rethrow).
     * 
     * @param commitId The SHA key that identifies the commit.
     * @param e The exception thrown.
     */
    public void handleException(String commitId, Exception e) {
        throw new RuntimeException(e);
    }

	/**
	 * This method is called after all commits are analyzed.
	 * You may override this method to implement custom logic.
	 * 
	 * @param refactoringsCount Total number of refactorings detected. 
	 * @param commitsCount Total number of commits analyzed.
	 * @param errorCommitsCount Total number of commits not analyzed due to errors.
	 */
	public void onFinish(int refactoringsCount, int commitsCount, int errorCommitsCount) {}

	@Override
	public void handle(String commitId, List<Refactoring> refactorings) {
	    for (Refactoring r : refactorings) {
	      if (r instanceof MoveClassRefactoring) {
	        MoveClassRefactoring ref = (MoveClassRefactoring) r;
	        rs.add(new RefactoringRelationship(r.getRefactoringType(), ref.getOriginalClassName(), ref.getMovedClassName()));
	      } else if (r instanceof RenameClassRefactoring) {
	        RenameClassRefactoring ref = (RenameClassRefactoring) r;
	        rs.add(new RefactoringRelationship(r.getRefactoringType(), ref.getOriginalClassName(), ref.getRenamedClassName()));
	      } else if (r instanceof ExtractSuperclassRefactoring) {
	        ExtractSuperclassRefactoring ref = (ExtractSuperclassRefactoring) r;
	        for (String subclass : ref.getSubclassSet()) {
	          rs.add(new RefactoringRelationship(r.getRefactoringType(), subclass, ref.getExtractedClass().getName()));
	        }
	      } else if (r instanceof MoveOperationRefactoring) {
	        MoveOperationRefactoring ref = (MoveOperationRefactoring) r;
	        rs.add(new RefactoringRelationship(r.getRefactoringType(), ref.getOriginalOperation().getKey(), ref.getMovedOperation().getKey()));
	      } else if (r instanceof RenameOperationRefactoring) {
	        RenameOperationRefactoring ref = (RenameOperationRefactoring) r;
	        rs.add(new RefactoringRelationship(r.getRefactoringType(), ref.getOriginalOperation().getKey(), ref.getRenamedOperation().getKey()));
	      } else if (r instanceof ExtractOperationRefactoring) {
	        ExtractOperationRefactoring ref = (ExtractOperationRefactoring) r;
	        rs.add(new RefactoringRelationship(r.getRefactoringType(), ref.getSourceOperationBeforeExtraction().getKey(), ref.getExtractedOperation().getKey()));
	      } else if (r instanceof InlineOperationRefactoring) {
	        InlineOperationRefactoring ref = (InlineOperationRefactoring) r;
	        rs.add(new RefactoringRelationship(r.getRefactoringType(), ref.getInlinedOperation().getKey(), ref.getTargetOperationAfterInline().getKey()));
	      } else if (r instanceof MoveAttributeRefactoring) {
	        MoveAttributeRefactoring ref = (MoveAttributeRefactoring) r;
	        String attrName = ref.getMovedAttribute().getName();
	        rs.add(new RefactoringRelationship(r.getRefactoringType(), ref.getSourceClassName() + "#" + attrName, ref.getTargetClassName() + "#" + attrName));
	      } else {
	        throw new RuntimeException("refactoring not supported");
	      }
	    }
	  }
}
