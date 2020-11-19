package gr.uom.java.xmi.decomposition;

import java.util.List;

public interface IAbstractStatement {

	void setParent(CompositeStatementObject parent);

	CompositeStatementObject getParent();

	String getString();

	VariableDeclaration searchVariableDeclaration(String variableName);

	List<StatementObject> getLeaves();

	int statementCount();

}