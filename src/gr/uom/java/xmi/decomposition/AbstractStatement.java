package gr.uom.java.xmi.decomposition;

import java.util.List;
import java.util.Map;

import gr.uom.java.xmi.LocationInfo.CodeElementType;
import gr.uom.java.xmi.decomposition.AbstractCall.StatementCoverageType;

public abstract class AbstractStatement extends AbstractCodeFragment {
	private CompositeStatementObject parent;
	
	public void setParent(CompositeStatementObject parent) {
    	this.parent = parent;
    }

    public CompositeStatementObject getParent() {
    	return this.parent;
    }

	public String getString() {
    	return toString();
    }

    public VariableDeclaration searchVariableDeclaration(String variableName) {
    	VariableDeclaration variableDeclaration = this.getVariableDeclaration(variableName);
    	if(variableDeclaration != null) {
    		return variableDeclaration;
    	}
    	else if(parent != null) {
    		return parent.searchVariableDeclaration(variableName);
    	}
    	return null;
    }

    public abstract List<StatementObject> getLeaves();
    public abstract int statementCount();

	public OperationInvocation invocationCoveringEntireFragment() {
		Map<String, List<OperationInvocation>> methodInvocationMap = getMethodInvocationMap();
		String statement = getString();
		for(String methodInvocation : methodInvocationMap.keySet()) {
			List<OperationInvocation> invocations = methodInvocationMap.get(methodInvocation);
			for(OperationInvocation invocation : invocations) {
				if((methodInvocation + ";\n").equals(statement) || methodInvocation.equals(statement)) {
					invocation.coverage = StatementCoverageType.ONLY_CALL;
					return invocation;
				}
				else if(("return " + methodInvocation + ";\n").equals(statement)) {
					invocation.coverage = StatementCoverageType.RETURN_CALL;
					return invocation;
				}
				else if(isCastExpressionCoveringEntireFragment(methodInvocation)) {
					invocation.coverage = StatementCoverageType.CAST_CALL;
					return invocation;
				}
				else if(expressionIsTheInitializerOfVariableDeclaration(methodInvocation)) {
					invocation.coverage = StatementCoverageType.VARIABLE_DECLARATION_INITIALIZER_CALL;
					return invocation;
				}
				else if(invocation.getLocationInfo().getCodeElementType().equals(CodeElementType.SUPER_CONSTRUCTOR_INVOCATION) ||
						invocation.getLocationInfo().getCodeElementType().equals(CodeElementType.CONSTRUCTOR_INVOCATION)) {
					invocation.coverage = StatementCoverageType.ONLY_CALL;
					return invocation;
				}
			}
		}
		return null;
	}
}
