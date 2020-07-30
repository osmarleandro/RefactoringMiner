package gr.uom.java.xmi.decomposition;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	public Map<String, Set<String>> aliasedAttributes() {
		Map<String, Set<String>> map = new LinkedHashMap<String, Set<String>>();
		for(StatementObject statement : getLeaves()) {
			String s = statement.getString();
			if(s.startsWith("this.") && s.endsWith(";\n")) {
				String firstLine = s.substring(0, s.indexOf("\n"));
				if(firstLine.contains("=")) {
					String attribute = s.substring(5, s.indexOf("="));
					String value = s.substring(s.indexOf("=")+1, s.indexOf(";\n"));
					if(map.containsKey(value)) {
						map.get(value).add(attribute);
					}
					else {
						Set<String> set = new LinkedHashSet<String>();
						set.add(attribute);
						map.put(value, set);
					}
				}
			}
		}
		Set<String> keysToBeRemoved = new LinkedHashSet<String>();
		for(String key : map.keySet()) {
			if(map.get(key).size() <= 1) {
				keysToBeRemoved.add(key);
			}
		}
		for(String key : keysToBeRemoved) {
			map.remove(key);
		}
		return map;
	}
}
