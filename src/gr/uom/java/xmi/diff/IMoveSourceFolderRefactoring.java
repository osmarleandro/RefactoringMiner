package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.RefactoringType;

public interface IMoveSourceFolderRefactoring {

	void addMovedClassToAnotherSourceFolder(MovedClassToAnotherSourceFolder movedClassToAnotherSourceFolder);

	List<MovedClassToAnotherSourceFolder> getMovedClassesToAnotherSourceFolder();

	RenamePattern getPattern();

	String toString();

	String getName();

	RefactoringType getRefactoringType();

	Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring();

	Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring();

	List<CodeRange> leftSide();

	List<CodeRange> rightSide();

}