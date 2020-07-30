package gr.uom.java.xmi.decomposition;

import java.util.List;

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

	private boolean expressionIsTheRightHandSideOfAssignment(String expression) {
		String statement = getString();
		if(statement.contains("=")) {
			List<String> variables = getVariables();
			if(variables.size() > 0) {
				String s = variables.get(0) + "=" + expression + ";\n";
				if(statement.equals(s)) {
					return true;
				}
			}
		}
		return false;
	}
}
