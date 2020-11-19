package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.UMLType;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;

public interface IChangeReturnTypeRefactoring {

	RefactoringType getRefactoringType();

	String getName();

	UMLType getOriginalType();

	UMLType getChangedType();

	UMLOperation getOperationBefore();

	UMLOperation getOperationAfter();

	Set<AbstractCodeMapping> getReturnReferences();

	String toString();

	int hashCode();

	boolean equals(Object obj);

	Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring();

	Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring();

	List<CodeRange> leftSide();

	List<CodeRange> rightSide();

}