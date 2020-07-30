package gr.uom.java.xmi.decomposition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
