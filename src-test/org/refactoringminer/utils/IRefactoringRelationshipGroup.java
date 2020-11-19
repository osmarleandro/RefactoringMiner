package org.refactoringminer.utils;

import org.refactoringminer.api.RefactoringType;

public interface IRefactoringRelationshipGroup {

	RefactoringType addRefactoringRelationship(RefactoringRelationship r);

	RefactoringType getRefactoringType();

	String getMainEntity();

	boolean equals(Object obj);

	int hashCode();

}