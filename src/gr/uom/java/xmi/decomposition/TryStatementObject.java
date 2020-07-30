package gr.uom.java.xmi.decomposition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Statement;

import gr.uom.java.xmi.LocationInfo.CodeElementType;

public class TryStatementObject extends CompositeStatementObject {
	private List<CompositeStatementObject> catchClauses;
	private CompositeStatementObject finallyClause;

	public TryStatementObject(CompilationUnit cu, String filePath, Statement statement, int depth) {
		super(cu, filePath, statement, depth, CodeElementType.TRY_STATEMENT);
		this.catchClauses = new ArrayList<CompositeStatementObject>();
	}

	public void addCatchClause(CompositeStatementObject catchClause) {
		catchClauses.add(catchClause);
	}

	public List<CompositeStatementObject> getCatchClauses() {
		return catchClauses;
	}

	public void setFinallyClause(CompositeStatementObject finallyClause) {
		this.finallyClause = finallyClause;
	}

	public CompositeStatementObject getFinallyClause() {
		return finallyClause;
	}

	@Override
	public List<VariableDeclaration> getVariableDeclarations() {
		List<VariableDeclaration> variableDeclarations = new ArrayList<VariableDeclaration>();
		variableDeclarations.addAll(super.getVariableDeclarations());
		for(CompositeStatementObject catchClause : catchClauses) {
			variableDeclarations.addAll(catchClause.getVariableDeclarations());
		}
		return variableDeclarations;
	}

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
