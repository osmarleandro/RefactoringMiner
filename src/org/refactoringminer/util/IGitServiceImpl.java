package org.refactoringminer.util;

import java.util.List;
import java.util.Map;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.refactoringminer.api.Churn;

public interface IGitServiceImpl {

	Repository cloneIfNotExists(String projectPath, String cloneUrl/*, String branch*/) throws Exception;

	Repository openRepository(String repositoryPath) throws Exception;

	void checkout(Repository repository, String commitId) throws Exception;

	void checkout2(Repository repository, String commitId) throws Exception;

	int countCommits(Repository repository, String branch) throws Exception;

	RevWalk fetchAndCreateNewRevsWalk(Repository repository) throws Exception;

	RevWalk fetchAndCreateNewRevsWalk(Repository repository, String branch) throws Exception;

	RevWalk createAllRevsWalk(Repository repository) throws Exception;

	RevWalk createAllRevsWalk(Repository repository, String branch) throws Exception;

	Iterable<RevCommit> createRevsWalkBetweenTags(Repository repository, String startTag, String endTag)
			throws Exception;

	Iterable<RevCommit> createRevsWalkBetweenCommits(Repository repository, String startCommitId, String endCommitId)
			throws Exception;

	boolean isCommitAnalyzed(String sha1);

	void fileTreeDiff(Repository repository, RevCommit currentCommit, List<String> javaFilesBefore,
			List<String> javaFilesCurrent, Map<String, String> renamedFilesHint) throws Exception;

	Churn churn(Repository repository, RevCommit currentCommit) throws Exception;

}