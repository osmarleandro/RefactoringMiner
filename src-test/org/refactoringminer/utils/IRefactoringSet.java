package org.refactoringminer.utils;

import java.io.File;
import java.io.PrintStream;
import java.util.EnumSet;
import java.util.Set;

import org.refactoringminer.api.RefactoringType;
import org.refactoringminer.utils.RefactoringRelationship.GroupKey;

public interface IRefactoringSet {

	String getProject();

	String getRevision();

	Set<RefactoringRelationship> getRefactorings();

	Set<RefactoringRelationship.GroupKey> getRefactoringsGroups();

	IRefactoringSet add(RefactoringType type, String entityBefore, String entityAfter);

	IRefactoringSet add(RefactoringRelationship r);

	IRefactoringSet add(Iterable<RefactoringRelationship> rs);

	IRefactoringSet ignoring(EnumSet<RefactoringType> refTypes);

	IRefactoringSet ignoringMethodParameters(boolean active);

	void printSourceCode(PrintStream pw);

	void saveToFile(File file);

	void readFromFile(File file);

}