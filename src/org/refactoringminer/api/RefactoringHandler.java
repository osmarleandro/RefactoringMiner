package org.refactoringminer.api;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public List<Refactoring> detectRefactorings(GitHistoryRefactoringMinerImpl gitHistoryRefactoringMinerImpl, File projectFolder, String cloneURL, String currentCommitId) {
		List<Refactoring> refactoringsAtRevision = Collections.emptyList();
		try {
			List<String> filesBefore = new ArrayList<String>();
			List<String> filesCurrent = new ArrayList<String>();
			Map<String, String> renamedFilesHint = new HashMap<String, String>();
			String parentCommitId = gitHistoryRefactoringMinerImpl.populateWithGitHubAPI(cloneURL, currentCommitId, filesBefore, filesCurrent, renamedFilesHint);
			File currentFolder = new File(projectFolder.getParentFile(), projectFolder.getName() + "-" + currentCommitId);
			File parentFolder = new File(projectFolder.getParentFile(), projectFolder.getName() + "-" + parentCommitId);
			if (!currentFolder.exists()) {	
				gitHistoryRefactoringMinerImpl.downloadAndExtractZipFile(projectFolder, cloneURL, currentCommitId);
			}
			if (!parentFolder.exists()) {	
				gitHistoryRefactoringMinerImpl.downloadAndExtractZipFile(projectFolder, cloneURL, parentCommitId);
			}
			if (currentFolder.exists() && parentFolder.exists()) {
				UMLModel currentUMLModel = gitHistoryRefactoringMinerImpl.createModel(currentFolder, filesCurrent);
				UMLModel parentUMLModel = gitHistoryRefactoringMinerImpl.createModel(parentFolder, filesBefore);
				// Diff between currentModel e parentModel
				refactoringsAtRevision = parentUMLModel.diff(currentUMLModel, renamedFilesHint).getRefactorings();
				refactoringsAtRevision = gitHistoryRefactoringMinerImpl.filter(refactoringsAtRevision);
			}
			else {
				gitHistoryRefactoringMinerImpl.logger.warn(String.format("Folder %s not found", currentFolder.getPath()));
			}
		} catch (Exception e) {
			gitHistoryRefactoringMinerImpl.logger.warn(String.format("Ignored revision %s due to error", currentCommitId), e);
			handleException(currentCommitId, e);
		}
		handle(currentCommitId, refactoringsAtRevision);
	
		return refactoringsAtRevision;
	}
}
