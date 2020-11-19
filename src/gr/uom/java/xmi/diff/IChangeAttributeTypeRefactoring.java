package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.decomposition.AbstractCodeMapping;
import gr.uom.java.xmi.decomposition.VariableDeclaration;

public interface IChangeAttributeTypeRefactoring {

	void addRelatedRefactoring(Refactoring refactoring);

	Set<Refactoring> getRelatedRefactorings();

	VariableDeclaration getOriginalAttribute();

	VariableDeclaration getChangedTypeAttribute();

	String getClassNameBefore();

	String getClassNameAfter();

	Set<AbstractCodeMapping> getAttributeReferences();

	RefactoringType getRefactoringType();

	String getName();

	String toString();

	int hashCode();

	boolean equals(Object obj);

	Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring();

	Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring();

	List<CodeRange> leftSide();

	List<CodeRange> rightSide();

}