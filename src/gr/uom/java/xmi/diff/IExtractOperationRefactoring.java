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

public interface IExtractOperationRefactoring {

	String toString();

	UMLOperationBodyMapper getBodyMapper();

	UMLOperation getExtractedOperation();

	UMLOperation getSourceOperationBeforeExtraction();

	UMLOperation getSourceOperationAfterExtraction();

	List<OperationInvocation> getExtractedOperationInvocations();

	Set<Replacement> getReplacements();

	Set<AbstractCodeFragment> getExtractedCodeFragmentsFromSourceOperation();

	Set<AbstractCodeFragment> getExtractedCodeFragmentsToExtractedOperation();

	/**
	 * @return the code range of the source method in the <b>parent</b> commit
	 */
	CodeRange getSourceOperationCodeRangeBeforeExtraction();

	/**
	 * @return the code range of the source method in the <b>child</b> commit
	 */
	CodeRange getSourceOperationCodeRangeAfterExtraction();

	/**
	 * @return the code range of the extracted method in the <b>child</b> commit
	 */
	CodeRange getExtractedOperationCodeRange();

	/**
	 * @return the code range of the extracted code fragment from the source method in the <b>parent</b> commit
	 */
	CodeRange getExtractedCodeRangeFromSourceOperation();

	/**
	 * @return the code range of the extracted code fragment to the extracted method in the <b>child</b> commit
	 */
	CodeRange getExtractedCodeRangeToExtractedOperation();

	/**
	 * @return the code range(s) of the invocation(s) to the extracted method inside the source method in the <b>child</b> commit
	 */
	Set<CodeRange> getExtractedOperationInvocationCodeRanges();

	String getName();

	RefactoringType getRefactoringType();

	Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring();

	Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring();

	List<CodeRange> leftSide();

	List<CodeRange> rightSide();

}