package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.UMLAttribute;
import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;

public interface IExtractAttributeRefactoring {

	void addReference(AbstractCodeMapping mapping);

	RefactoringType getRefactoringType();

	String getName();

	UMLAttribute getVariableDeclaration();

	Set<AbstractCodeMapping> getReferences();

	String toString();

	/**
	 * @return the code range of the extracted variable declaration in the <b>child</b> commit
	 */
	CodeRange getExtractedVariableDeclarationCodeRange();

	UMLClass getOriginalClass();

	UMLClass getNextClass();

	int hashCode();

	boolean equals(Object obj);

	Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring();

	Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring();

	List<CodeRange> leftSide();

	List<CodeRange> rightSide();

}