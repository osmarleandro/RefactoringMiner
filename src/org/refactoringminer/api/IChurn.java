package org.refactoringminer.api;

public interface IChurn {

	int getLinesAdded();

	int getLinesRemoved();

	int getChurn();

}