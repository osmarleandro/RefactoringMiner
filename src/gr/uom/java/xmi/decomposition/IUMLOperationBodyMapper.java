package gr.uom.java.xmi.decomposition;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringMinerTimedOutException;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.replacement.MethodInvocationReplacement;
import gr.uom.java.xmi.decomposition.replacement.Replacement;
import gr.uom.java.xmi.diff.CandidateAttributeRefactoring;
import gr.uom.java.xmi.diff.CandidateMergeVariableRefactoring;
import gr.uom.java.xmi.diff.CandidateSplitVariableRefactoring;

public interface IUMLOperationBodyMapper {

	void addChildMapper(UMLOperationBodyMapper mapper);

	List<UMLOperationBodyMapper> getChildMappers();

	IUMLOperationBodyMapper getParentMapper();

	UMLOperation getCallSiteOperation();

	UMLOperation getOperation1();

	UMLOperation getOperation2();

	Set<Refactoring> getRefactorings();

	Set<Refactoring> getRefactoringsAfterPostProcessing();

	Set<CandidateAttributeRefactoring> getCandidateAttributeRenames();

	Set<CandidateMergeVariableRefactoring> getCandidateAttributeMerges();

	Set<CandidateSplitVariableRefactoring> getCandidateAttributeSplits();

	Set<AbstractCodeMapping> getMappings();

	List<StatementObject> getNonMappedLeavesT1();

	List<StatementObject> getNonMappedLeavesT2();

	List<CompositeStatementObject> getNonMappedInnerNodesT1();

	List<CompositeStatementObject> getNonMappedInnerNodesT2();

	int mappingsWithoutBlocks();

	int nonMappedElementsT1();

	int nonMappedLeafElementsT1();

	int nonMappedElementsT2();

	int nonMappedLeafElementsT2();

	int nonMappedElementsT2CallingAddedOperation(List<UMLOperation> addedOperations);

	int nonMappedElementsT1CallingRemovedOperation(List<UMLOperation> removedOperations);

	boolean callsRemovedAndAddedOperation(List<UMLOperation> removedOperations, List<UMLOperation> addedOperations);

	int exactMatches();

	List<AbstractCodeMapping> getExactMatches();

	double normalizedEditDistance();

	int operationNameEditDistance();

	Set<Replacement> getReplacements();

	Set<Replacement> getReplacementsInvolvingMethodInvocation();

	Set<MethodInvocationReplacement> getMethodInvocationRenameReplacements();

	void processInnerNodes(List<CompositeStatementObject> innerNodes1, List<CompositeStatementObject> innerNodes2,
			Map<String, String> parameterToArgumentMap) throws RefactoringMinerTimedOutException;

	void processLeaves(List<? extends AbstractCodeFragment> leaves1, List<? extends AbstractCodeFragment> leaves2,
			Map<String, String> parameterToArgumentMap) throws RefactoringMinerTimedOutException;

	boolean isEmpty();

	boolean equals(Object o);

	String toString();

	int compareTo(IUMLOperationBodyMapper operationBodyMapper);

	boolean containsExtractOperationRefactoring(UMLOperation extractedOperation);

}