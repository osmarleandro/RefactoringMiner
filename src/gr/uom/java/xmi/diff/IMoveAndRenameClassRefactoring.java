package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.UMLClass;

public interface IMoveAndRenameClassRefactoring {

	String toString();

	String getName();

	RefactoringType getRefactoringType();

	String getOriginalClassName();

	String getRenamedClassName();

	UMLClass getOriginalClass();

	UMLClass getRenamedClass();

	Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring();

	Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring();

	List<CodeRange> leftSide();

	List<CodeRange> rightSide();

}