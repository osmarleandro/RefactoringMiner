package gr.uom.java.xmi.decomposition;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Statement;

import gr.uom.java.xmi.LocationInfo.CodeElementType;

public class TryStatementObject extends CompositeStatementObject_RENAMED {
	private List<CompositeStatementObject_RENAMED> catchClauses;
	private CompositeStatementObject_RENAMED finallyClause;

	public TryStatementObject(CompilationUnit cu, String filePath, Statement statement, int depth) {
		super(cu, filePath, statement, depth, CodeElementType.TRY_STATEMENT);
		this.catchClauses = new ArrayList<CompositeStatementObject_RENAMED>();
	}

	public void addCatchClause(CompositeStatementObject_RENAMED catchClause) {
		catchClauses.add(catchClause);
	}

	public List<CompositeStatementObject_RENAMED> getCatchClauses() {
		return catchClauses;
	}

	public void setFinallyClause(CompositeStatementObject_RENAMED finallyClause) {
		this.finallyClause = finallyClause;
	}

	public CompositeStatementObject_RENAMED getFinallyClause() {
		return finallyClause;
	}

	@Override
	public List<VariableDeclaration> getVariableDeclarations() {
		List<VariableDeclaration> variableDeclarations = new ArrayList<VariableDeclaration>();
		variableDeclarations.addAll(super.getVariableDeclarations());
		for(CompositeStatementObject_RENAMED catchClause : catchClauses) {
			variableDeclarations.addAll(catchClause.getVariableDeclarations());
		}
		return variableDeclarations;
	}
}
