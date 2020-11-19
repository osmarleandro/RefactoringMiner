package gr.uom.java.xmi.decomposition;

import java.util.List;
import java.util.Map;
import java.util.Set;

import gr.uom.java.xmi.LocationInfo;
import gr.uom.java.xmi.diff.CodeRange;

public interface ICompositeStatementObject {

	void addStatement(AbstractStatement statement);

	List<AbstractStatement> getStatements();

	void addExpression(AbstractExpression expression);

	List<AbstractExpression> getExpressions();

	void addVariableDeclaration(VariableDeclaration declaration);

	List<StatementObject> getLeaves();

	List<CompositeStatementObject> getInnerNodes();

	boolean contains(AbstractCodeFragment fragment);

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

	List<String> getInfixOperators();

	List<String> getArrayAccesses();

	List<String> getPrefixExpressions();

	List<String> getPostfixExpressions();

	List<String> getArguments();

	List<TernaryOperatorExpression> getTernaryOperatorExpressions();

	List<LambdaExpressionObject> getLambdas();

	Map<String, List<ObjectCreation>> getCreationMap();

	Map<String, List<OperationInvocation>> getAllMethodInvocations();

	List<AnonymousClassDeclarationObject> getAllAnonymousClassDeclarations();

	List<LambdaExpressionObject> getAllLambdas();

	List<String> getAllVariables();

	List<VariableDeclaration> getAllVariableDeclarations();

	List<VariableDeclaration> getVariableDeclarationsInScope(LocationInfo location);

	int statementCount();

	LocationInfo getLocationInfo();

	VariableDeclaration getVariableDeclaration(String variableName);

	Map<String, Set<String>> aliasedAttributes();

	CodeRange codeRange();

	boolean isLoop();

	ICompositeStatementObject loopWithVariables(String currentElementName, String collectionName);

}