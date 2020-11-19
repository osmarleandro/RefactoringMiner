package org.refactoringminer.api;

import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;

public interface IRefactoring {

	RefactoringType getRefactoringType();

	String getName();

	String toString();

	/**
	 * @return a Set of ImmutablePair where left is the file path of a program element, and right is the qualified name of the class containing the program element
	 */
	Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring();

	/**
	 * @return a Set of ImmutablePair where left is the file path of a program element, and right is the qualified name of the class containing the program element
	 */
	Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring();

	String toJSON();

}