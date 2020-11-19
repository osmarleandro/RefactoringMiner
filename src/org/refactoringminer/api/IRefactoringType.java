package org.refactoringminer.api;

import java.util.List;
import java.util.regex.Pattern;

import org.refactoringminer.utils.RefactoringRelationship;

public interface IRefactoringType {

	Pattern getRegex();

	String getDisplayName();

	String getAbbreviation();

	String aggregate(String refactoringDescription);

	List<RefactoringRelationship> parseRefactoring(String refactoringDescription);

	String getGroup(String refactoringDescription, int group);

}