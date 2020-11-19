package org.refactoringminer.utils;

import java.io.PrintStream;
import java.util.EnumSet;

import org.refactoringminer.api.RefactoringType;
import org.refactoringminer.utils.ResultComparator.CompareResult;

public interface IResultComparator {

	IResultComparator expect(RefactoringSet... sets);

	IResultComparator dontExpect(RefactoringSet... sets);

	IResultComparator compareWith(String groupId, RefactoringSet... actualArray);

	void printSummary(PrintStream out, EnumSet<RefactoringType> refTypesToConsider);

	CompareResult getCompareResult(String groupId, EnumSet<RefactoringType> refTypesToConsider);

	void printDetails(PrintStream out, EnumSet<RefactoringType> refTypesToConsider);

	boolean isGroupRefactorings();

	void setGroupRefactorings(boolean groupRefactorings);

	boolean isIgnoreMethodParams();

	void setIgnoreMethodParams(boolean ignoreMethodParams);

	boolean isIgnorePullUpToExtractedSupertype();

	void setIgnorePullUpToExtractedSupertype(boolean ignorePullUpToExtractedSupertype);

	boolean isIgnoreMoveToMovedType();

	void setIgnoreMoveToMovedType(boolean ignoreMoveToMovedType);

	boolean isIgnoreMoveToRenamedType();

	void setIgnoreMoveToRenamedType(boolean ignoreMoveToRenamedType);

}