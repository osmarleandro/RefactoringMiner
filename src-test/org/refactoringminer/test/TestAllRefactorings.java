package org.refactoringminer.test;

import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.test.RefactoringPopulator.Refactorings;
import org.refactoringminer.test.RefactoringPopulator.Systems;

import java.math.BigInteger;

import org.junit.Test;

public class TestAllRefactorings {

	@Test
	public void testAllRefactorings() throws Exception {
		TestBuilder test = new TestBuilder(new GitHistoryRefactoringMinerImpl(), "tmp1", Refactorings.All.getValue());
		BigInteger refactoringsFlag = Refactorings.All.getValue();
		int systemsFlag = Systems.FSE.getValue();
		if ((systemsFlag & Systems.FSE.getValue()) > 0) {
			RefactoringPopulator.prepareFSERefactorings(test, refactoringsFlag);
		}
		test.assertExpectations();
	}
}
