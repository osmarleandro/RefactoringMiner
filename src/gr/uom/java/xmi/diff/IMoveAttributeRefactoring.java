package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.UMLAttribute;

public interface IMoveAttributeRefactoring {

	String toString();

	String getName();

	RefactoringType getRefactoringType();

	UMLAttribute getOriginalAttribute();

	UMLAttribute getMovedAttribute();

	String getSourceClassName();

	String getTargetClassName();

	/**
	 * @return the code range of the source attribute in the <b>parent</b> commit
	 */
	CodeRange getSourceAttributeCodeRangeBeforeMove();

	/**
	 * @return the code range of the target attribute in the <b>child</b> commit
	 */
	CodeRange getTargetAttributeCodeRangeAfterMove();

	boolean equals(Object o);

	int hashCode();

	Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring();

	Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring();

	List<CodeRange> leftSide();

	List<CodeRange> rightSide();

}