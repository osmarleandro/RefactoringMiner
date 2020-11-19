package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.decomposition.VariableDeclaration;

public interface IMergeAttributeRefactoring {

	Set<VariableDeclaration> getMergedAttributes();

	VariableDeclaration getNewAttribute();

	Set<CandidateMergeVariableRefactoring> getAttributeMerges();

	String getClassNameBefore();

	String getClassNameAfter();

	RefactoringType getRefactoringType();

	String getName();

	String toString();

	int hashCode();

	boolean equals(Object obj);

	Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring();

	Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring();

	List<CodeRange> leftSide();

	List<CodeRange> rightSide();

}