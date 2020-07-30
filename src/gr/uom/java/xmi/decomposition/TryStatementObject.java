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

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(locationInfo.getCodeElementType().getName());
		if(expressionList.size() > 0) {
			sb.append("(");
			for(int i=0; i<expressionList.size()-1; i++) {
				sb.append(expressionList.get(i).toString()).append("; ");
			}
			sb.append(expressionList.get(expressionList.size()-1).toString());
			sb.append(")");
		}
		return sb.toString();
	}
}
