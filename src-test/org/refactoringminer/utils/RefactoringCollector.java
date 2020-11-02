package org.refactoringminer.utils;

import java.util.List;

import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;

import gr.uom.java.xmi.diff.ExtractOperationRefactoring;
import gr.uom.java.xmi.diff.ExtractSuperclassRefactoring;
import gr.uom.java.xmi.diff.InlineOperationRefactoring;
import gr.uom.java.xmi.diff.MoveAttributeRefactoring;
import gr.uom.java.xmi.diff.MoveClassRefactoring;
import gr.uom.java.xmi.diff.MoveOperationRefactoring;
import gr.uom.java.xmi.diff.RenameClassRefactoring;
import gr.uom.java.xmi.diff.RenameOperationRefactoring;

public class RefactoringCollector extends RefactoringHandler {
  private final RefactoringSet rs;
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