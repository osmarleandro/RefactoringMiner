package org.refactoringminer.rm1;

import java.io.IOException;

import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.Churn;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.api.RefactoringType;

public interface IGitHistoryRefactoringMinerImpl {

	void setRefactoringTypesToConsider(RefactoringType... types);

	void detectAll(Repository repository, String branch, RefactoringHandler handler) throws Exception;

	void fetchAndDetectNew(Repository repository, RefactoringHandler handler) throws Exception;

	void detectAtCommit(Repository repository, String commitId, RefactoringHandler handler);

	void detectAtCommit(Repository repository, String commitId, RefactoringHandler handler, int timeout);

	String getConfigId();

	void detectBetweenTags(Repository repository, String startTag, String endTag, RefactoringHandler handler)
			throws Exception;

	void detectBetweenCommits(Repository repository, String startCommitId, String endCommitId,
			RefactoringHandler handler) throws Exception;

	Churn churnAtCommit(Repository repository, String commitId, RefactoringHandler handler);

	void detectAtCommit(String gitURL, String commitId, RefactoringHandler handler, int timeout);

	void detectAtPullRequest(String cloneURL, int pullRequestId, RefactoringHandler handler, int timeout)
			throws IOException;

}