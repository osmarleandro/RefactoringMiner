package gr.uom.java.xmi.decomposition;

import java.util.ArrayList;
import java.util.List;

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
