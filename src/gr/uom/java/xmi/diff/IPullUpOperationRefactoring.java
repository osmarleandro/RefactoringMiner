package gr.uom.java.xmi.diff;

import java.util.List;

import org.refactoringminer.api.RefactoringType;

public interface IPullUpOperationRefactoring {

	RefactoringType getRefactoringType();

	List<CodeRange> rightSide();

}