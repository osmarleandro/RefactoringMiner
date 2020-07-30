package gr.uom.java.xmi.decomposition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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

	@Override
	public Map<String, List<OperationInvocation>> getMethodInvocationMap() {
		Map<String, List<OperationInvocation>> map = new LinkedHashMap<String, List<OperationInvocation>>();
		for(AbstractExpression expression : expressionList) {
			Map<String, List<OperationInvocation>> expressionMap = expression.getMethodInvocationMap();
			for(String key : expressionMap.keySet()) {
				if(map.containsKey(key)) {
					map.get(key).addAll(expressionMap.get(key));
				}
				else {
					List<OperationInvocation> list = new ArrayList<OperationInvocation>();
					list.addAll(expressionMap.get(key));
					map.put(key, list);
				}
			}
		}
		return map;
	}
}
