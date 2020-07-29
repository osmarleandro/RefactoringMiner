package gr.uom.java.xmi.diff;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.refactoringminer.api.RefactoringMinerTimedOutException;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.UMLParameter;
import gr.uom.java.xmi.UMLType;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;
import gr.uom.java.xmi.decomposition.CompositeStatementObject;
import gr.uom.java.xmi.decomposition.LambdaExpressionObject;
import gr.uom.java.xmi.decomposition.OperationInvocation;
import gr.uom.java.xmi.decomposition.StatementObject;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper;
import gr.uom.java.xmi.decomposition.replacement.Replacement.ReplacementType;

public class ExtractOperationDetection {
	private UMLOperationBodyMapper mapper;
	private List<UMLOperation> addedOperations;
	private UMLClassBaseDiff classDiff;
	private UMLModelDiff modelDiff;
	public List<OperationInvocation> operationInvocations;
	public Map<CallTreeNode, CallTree> callTreeMap = new LinkedHashMap<CallTreeNode, CallTree>();

	public ExtractOperationDetection(UMLOperationBodyMapper mapper, List<UMLOperation> addedOperations, UMLClassBaseDiff classDiff, UMLModelDiff modelDiff) {
		this.mapper = mapper;
		this.addedOperations = addedOperations;
		this.classDiff = classDiff;
		this.modelDiff = modelDiff;
		this.operationInvocations = getInvocationsInSourceOperationAfterExtraction(mapper);
	}

	public List<ExtractOperationRefactoring> check(UMLOperation addedOperation) throws RefactoringMinerTimedOutException {
		List<ExtractOperationRefactoring> refactorings = new ArrayList<ExtractOperationRefactoring>();
		if(!mapper.getNonMappedLeavesT1().isEmpty() || !mapper.getNonMappedInnerNodesT1().isEmpty() ||
			!mapper.getReplacementsInvolvingMethodInvocation().isEmpty()) {
			List<OperationInvocation> addedOperationInvocations = matchingInvocations(addedOperation, operationInvocations, mapper.getOperation2().variableTypeMap());
			if(addedOperationInvocations.size() > 0) {
				int otherAddedMethodsCalled = 0;
				for(UMLOperation addedOperation2 : this.addedOperations) {
					if(!addedOperation.equals(addedOperation2)) {
						List<OperationInvocation> addedOperationInvocations2 = matchingInvocations(addedOperation2, operationInvocations, mapper.getOperation2().variableTypeMap());
						if(addedOperationInvocations2.size() > 0) {
							otherAddedMethodsCalled++;
						}
					}
				}
				if(otherAddedMethodsCalled == 0) {
					for(OperationInvocation addedOperationInvocation : addedOperationInvocations) {
						processAddedOperation(mapper, addedOperation, refactorings, addedOperationInvocations, addedOperationInvocation);
					}
				}
				else {
					processAddedOperation(mapper, addedOperation, refactorings, addedOperationInvocations, addedOperationInvocations.get(0));
				}
			}
		}
		return refactorings;
	}

	public static List<OperationInvocation> getInvocationsInSourceOperationAfterExtraction(UMLOperationBodyMapper mapper) {
		List<OperationInvocation> operationInvocations = mapper.getOperation2().getAllOperationInvocations();
		for(StatementObject statement : mapper.getNonMappedLeavesT2()) {
			addStatementInvocations(operationInvocations, statement);
		}
		return operationInvocations;
	}

	public static void addStatementInvocations(List<OperationInvocation> operationInvocations, StatementObject statement) {
		Map<String, List<OperationInvocation>> statementMethodInvocationMap = statement.getMethodInvocationMap();
		for(String key : statementMethodInvocationMap.keySet()) {
			for(OperationInvocation statementInvocation : statementMethodInvocationMap.get(key)) {
				if(!containsInvocation(operationInvocations, statementInvocation)) {
					operationInvocations.add(statementInvocation);
				}
			}
		}
		List<LambdaExpressionObject> lambdas = statement.getLambdas();
		for(LambdaExpressionObject lambda : lambdas) {
			if(lambda.getBody() != null) {
				for(OperationInvocation statementInvocation : lambda.getBody().getAllOperationInvocations()) {
					if(!containsInvocation(operationInvocations, statementInvocation)) {
						operationInvocations.add(statementInvocation);
					}
				}
			}
			if(lambda.getExpression() != null) {
				Map<String, List<OperationInvocation>> methodInvocationMap = lambda.getExpression().getMethodInvocationMap();
				for(String key : methodInvocationMap.keySet()) {
					for(OperationInvocation statementInvocation : methodInvocationMap.get(key)) {
						if(!containsInvocation(operationInvocations, statementInvocation)) {
							operationInvocations.add(statementInvocation);
						}
					}
				}
			}
		}
	}

	public static boolean containsInvocation(List<OperationInvocation> operationInvocations, OperationInvocation invocation) {
		for(OperationInvocation operationInvocation : operationInvocations) {
			if(operationInvocation.getLocationInfo().equals(invocation.getLocationInfo())) {
				return true;
			}
		}
		return false;
	}

	public List<OperationInvocation> matchingInvocations(UMLOperation operation,
			List<OperationInvocation> operationInvocations, Map<String, UMLType> variableTypeMap) {
		List<OperationInvocation> addedOperationInvocations = new ArrayList<OperationInvocation>();
		for(OperationInvocation invocation : operationInvocations) {
			if(invocation.matchesOperation(operation, variableTypeMap, modelDiff)) {
				addedOperationInvocations.add(invocation);
			}
		}
		return addedOperationInvocations;
	}

	public void generateCallTree(UMLOperation operation, CallTreeNode parent, CallTree callTree) {
		List<OperationInvocation> invocations = operation.getAllOperationInvocations();
		for(UMLOperation addedOperation : addedOperations) {
			for(OperationInvocation invocation : invocations) {
				if(invocation.matchesOperation(addedOperation, operation.variableTypeMap(), modelDiff)) {
					if(!callTree.contains(addedOperation)) {
						CallTreeNode node = new CallTreeNode(operation, addedOperation, invocation);
						parent.addChild(node);
						generateCallTree(addedOperation, node, callTree);
					}
				}
			}
		}
	}

	public UMLOperationBodyMapper createMapperForExtractedMethod(UMLOperationBodyMapper mapper,
			UMLOperation originalOperation, UMLOperation addedOperation, OperationInvocation addedOperationInvocation) throws RefactoringMinerTimedOutException {
		List<UMLParameter> originalMethodParameters = originalOperation.getParametersWithoutReturnType();
		Map<UMLParameter, UMLParameter> originalMethodParametersPassedAsArgumentsMappedToCalledMethodParameters = new LinkedHashMap<UMLParameter, UMLParameter>();
		List<String> arguments = addedOperationInvocation.getArguments();
		List<UMLParameter> parameters = addedOperation.getParametersWithoutReturnType();
		Map<String, String> parameterToArgumentMap = new LinkedHashMap<String, String>();
		//special handling for methods with varargs parameter for which no argument is passed in the matching invocation
		int size = Math.min(arguments.size(), parameters.size());
		for(int i=0; i<size; i++) {
			String argumentName = arguments.get(i);
			String parameterName = parameters.get(i).getName();
			parameterToArgumentMap.put(parameterName, argumentName);
			for(UMLParameter originalMethodParameter : originalMethodParameters) {
				if(originalMethodParameter.getName().equals(argumentName)) {
					originalMethodParametersPassedAsArgumentsMappedToCalledMethodParameters.put(originalMethodParameter, parameters.get(i));
				}
			}
		}
		if(parameterTypesMatch(originalMethodParametersPassedAsArgumentsMappedToCalledMethodParameters)) {
			UMLOperation delegateMethod = findDelegateMethod(originalOperation, addedOperation, addedOperationInvocation);
			return new UMLOperationBodyMapper(mapper,
					delegateMethod != null ? delegateMethod : addedOperation,
					new LinkedHashMap<String, String>(), parameterToArgumentMap, classDiff);
		}
		return null;
	}

	public boolean extractMatchCondition(UMLOperationBodyMapper operationBodyMapper, List<AbstractCodeMapping> additionalExactMatches) {
		int mappings = operationBodyMapper.mappingsWithoutBlocks();
		int nonMappedElementsT1 = operationBodyMapper.nonMappedElementsT1();
		int nonMappedElementsT2 = operationBodyMapper.nonMappedElementsT2();
		List<AbstractCodeMapping> exactMatchList = new ArrayList<AbstractCodeMapping>(operationBodyMapper.getExactMatches());
		boolean exceptionHandlingExactMatch = false;
		boolean throwsNewExceptionExactMatch = false;
		if(exactMatchList.size() == 1) {
			AbstractCodeMapping mapping = exactMatchList.get(0);
			if(mapping.getFragment1() instanceof StatementObject && mapping.getFragment2() instanceof StatementObject) {
				StatementObject statement1 = (StatementObject)mapping.getFragment1();
				StatementObject statement2 = (StatementObject)mapping.getFragment2();
				if(statement1.getParent().getString().startsWith("catch(") &&
						statement2.getParent().getString().startsWith("catch(")) {
					exceptionHandlingExactMatch = true;
				}
			}
			if(mapping.getFragment1().throwsNewException() && mapping.getFragment2().throwsNewException()) {
				throwsNewExceptionExactMatch = true;
			}
		}
		exactMatchList.addAll(additionalExactMatches);
		int exactMatches = exactMatchList.size();
		return mappings > 0 && (mappings > nonMappedElementsT2 || (mappings > 1 && mappings >= nonMappedElementsT2) ||
				(exactMatches >= mappings && nonMappedElementsT1 == 0) ||
				(exactMatches == 1 && !throwsNewExceptionExactMatch && nonMappedElementsT2-exactMatches <= 10) ||
				(!exceptionHandlingExactMatch && exactMatches > 1 && additionalExactMatches.size() < exactMatches && nonMappedElementsT2-exactMatches < 20) ||
				(mappings == 1 && mappings > operationBodyMapper.nonMappedLeafElementsT2())) ||
				argumentExtractedWithDefaultReturnAdded(operationBodyMapper);
	}

	private boolean argumentExtractedWithDefaultReturnAdded(UMLOperationBodyMapper operationBodyMapper) {
		List<AbstractCodeMapping> totalMappings = new ArrayList<AbstractCodeMapping>(operationBodyMapper.getMappings());
		List<CompositeStatementObject> nonMappedInnerNodesT2 = new ArrayList<CompositeStatementObject>(operationBodyMapper.getNonMappedInnerNodesT2());
		ListIterator<CompositeStatementObject> iterator = nonMappedInnerNodesT2.listIterator();
		while(iterator.hasNext()) {
			if(iterator.next().toString().equals("{")) {
				iterator.remove();
			}
		}
		List<StatementObject> nonMappedLeavesT2 = operationBodyMapper.getNonMappedLeavesT2();
		return totalMappings.size() == 1 && totalMappings.get(0).containsReplacement(ReplacementType.ARGUMENT_REPLACED_WITH_RETURN_EXPRESSION) &&
				nonMappedInnerNodesT2.size() == 1 && nonMappedInnerNodesT2.get(0).toString().startsWith("if") &&
				nonMappedLeavesT2.size() == 1 && nonMappedLeavesT2.get(0).toString().startsWith("return ");
	}

	public UMLOperation findDelegateMethod(UMLOperation originalOperation, UMLOperation addedOperation, OperationInvocation addedOperationInvocation) {
		OperationInvocation delegateMethodInvocation = addedOperation.isDelegate();
		if(originalOperation.isDelegate() == null && delegateMethodInvocation != null && !originalOperation.getAllOperationInvocations().contains(addedOperationInvocation)) {
			for(UMLOperation operation : addedOperations) {
				if(delegateMethodInvocation.matchesOperation(operation, addedOperation.variableTypeMap(), modelDiff)) {
					return operation;
				}
			}
		}
		return null;
	}

	private boolean parameterTypesMatch(Map<UMLParameter, UMLParameter> originalMethodParametersPassedAsArgumentsMappedToCalledMethodParameters) {
		for(UMLParameter key : originalMethodParametersPassedAsArgumentsMappedToCalledMethodParameters.keySet()) {
			UMLParameter value = originalMethodParametersPassedAsArgumentsMappedToCalledMethodParameters.get(key);
			if(!key.getType().equals(value.getType()) && !key.getType().equalsWithSubType(value.getType()) &&
					!modelDiff.isSubclassOf(key.getType().getClassType(), value.getType().getClassType())) {
				return false;
			}
		}
		return true;
	}
}
