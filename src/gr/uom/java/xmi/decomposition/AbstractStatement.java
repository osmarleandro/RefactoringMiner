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

	public boolean contains(AbstractCodeFragment fragment) {
		if(fragment instanceof StatementObject) {
			return getLeaves().contains(fragment);
		}
		else if(fragment instanceof CompositeStatementObject) {
			return getInnerNodes().contains(fragment);
		}
		else if(fragment instanceof AbstractExpression) {
			return getExpressions().contains(fragment);
		}
		return false;
	}
}
