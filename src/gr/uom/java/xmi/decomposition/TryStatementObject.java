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
