package org.refactoringminer.api;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;

import gr.uom.java.xmi.UMLModel;

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

	public List<Refactoring> detectRefactorings(GitHistoryRefactoringMinerImpl gitHistoryRefactoringMinerImpl, String gitURL, String currentCommitId) {
		List<Refactoring> refactoringsAtRevision = Collections.emptyList();
		try {
			Set<String> repositoryDirectoriesBefore = ConcurrentHashMap.newKeySet();
			Set<String> repositoryDirectoriesCurrent = ConcurrentHashMap.newKeySet();
			Map<String, String> fileContentsBefore = new ConcurrentHashMap<String, String>();
			Map<String, String> fileContentsCurrent = new ConcurrentHashMap<String, String>();
			Map<String, String> renamedFilesHint = new ConcurrentHashMap<String, String>();
			gitHistoryRefactoringMinerImpl.populateWithGitHubAPI(gitURL, currentCommitId, fileContentsBefore, fileContentsCurrent, renamedFilesHint, repositoryDirectoriesBefore, repositoryDirectoriesCurrent);
			UMLModel currentUMLModel = gitHistoryRefactoringMinerImpl.createModel(fileContentsCurrent, repositoryDirectoriesCurrent);
			UMLModel parentUMLModel = gitHistoryRefactoringMinerImpl.createModel(fileContentsBefore, repositoryDirectoriesBefore);
			//  Diff between currentModel e parentModel
			refactoringsAtRevision = parentUMLModel.diff(currentUMLModel, renamedFilesHint).getRefactorings();
			refactoringsAtRevision = gitHistoryRefactoringMinerImpl.filter(refactoringsAtRevision);
		}
		catch(RefactoringMinerTimedOutException e) {
			gitHistoryRefactoringMinerImpl.logger.warn(String.format("Ignored revision %s due to timeout", currentCommitId), e);
			handleException(currentCommitId, e);
		}
		catch (Exception e) {
			gitHistoryRefactoringMinerImpl.logger.warn(String.format("Ignored revision %s due to error", currentCommitId), e);
			handleException(currentCommitId, e);
		}
		handle(currentCommitId, refactoringsAtRevision);
	
		return refactoringsAtRevision;
	}
}
