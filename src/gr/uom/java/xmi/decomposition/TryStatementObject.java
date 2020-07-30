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

	public CompositeStatementObject loopWithVariables(String currentElementName, String collectionName) {
		for(CompositeStatementObject innerNode : getInnerNodes()) {
			if(innerNode.getLocationInfo().getCodeElementType().equals(CodeElementType.ENHANCED_FOR_STATEMENT)) {
				boolean currentElementNameMatched = false;
				for(VariableDeclaration declaration : innerNode.getVariableDeclarations()) {
					if(declaration.getVariableName().equals(currentElementName)) {
						currentElementNameMatched = true;
						break;
					}
				}
				boolean collectionNameMatched = false;
				for(AbstractExpression expression : innerNode.getExpressions()) {
					if(expression.getVariables().contains(collectionName)) {
						collectionNameMatched = true;
						break;
					}
				}
				if(currentElementNameMatched && collectionNameMatched) {
					return innerNode;
				}
			}
			else if(innerNode.getLocationInfo().getCodeElementType().equals(CodeElementType.FOR_STATEMENT) ||
					innerNode.getLocationInfo().getCodeElementType().equals(CodeElementType.WHILE_STATEMENT)) {
				boolean collectionNameMatched = false;
				for(AbstractExpression expression : innerNode.getExpressions()) {
					if(expression.getVariables().contains(collectionName)) {
						collectionNameMatched = true;
						break;
					}
				}
				boolean currentElementNameMatched = false;
				for(StatementObject statement : innerNode.getLeaves()) {
					VariableDeclaration variableDeclaration = statement.getVariableDeclaration(currentElementName);
					if(variableDeclaration != null && statement.getVariables().contains(collectionName)) {
						currentElementNameMatched = true;
						break;
					}
				}
				if(currentElementNameMatched && collectionNameMatched) {
					return innerNode;
				}
			}
		}
		return null;
	}
}
