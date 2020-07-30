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

	public Map<String, List<OperationInvocation>> getAllMethodInvocations() {
		Map<String, List<OperationInvocation>> map = new LinkedHashMap<String, List<OperationInvocation>>();
		map.putAll(getMethodInvocationMap());
		for(AbstractStatement statement : statementList) {
			if(statement instanceof CompositeStatementObject) {
				CompositeStatementObject composite = (CompositeStatementObject)statement;
				Map<String, List<OperationInvocation>> compositeMap = composite.getAllMethodInvocations();
				for(String key : compositeMap.keySet()) {
					if(map.containsKey(key)) {
						map.get(key).addAll(compositeMap.get(key));
					}
					else {
						List<OperationInvocation> list = new ArrayList<OperationInvocation>();
						list.addAll(compositeMap.get(key));
						map.put(key, list);
					}
				}
			}
			else if(statement instanceof StatementObject) {
				StatementObject statementObject = (StatementObject)statement;
				Map<String, List<OperationInvocation>> statementMap = statementObject.getMethodInvocationMap();
				for(String key : statementMap.keySet()) {
					if(map.containsKey(key)) {
						map.get(key).addAll(statementMap.get(key));
					}
					else {
						List<OperationInvocation> list = new ArrayList<OperationInvocation>();
						list.addAll(statementMap.get(key));
						map.put(key, list);
					}
				}
				for(LambdaExpressionObject lambda : statementObject.getLambdas()) {
					if(lambda.getBody() != null) {
						Map<String, List<OperationInvocation>> lambdaMap = lambda.getBody().getCompositeStatement().getAllMethodInvocations();
						for(String key : lambdaMap.keySet()) {
							if(map.containsKey(key)) {
								map.get(key).addAll(lambdaMap.get(key));
							}
							else {
								List<OperationInvocation> list = new ArrayList<OperationInvocation>();
								list.addAll(lambdaMap.get(key));
								map.put(key, list);
							}
						}
					}
				}
			}
		}
		return map;
	}
}
