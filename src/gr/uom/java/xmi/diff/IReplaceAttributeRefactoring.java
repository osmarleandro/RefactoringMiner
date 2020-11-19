package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.refactoringminer.api.RefactoringType;

public interface IReplaceAttributeRefactoring {

	Set<CandidateAttributeRefactoring> getAttributeRenames();

	String toString();

	String getName();

	RefactoringType getRefactoringType();

	List<CodeRange> rightSide();

}