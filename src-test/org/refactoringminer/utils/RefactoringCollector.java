package org.refactoringminer.utils;

import org.refactoringminer.api.RefactoringHandler;

public class RefactoringCollector extends RefactoringHandler {
  public final RefactoringSet rs;
  private Exception ex = null;
  public RefactoringCollector(String cloneUrl, String commitId) {
    rs = new RefactoringSet(cloneUrl, commitId);
  }
  @Override
  public void handleException(String commitId, Exception e) {
    this.ex = e;
  }
  public RefactoringSet assertAndGetResult() {
    if (ex == null) {
      return rs;
    }
    throw new RuntimeException(ex); 
  }
}