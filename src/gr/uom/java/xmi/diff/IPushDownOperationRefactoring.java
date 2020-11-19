package gr.uom.java.xmi.diff;

import java.util.List;

import org.refactoringminer.api.RefactoringType;

public interface IPushDownOperationRefactoring {

	RefactoringType getRefactoringType();

	List<CodeRange> rightSide();

}