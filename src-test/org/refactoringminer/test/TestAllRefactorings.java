package org.refactoringminer.test;

import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.test.RefactoringPopulator_RENAMED.Refactorings;
import org.refactoringminer.test.RefactoringPopulator_RENAMED.Systems;

import org.junit.Test;

public class TestAllRefactorings {

	@Test
	public void testAllRefactorings() throws Exception {
		TestBuilder test = new TestBuilder(new GitHistoryRefactoringMinerImpl(), "tmp1", Refactorings.All.getValue());
		RefactoringPopulator_RENAMED.feedRefactoringsInstances(Refactorings.All.getValue(), Systems.FSE.getValue(), test);
		test.assertExpectations();
	}
}
