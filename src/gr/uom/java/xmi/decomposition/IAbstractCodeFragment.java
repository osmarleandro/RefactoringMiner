package gr.uom.java.xmi.decomposition;

import java.util.List;
import java.util.Map;

public interface IAbstractCodeFragment {

	String getArgumentizedString();

	int getDepth();

	void setDepth(int depth);

	int getIndex();

	void setIndex(int index);

	CompositeStatementObject getParent();

	String getString();

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

	VariableDeclaration searchVariableDeclaration(String variableName);

	VariableDeclaration getVariableDeclaration(String variableName);

	void replaceParametersWithArguments(Map<String, String> parameterToArgumentMap);

	boolean equalFragment(AbstractCodeFragment other);

	void resetArgumentization();

	ObjectCreation creationCoveringEntireFragment();

	OperationInvocation invocationCoveringEntireFragment();

	OperationInvocation assignmentInvocationCoveringEntireStatement();

	boolean throwsNewException();

	boolean countableStatement();

}