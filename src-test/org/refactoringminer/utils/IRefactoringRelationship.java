package org.refactoringminer.utils;

import org.refactoringminer.api.RefactoringType;
import org.refactoringminer.utils.RefactoringRelationship.GroupKey;

public interface IRefactoringRelationship {

	RefactoringType getRefactoringType();

	String getEntityBefore();

	String getEntityAfter();

	boolean equals(Object obj);

	int hashCode();

	String toString();

	String getMainEntity();

	String getSecondaryEntity();

	int compareTo(IRefactoringRelationship o);

	GroupKey getGroupKey();

}