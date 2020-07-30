package org.refactoringminer.api;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;

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

	public void detectAtCommit(String gitURL, String commitId, GitHistoryRefactoringMinerImpl gitHistoryRefactoringMinerImpl, int timeout) {
		ExecutorService service = Executors.newSingleThreadExecutor();
		Future<?> f = null;
		try {
			Runnable r = () -> gitHistoryRefactoringMinerImpl.detectRefactorings(this, gitURL, commitId);
			f = service.submit(r);
			f.get(timeout, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			f.cancel(true);
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			service.shutdown();
		}
	}
}
