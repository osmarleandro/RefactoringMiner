package gr.uom.java.xmi.diff;

import java.util.ArrayList;
import java.util.List;

import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;
import gr.uom.java.xmi.decomposition.CompositeStatementObject;
import gr.uom.java.xmi.decomposition.OperationInvocation;
import gr.uom.java.xmi.decomposition.StatementObject;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper;

public class UMLClassMoveDiff extends UMLClassBaseDiff {
	
	public UMLClassMoveDiff(UMLClass originalClass, UMLClass movedClass, UMLModelDiff modelDiff) {
		super(originalClass, movedClass, modelDiff);
	}

	public UMLClass getMovedClass() {
		return nextClass;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ");
		sb.append(originalClass.getName());
		sb.append(" was moved to ");
		sb.append(nextClass.getName());
		sb.append("\n");
		return sb.toString();
	}

	public boolean equals(Object o) {
		if(this == o) {
    		return true;
    	}
		
		if(o instanceof UMLClassMoveDiff) {
			UMLClassMoveDiff classMoveDiff = (UMLClassMoveDiff)o;
			return this.originalClass.equals(classMoveDiff.originalClass) && this.nextClass.equals(classMoveDiff.nextClass);
		}
		return false;
	}

	private boolean exactMappings(UMLOperationBodyMapper operationBodyMapper) {
		if(allMappingsAreExactMatches(operationBodyMapper)) {
			if(operationBodyMapper.nonMappedElementsT1() == 0 && operationBodyMapper.nonMappedElementsT2() == 0)
				return true;
			else if(operationBodyMapper.nonMappedElementsT1() > 0 && operationBodyMapper.getNonMappedInnerNodesT1().size() == 0 && operationBodyMapper.nonMappedElementsT2() == 0) {
				int countableStatements = 0;
				int parameterizedVariableDeclarationStatements = 0;
				UMLOperation addedOperation = operationBodyMapper.getOperation2();
				List<String> nonMappedLeavesT1 = new ArrayList<String>();
				for(StatementObject statement : operationBodyMapper.getNonMappedLeavesT1()) {
					if(statement.countableStatement()) {
						nonMappedLeavesT1.add(statement.getString());
						for(String parameterName : addedOperation.getParameterNameList()) {
							if(statement.getVariableDeclaration(parameterName) != null) {
								parameterizedVariableDeclarationStatements++;
								break;
							}
						}
						countableStatements++;
					}
				}
				int nonMappedLeavesExactlyMatchedInTheBodyOfAddedOperation = 0;
				for(UMLOperation operation : addedOperations) {
					if(!operation.equals(addedOperation) && operation.getBody() != null) {
						for(StatementObject statement : operation.getBody().getCompositeStatement().getLeaves()) {
							if(nonMappedLeavesT1.contains(statement.getString())) {
								nonMappedLeavesExactlyMatchedInTheBodyOfAddedOperation++;
							}
						}
					}
				}
				return (countableStatements == parameterizedVariableDeclarationStatements || countableStatements == nonMappedLeavesExactlyMatchedInTheBodyOfAddedOperation + parameterizedVariableDeclarationStatements) && countableStatements > 0;
			}
			else if(operationBodyMapper.nonMappedElementsT1() == 0 && operationBodyMapper.nonMappedElementsT2() > 0 && operationBodyMapper.getNonMappedInnerNodesT2().size() == 0) {
				int countableStatements = 0;
				int parameterizedVariableDeclarationStatements = 0;
				UMLOperation removedOperation = operationBodyMapper.getOperation1();
				for(StatementObject statement : operationBodyMapper.getNonMappedLeavesT2()) {
					if(statement.countableStatement()) {
						for(String parameterName : removedOperation.getParameterNameList()) {
							if(statement.getVariableDeclaration(parameterName) != null) {
								parameterizedVariableDeclarationStatements++;
								break;
							}
						}
						countableStatements++;
					}
				}
				return countableStatements == parameterizedVariableDeclarationStatements && countableStatements > 0;
			}
			else if((operationBodyMapper.nonMappedElementsT1() == 1 || operationBodyMapper.nonMappedElementsT2() == 1) &&
					operationBodyMapper.getNonMappedInnerNodesT1().size() == 0 && operationBodyMapper.getNonMappedInnerNodesT2().size() == 0) {
				StatementObject statementUsingParameterAsInvoker1 = null;
				UMLOperation removedOperation = operationBodyMapper.getOperation1();
				for(StatementObject statement : operationBodyMapper.getNonMappedLeavesT1()) {
					if(statement.countableStatement()) {
						for(String parameterName : removedOperation.getParameterNameList()) {
							OperationInvocation invocation = statement.invocationCoveringEntireFragment();
							if(invocation != null && invocation.getExpression() != null && invocation.getExpression().equals(parameterName)) {
								statementUsingParameterAsInvoker1 = statement;
								break;
							}
						}
					}
				}
				StatementObject statementUsingParameterAsInvoker2 = null;
				UMLOperation addedOperation = operationBodyMapper.getOperation2();
				for(StatementObject statement : operationBodyMapper.getNonMappedLeavesT2()) {
					if(statement.countableStatement()) {
						for(String parameterName : addedOperation.getParameterNameList()) {
							OperationInvocation invocation = statement.invocationCoveringEntireFragment();
							if(invocation != null && invocation.getExpression() != null && invocation.getExpression().equals(parameterName)) {
								statementUsingParameterAsInvoker2 = statement;
								break;
							}
						}
					}
				}
				if(statementUsingParameterAsInvoker1 != null && statementUsingParameterAsInvoker2 != null) {
					for(AbstractCodeMapping mapping : operationBodyMapper.getMappings()) {
						if(mapping.getFragment1() instanceof CompositeStatementObject && mapping.getFragment2() instanceof CompositeStatementObject) {
							CompositeStatementObject parent1 = (CompositeStatementObject)mapping.getFragment1();
							CompositeStatementObject parent2 = (CompositeStatementObject)mapping.getFragment2();
							if(parent1.getLeaves().contains(statementUsingParameterAsInvoker1) && parent2.getLeaves().contains(statementUsingParameterAsInvoker2)) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
}
