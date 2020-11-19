package org.refactoringminer.test;

import org.refactoringminer.test.TestBuilder.ProjectMatcher;

public interface ITestBuilder {

	ITestBuilder verbose();

	ITestBuilder withAggregation();

	ProjectMatcher project(String cloneUrl, String branch);

	void assertExpectations() throws Exception;

}