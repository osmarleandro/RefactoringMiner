package gr.uom.java.xmi.decomposition;

import java.util.List;
import java.util.Map;

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

	public ObjectCreation creationCoveringEntireFragment() {
		Map<String, List<ObjectCreation>> creationMap = getCreationMap();
		String statement = getString();
		for(String objectCreation : creationMap.keySet()) {
			List<ObjectCreation> creations = creationMap.get(objectCreation);
			for(ObjectCreation creation : creations) {
				if((objectCreation + ";\n").equals(statement) || objectCreation.equals(statement)) {
					creation.coverage = StatementCoverageType.ONLY_CALL;
					return creation;
				}
				else if(("return " + objectCreation + ";\n").equals(statement)) {
					creation.coverage = StatementCoverageType.RETURN_CALL;
					return creation;
				}
				else if(("throw " + objectCreation + ";\n").equals(statement)) {
					creation.coverage = StatementCoverageType.THROW_CALL;
					return creation;
				}
				else if(expressionIsTheInitializerOfVariableDeclaration(objectCreation)) {
					creation.coverage = StatementCoverageType.VARIABLE_DECLARATION_INITIALIZER_CALL;
					return creation;
				}
			}
		}
		return null;
	}
}
