package gr.uom.java.xmi.decomposition;

import java.util.List;

public interface ITryStatementObject {

	void addCatchClause(CompositeStatementObject catchClause);

	List<CompositeStatementObject> getCatchClauses();

	void setFinallyClause(CompositeStatementObject finallyClause);

	CompositeStatementObject getFinallyClause();

	List<VariableDeclaration> getVariableDeclarations();

}