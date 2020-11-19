package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.RefactoringType;

public interface IRenamePackageRefactoring {

	void addMoveClassRefactoring(MoveClassRefactoring moveClassRefactoring);

	RenamePattern getPattern();

	List<MoveClassRefactoring> getMoveClassRefactorings();

	RefactoringType getRefactoringType();

	String getName();

	String toString();

	Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring();

	Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring();

	List<CodeRange> leftSide();

	List<CodeRange> rightSide();

}