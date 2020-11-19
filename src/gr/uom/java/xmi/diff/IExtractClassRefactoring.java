package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.UMLAttribute;
import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.UMLOperation;

public interface IExtractClassRefactoring {

	String toString();

	RefactoringType getRefactoringType();

	String getName();

	UMLClass getExtractedClass();

	UMLClass getOriginalClass();

	Set<UMLOperation> getExtractedOperations();

	Set<UMLAttribute> getExtractedAttributes();

	UMLAttribute getAttributeOfExtractedClassTypeInOriginalClass();

	Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring();

	Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring();

	List<CodeRange> leftSide();

	List<CodeRange> rightSide();

}