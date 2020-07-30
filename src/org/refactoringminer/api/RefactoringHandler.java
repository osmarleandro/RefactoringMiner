package org.refactoringminer.api;

import java.io.File;
import java.util.List;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

/**
 * Handler object that works in conjunction with {@link org.refactoringminer.api.GitHistoryRefactoringMiner}.
 * 
 */
public abstract class RefactoringHandler {

	/**
	 * Indicate commits that should be ignored.
	 * You may override this method to implement custom logic.
	 *  
	 * @param commitId The SHA key that identifies the commit.
	 * @return True to skip the commit, false otherwise.
	 */
	public boolean skipCommit(String commitId) {
		return false;
	}

	/**
	 * This method is called after each commit is analyzed.
	 * You should override this method to do your custom logic with the list of detected refactorings.
	 * 
	 * @param commitId The sha of the analyzed commit.
	 * @param refactorings List of refactorings detected in the commit.
	 */
	public void handle(String commitId, List<Refactoring> refactorings) {}

	/**
     * This method is called whenever an exception is thrown during the analysis of the given commit.
     * You should override this method to do your custom logic in the case of exceptions (e.g. skip or rethrow).
     * 
     * @param commitId The SHA key that identifies the commit.
     * @param e The exception thrown.
     */
    public void handleException(String commitId, Exception e) {
        throw new RuntimeException(e);
    }

	/**
	 * This method is called after all commits are analyzed.
	 * You may override this method to implement custom logic.
	 * 
	 * @param refactoringsCount Total number of refactorings detected. 
	 * @param commitsCount Total number of commits analyzed.
	 * @param errorCommitsCount Total number of commits not analyzed due to errors.
	 */
	public void onFinish(int refactoringsCount, int commitsCount, int errorCommitsCount) {}

	public void detectAtCommit(Repository repository, String commitId, GitHistoryRefactoringMinerImpl gitHistoryRefactoringMinerImpl) {
		String cloneURL = repository.getConfig().getString("remote", "origin", "url");
		File metadataFolder = repository.getDirectory();
		File projectFolder = metadataFolder.getParentFile();
		GitService gitService = new GitServiceImpl();
		RevWalk walk = new RevWalk(repository);
		try {
			RevCommit commit = walk.parseCommit(repository.resolve(commitId));
			if (commit.getParentCount() > 0) {
				walk.parseCommit(commit.getParent(0));
				gitHistoryRefactoringMinerImpl.detectRefactorings(gitService, repository, this, projectFolder, commit);
			}
			else {
				gitHistoryRefactoringMinerImpl.logger.warn(String.format("Ignored revision %s because it has no parent", commitId));
			}
		} catch (MissingObjectException moe) {
			gitHistoryRefactoringMinerImpl.detectRefactorings(this, projectFolder, cloneURL, commitId);
		} catch (RefactoringMinerTimedOutException e) {
			gitHistoryRefactoringMinerImpl.logger.warn(String.format("Ignored revision %s due to timeout", commitId), e);
		} catch (Exception e) {
			gitHistoryRefactoringMinerImpl.logger.warn(String.format("Ignored revision %s due to error", commitId), e);
			handleException(commitId, e);
		} finally {
			walk.close();
			walk.dispose();
		}
	}
}
