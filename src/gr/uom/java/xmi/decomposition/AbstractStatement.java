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

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(locationInfo.getCodeElementType().getName());
		if(expressionList.size() > 0) {
			sb.append("(");
			for(int i=0; i<expressionList.size()-1; i++) {
				sb.append(expressionList.get(i).toString()).append("; ");
			}
			sb.append(expressionList.get(expressionList.size()-1).toString());
			sb.append(")");
		}
		return sb.toString();
	}
}
