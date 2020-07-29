package gr.uom.java.xmi.diff;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.refactoringminer.api.RefactoringMinerTimedOutException;

import gr.uom.java.xmi.UMLAnonymousClass;
import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.UMLType;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;
import gr.uom.java.xmi.decomposition.OperationInvocation;
import gr.uom.java.xmi.decomposition.StatementObject;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper;

public class InlineOperationDetection {
	public UMLOperationBodyMapper mapper;
	private List<UMLOperation> removedOperations;
	private UMLClassBaseDiff classDiff;
	private UMLModelDiff modelDiff;
	public List<OperationInvocation> operationInvocations;
	public Map<CallTreeNode, CallTree> callTreeMap = new LinkedHashMap<CallTreeNode, CallTree>();
	
	public InlineOperationDetection(UMLOperationBodyMapper mapper, List<UMLOperation> removedOperations, UMLClassBaseDiff classDiff, UMLModelDiff modelDiff) {
		this.mapper = mapper;
		this.removedOperations = removedOperations;
		this.classDiff = classDiff;
		this.modelDiff = modelDiff;
		this.operationInvocations = getInvocationsInTargetOperationBeforeInline(mapper);
	}

	public List<OperationInvocation> matchingInvocations(UMLOperation removedOperation, List<OperationInvocation> operationInvocations, Map<String, UMLType> variableTypeMap) {
		List<OperationInvocation> removedOperationInvocations = new ArrayList<OperationInvocation>();
		for(OperationInvocation invocation : operationInvocations) {
			if(invocation.matchesOperation(removedOperation, variableTypeMap, modelDiff)) {
				removedOperationInvocations.add(invocation);
			}
		}
		return removedOperationInvocations;
	}

	public UMLOperationBodyMapper createMapperForInlinedMethod(UMLOperationBodyMapper mapper,
			UMLOperation removedOperation, OperationInvocation removedOperationInvocation) throws RefactoringMinerTimedOutException {
		List<String> arguments = removedOperationInvocation.getArguments();
		List<String> parameters = removedOperation.getParameterNameList();
		Map<String, String> parameterToArgumentMap = new LinkedHashMap<String, String>();
		//special handling for methods with varargs parameter for which no argument is passed in the matching invocation
		int size = Math.min(arguments.size(), parameters.size());
		for(int i=0; i<size; i++) {
			parameterToArgumentMap.put(parameters.get(i), arguments.get(i));
		}
		UMLOperationBodyMapper operationBodyMapper = new UMLOperationBodyMapper(removedOperation, mapper, parameterToArgumentMap, classDiff);
		return operationBodyMapper;
	}

	public void generateCallTree(UMLOperation operation, CallTreeNode parent, CallTree callTree) {
		List<OperationInvocation> invocations = operation.getAllOperationInvocations();
		for(UMLOperation removedOperation : removedOperations) {
			for(OperationInvocation invocation : invocations) {
				if(invocation.matchesOperation(removedOperation, operation.variableTypeMap(), modelDiff)) {
					if(!callTree.contains(removedOperation)) {
						CallTreeNode node = new CallTreeNode(operation, removedOperation, invocation);
						parent.addChild(node);
						generateCallTree(removedOperation, node, callTree);
					}
				}
			}
		}
	}

	private List<OperationInvocation> getInvocationsInTargetOperationBeforeInline(UMLOperationBodyMapper mapper) {
		List<OperationInvocation> operationInvocations = mapper.getOperation1().getAllOperationInvocations();
		for(StatementObject statement : mapper.getNonMappedLeavesT1()) {
			ExtractOperationDetection.addStatementInvocations(operationInvocations, statement);
			for(UMLAnonymousClass anonymousClass : classDiff.getRemovedAnonymousClasses()) {
				if(statement.getLocationInfo().subsumes(anonymousClass.getLocationInfo())) {
					for(UMLOperation anonymousOperation : anonymousClass.getOperations()) {
						for(OperationInvocation anonymousInvocation : anonymousOperation.getAllOperationInvocations()) {
							if(!ExtractOperationDetection.containsInvocation(operationInvocations, anonymousInvocation)) {
								operationInvocations.add(anonymousInvocation);
							}
						}
					}
				}
			}
		}
		return operationInvocations;
	}

	public boolean inlineMatchCondition(UMLOperationBodyMapper operationBodyMapper) {
		int delegateStatements = 0;
		for(StatementObject statement : operationBodyMapper.getNonMappedLeavesT1()) {
			OperationInvocation invocation = statement.invocationCoveringEntireFragment();
			if(invocation != null && invocation.matchesOperation(operationBodyMapper.getOperation1())) {
				delegateStatements++;
			}
		}
		int mappings = operationBodyMapper.mappingsWithoutBlocks();
		int nonMappedElementsT1 = operationBodyMapper.nonMappedElementsT1()-delegateStatements;
		List<AbstractCodeMapping> exactMatchList = operationBodyMapper.getExactMatches();
		int exactMatches = exactMatchList.size();
		return mappings > 0 && (mappings > nonMappedElementsT1 ||
				(exactMatches == 1 && !exactMatchList.get(0).getFragment1().throwsNewException() && nonMappedElementsT1-exactMatches < 10) ||
				(exactMatches > 1 && nonMappedElementsT1-exactMatches < 20));
	}

	public boolean invocationMatchesWithAddedOperation(OperationInvocation removedOperationInvocation, Map<String, UMLType> variableTypeMap, List<OperationInvocation> operationInvocationsInNewMethod) {
		if(operationInvocationsInNewMethod.contains(removedOperationInvocation)) {
			for(UMLOperation addedOperation : classDiff.getAddedOperations()) {
				if(removedOperationInvocation.matchesOperation(addedOperation, variableTypeMap, modelDiff)) {
					return true;
				}
			}
		}
		return false;
	}
}
