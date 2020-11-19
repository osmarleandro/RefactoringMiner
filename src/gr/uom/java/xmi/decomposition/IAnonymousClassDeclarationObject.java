package gr.uom.java.xmi.decomposition;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;

import gr.uom.java.xmi.LocationInfo;
import gr.uom.java.xmi.diff.CodeRange;

public interface IAnonymousClassDeclarationObject {

	LocationInfo getLocationInfo();

	AnonymousClassDeclaration getAstNode();

	void setAstNode(AnonymousClassDeclaration node);

	String toString();

	Map<String, List<OperationInvocation>> getMethodInvocationMap();

	List<VariableDeclaration> getVariableDeclarations();

	List<String> getTypes();

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

	List<String> getVariables();

	List<LambdaExpressionObject> getLambdas();

	CodeRange codeRange();

}