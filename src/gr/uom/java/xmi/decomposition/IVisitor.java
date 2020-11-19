package gr.uom.java.xmi.decomposition;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WildcardType;

public interface IVisitor {

	boolean visit(ArrayAccess node);

	boolean visit(PrefixExpression node);

	boolean visit(PostfixExpression node);

	boolean visit(ConditionalExpression node);

	boolean visit(InfixExpression node);

	boolean visit(ClassInstanceCreation node);

	boolean visit(ArrayCreation node);

	boolean visit(VariableDeclarationFragment node);

	boolean visit(SingleVariableDeclaration node);

	boolean visit(AnonymousClassDeclaration node);

	void endVisit(AnonymousClassDeclaration node);

	boolean visit(StringLiteral node);

	boolean visit(NumberLiteral node);

	boolean visit(NullLiteral node);

	boolean visit(BooleanLiteral node);

	boolean visit(TypeLiteral node);

	boolean visit(ThisExpression node);

	boolean visit(SimpleName node);

	boolean visit(ArrayType node);

	boolean visit(ParameterizedType node);

	boolean visit(WildcardType node);

	boolean visit(QualifiedType node);

	boolean visit(PrimitiveType node);

	boolean visit(SimpleType node);

	boolean visit(MethodInvocation node);

	boolean visit(SuperMethodInvocation node);

	boolean visit(SuperConstructorInvocation node);

	boolean visit(ConstructorInvocation node);

	boolean visit(QualifiedName node);

	boolean visit(CastExpression node);

	boolean visit(LambdaExpression node);

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

}