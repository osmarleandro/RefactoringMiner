package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;
import gr.uom.java.xmi.decomposition.VariableDeclaration;

public interface ISplitVariableRefactoring {

	Set<VariableDeclaration> getSplitVariables();

	VariableDeclaration getOldVariable();

	UMLOperation getOperationBefore();

	UMLOperation getOperationAfter();

	Set<AbstractCodeMapping> getVariableReferences();

	RefactoringType getRefactoringType();

	String getName();

	Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring();

	Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring();

	String toString();

	int hashCode();

	boolean equals(Object obj);

	List<CodeRange> leftSide();

	List<CodeRange> rightSide();

}