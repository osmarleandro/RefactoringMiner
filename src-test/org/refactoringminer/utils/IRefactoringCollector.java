package org.refactoringminer.utils;

import java.util.List;

import org.refactoringminer.api.Refactoring;

public interface IRefactoringCollector {

	void handle(String commitId, List<Refactoring> refactorings);

	void handleException(String commitId, Exception e);

	RefactoringSet assertAndGetResult();

}