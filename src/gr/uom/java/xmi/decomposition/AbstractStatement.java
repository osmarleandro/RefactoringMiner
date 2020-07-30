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

	public CompositeStatementObject loopWithVariables(String currentElementName, String collectionName) {
		for(CompositeStatementObject innerNode : getInnerNodes()) {
			if(innerNode.getLocationInfo().getCodeElementType().equals(CodeElementType.ENHANCED_FOR_STATEMENT)) {
				boolean currentElementNameMatched = false;
				for(VariableDeclaration declaration : innerNode.getVariableDeclarations()) {
					if(declaration.getVariableName().equals(currentElementName)) {
						currentElementNameMatched = true;
						break;
					}
				}
				boolean collectionNameMatched = false;
				for(AbstractExpression expression : innerNode.getExpressions()) {
					if(expression.getVariables().contains(collectionName)) {
						collectionNameMatched = true;
						break;
					}
				}
				if(currentElementNameMatched && collectionNameMatched) {
					return innerNode;
				}
			}
			else if(innerNode.getLocationInfo().getCodeElementType().equals(CodeElementType.FOR_STATEMENT) ||
					innerNode.getLocationInfo().getCodeElementType().equals(CodeElementType.WHILE_STATEMENT)) {
				boolean collectionNameMatched = false;
				for(AbstractExpression expression : innerNode.getExpressions()) {
					if(expression.getVariables().contains(collectionName)) {
						collectionNameMatched = true;
						break;
					}
				}
				boolean currentElementNameMatched = false;
				for(StatementObject statement : innerNode.getLeaves()) {
					VariableDeclaration variableDeclaration = statement.getVariableDeclaration(currentElementName);
					if(variableDeclaration != null && statement.getVariables().contains(collectionName)) {
						currentElementNameMatched = true;
						break;
					}
				}
				if(currentElementNameMatched && collectionNameMatched) {
					return innerNode;
				}
			}
		}
		return null;
	}
}
