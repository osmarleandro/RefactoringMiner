package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.UMLAnnotation;
import gr.uom.java.xmi.UMLAttribute;

public interface IRemoveAttributeAnnotationRefactoring {

	UMLAnnotation getAnnotation();

	UMLAttribute getAttributeBefore();

	UMLAttribute getAttributeAfter();

	List<CodeRange> leftSide();

	List<CodeRange> rightSide();

	RefactoringType getRefactoringType();

	String getName();

	Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring();

	Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring();

	String toString();

	int hashCode();

	boolean equals(Object obj);

}