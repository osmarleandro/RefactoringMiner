package gr.uom.java.xmi.decomposition;

import java.util.List;
import java.util.Map;

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

	public OperationInvocation assignmentInvocationCoveringEntireStatement() {
		Map<String, List<OperationInvocation>> methodInvocationMap = getMethodInvocationMap();
		for(String methodInvocation : methodInvocationMap.keySet()) {
			List<OperationInvocation> invocations = methodInvocationMap.get(methodInvocation);
			for(OperationInvocation invocation : invocations) {
				if(expressionIsTheRightHandSideOfAssignment(methodInvocation)) {
					return invocation;
				}
			}
		}
		return null;
	}
}
