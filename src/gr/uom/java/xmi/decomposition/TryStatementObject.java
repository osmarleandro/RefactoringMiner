package gr.uom.java.xmi.decomposition;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Statement;

import gr.uom.java.xmi.LocationInfo.CodeElementType;
import gr.uom.java.xmi.UMLOperation;

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

	double compositeChildMatchingScore(UMLOperationBodyMapper umlOperationBodyMapper, TryStatementObject try2, Set<AbstractCodeMapping> mappings, List<UMLOperation> removedOperations, List<UMLOperation> addedOperations) {
		double score = umlOperationBodyMapper.compositeChildMatchingScore((CompositeStatementObject)this, (CompositeStatementObject)try2, mappings, removedOperations, addedOperations);
		List<CompositeStatementObject> catchClauses1 = getCatchClauses();
		List<CompositeStatementObject> catchClauses2 = try2.getCatchClauses();
		if(catchClauses1.size() == catchClauses2.size()) {
			for(int i=0; i<catchClauses1.size(); i++) {
				double tmpScore = umlOperationBodyMapper.compositeChildMatchingScore(catchClauses1.get(i), catchClauses2.get(i), mappings, removedOperations, addedOperations);
				if(tmpScore == 1) {
					score += tmpScore;
				}
			}
		}
		if(getFinallyClause() != null && try2.getFinallyClause() != null) {
			double tmpScore = umlOperationBodyMapper.compositeChildMatchingScore(getFinallyClause(), try2.getFinallyClause(), mappings, removedOperations, addedOperations);
			if(tmpScore == 1) {
				score += tmpScore;
			}
		}
		return score;
	}
}
