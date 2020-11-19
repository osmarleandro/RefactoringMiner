package gr.uom.java.xmi.diff;

import java.util.List;

import org.refactoringminer.api.RefactoringType;

public interface IPushDownAttributeRefactoring {

	String toString();

	RefactoringType getRefactoringType();

	List<CodeRange> rightSide();

}