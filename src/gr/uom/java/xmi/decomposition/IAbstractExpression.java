package gr.uom.java.xmi.decomposition;

import java.util.List;
import java.util.Map;

import gr.uom.java.xmi.LocationInfo;
import gr.uom.java.xmi.diff.CodeRange;

public interface IAbstractExpression {

	void setOwner(CompositeStatementObject owner);

	CompositeStatementObject getOwner();

	CompositeStatementObject getParent();

	String getExpression();

	String getString();

	String toString();

	List<String> getVariables();

	List<String> getTypes();

	List<VariableDeclaration> getVariableDeclarations();

	Map<String, List<OperationInvocation>> getMethodInvocationMap();

	List<AnonymousClassDeclarationObject> getAnonymousClassDeclarations();

	List<String> getStringLiterals();

	List<String> getNumberLiterals();

	List<String> getNullLiterals();

	List<String> getBooleanLiterals();

	List<String> getTypeLiterals();

	Map<String, List<ObjectCreation>> getCreationMap();

	List<String> getInfixOperators();

	List<String> getArrayAccesses();

	List<String> getPrefixExpressions();

	List<String> getPostfixExpressions();

	List<String> getArguments();

	List<TernaryOperatorExpression> getTernaryOperatorExpressions();

	List<LambdaExpressionObject> getLambdas();

	LocationInfo getLocationInfo();

	VariableDeclaration searchVariableDeclaration(String variableName);

	VariableDeclaration getVariableDeclaration(String variableName);

	CodeRange codeRange();

}