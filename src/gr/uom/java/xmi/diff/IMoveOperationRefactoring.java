package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper;
import gr.uom.java.xmi.decomposition.replacement.Replacement;

public interface IMoveOperationRefactoring {

	String toString();

	String getName();

	RefactoringType getRefactoringType();

	UMLOperationBodyMapper getBodyMapper();

	UMLOperation getOriginalOperation();

	UMLOperation getMovedOperation();

	Set<Replacement> getReplacements();

	/**
	 * @return the code range of the source method in the <b>parent</b> commit
	 */
	CodeRange getSourceOperationCodeRangeBeforeMove();

	/**
	 * @return the code range of the target method in the <b>child</b> commit
	 */
	CodeRange getTargetOperationCodeRangeAfterMove();

	boolean compatibleWith(MoveAttributeRefactoring ref);

	Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring();

	Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring();

	List<CodeRange> leftSide();

	List<CodeRange> rightSide();

}