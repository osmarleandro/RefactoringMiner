package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.AbstractCodeFragment;
import gr.uom.java.xmi.decomposition.OperationInvocation;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper;
import gr.uom.java.xmi.decomposition.replacement.Replacement;

public interface IInlineOperationRefactoring {

	String toString();

	String getName();

	RefactoringType getRefactoringType();

	UMLOperationBodyMapper getBodyMapper();

	UMLOperation getInlinedOperation();

	UMLOperation getTargetOperationAfterInline();

	UMLOperation getTargetOperationBeforeInline();

	List<OperationInvocation> getInlinedOperationInvocations();

	Set<Replacement> getReplacements();

	Set<AbstractCodeFragment> getInlinedCodeFragments();

	/**
	 * @return the code range of the target method in the <b>parent</b> commit
	 */
	CodeRange getTargetOperationCodeRangeBeforeInline();

	/**
	 * @return the code range of the target method in the <b>child</b> commit
	 */
	CodeRange getTargetOperationCodeRangeAfterInline();

	/**
	 * @return the code range of the inlined method in the <b>parent</b> commit
	 */
	CodeRange getInlinedOperationCodeRange();

	/**
	 * @return the code range of the inlined code fragment from the inlined method in the <b>parent</b> commit
	 */
	CodeRange getInlinedCodeRangeFromInlinedOperation();

	/**
	 * @return the code range of the inlined code fragment in the target method in the <b>child</b> commit
	 */
	CodeRange getInlinedCodeRangeInTargetOperation();

	/**
	 * @return the code range(s) of the invocation(s) to the inlined method inside the target method in the <b>parent</b> commit
	 */
	Set<CodeRange> getInlinedOperationInvocationCodeRanges();

	Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring();

	Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring();

	List<CodeRange> leftSide();

	List<CodeRange> rightSide();

}