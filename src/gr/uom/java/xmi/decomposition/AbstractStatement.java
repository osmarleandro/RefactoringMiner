package gr.uom.java.xmi.decomposition;

import java.util.ArrayList;
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

	public List<VariableDeclaration> getAllVariableDeclarations() {
		List<VariableDeclaration> variableDeclarations = new ArrayList<VariableDeclaration>();
		variableDeclarations.addAll(getVariableDeclarations());
		for(AbstractStatement statement : statementList) {
			if(statement instanceof CompositeStatementObject) {
				CompositeStatementObject composite = (CompositeStatementObject)statement;
				variableDeclarations.addAll(composite.getAllVariableDeclarations());
			}
			else if(statement instanceof StatementObject) {
				StatementObject statementObject = (StatementObject)statement;
				variableDeclarations.addAll(statementObject.getVariableDeclarations());
				for(LambdaExpressionObject lambda : statementObject.getLambdas()) {
					if(lambda.getBody() != null) {
						variableDeclarations.addAll(lambda.getBody().getAllVariableDeclarations());
					}
				}
			}
		}
		return variableDeclarations;
	}
}
