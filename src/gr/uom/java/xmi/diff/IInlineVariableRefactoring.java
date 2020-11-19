package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;
import gr.uom.java.xmi.decomposition.VariableDeclaration;

public interface IInlineVariableRefactoring {

	void addReference(AbstractCodeMapping mapping);

	RefactoringType getRefactoringType();

	String getName();

	VariableDeclaration getVariableDeclaration();

	UMLOperation getOperationBefore();

	UMLOperation getOperationAfter();

	Set<AbstractCodeMapping> getReferences();

	String toString();

	/**
	 * @return the code range of the inlined variable declaration in the <b>parent</b> commit
	 */
	CodeRange getInlinedVariableDeclarationCodeRange();

	int hashCode();

	boolean equals(Object obj);

	Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring();

	Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring();

	List<CodeRange> leftSide();

	List<CodeRange> rightSide();

}