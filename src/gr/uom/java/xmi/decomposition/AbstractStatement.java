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

	private boolean isCastExpressionCoveringEntireFragment(String expression) {
		String statement = getString();
		int index = statement.indexOf(expression + ";\n");
		if(index != -1) {
			String prefix = statement.substring(0, index);
			if(prefix.contains("(") && prefix.contains(")")) {
				String casting = prefix.substring(prefix.indexOf("("), prefix.indexOf(")")+1);
				if(("return " + casting + expression + ";\n").equals(statement)) {
					return true;
				}
			}
		}
		return false;
	}
}
