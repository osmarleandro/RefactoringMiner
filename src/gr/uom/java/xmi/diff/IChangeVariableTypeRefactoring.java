package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;
import gr.uom.java.xmi.decomposition.VariableDeclaration;

public interface IChangeVariableTypeRefactoring {

	void addRelatedRefactoring(Refactoring refactoring);

	Set<Refactoring> getRelatedRefactorings();

	RefactoringType getRefactoringType();

	String getName();

	VariableDeclaration getOriginalVariable();

	VariableDeclaration getChangedTypeVariable();

	UMLOperation getOperationBefore();

	UMLOperation getOperationAfter();

	Set<AbstractCodeMapping> getVariableReferences();

	String toString();

	int hashCode();

	boolean equals(Object obj);

	Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring();

	Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring();

	List<CodeRange> leftSide();

	List<CodeRange> rightSide();

}