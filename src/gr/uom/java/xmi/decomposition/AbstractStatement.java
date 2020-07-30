package gr.uom.java.xmi.decomposition;

import java.util.List;

import gr.uom.java.xmi.LocationInfo.CodeElementType;

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

	public boolean isLoop() {
		return this.locationInfo.getCodeElementType().equals(CodeElementType.ENHANCED_FOR_STATEMENT) ||
				this.locationInfo.getCodeElementType().equals(CodeElementType.FOR_STATEMENT) ||
				this.locationInfo.getCodeElementType().equals(CodeElementType.WHILE_STATEMENT) ||
				this.locationInfo.getCodeElementType().equals(CodeElementType.DO_STATEMENT);
	}
}
