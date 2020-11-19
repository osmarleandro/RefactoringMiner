package gr.uom.java.xmi;

import java.util.List;
import java.util.Map;
import java.util.Set;

import gr.uom.java.xmi.decomposition.CompositeStatementObject;
import gr.uom.java.xmi.decomposition.LambdaExpressionObject;
import gr.uom.java.xmi.decomposition.OperationBody;
import gr.uom.java.xmi.decomposition.OperationInvocation;
import gr.uom.java.xmi.decomposition.VariableDeclaration;
import gr.uom.java.xmi.diff.CodeRange;

public interface IUMLOperation {

	List<UMLTypeParameter> getTypeParameters();

	void addTypeParameter(UMLTypeParameter typeParameter);

	List<UMLAnnotation> getAnnotations();

	void addAnnotation(UMLAnnotation annotation);

	LocationInfo getLocationInfo();

	String getName();

	String getVisibility();

	void setVisibility(String visibility);

	boolean isAbstract();

	void setAbstract(boolean isAbstract);

	boolean isConstructor();

	void setConstructor(boolean isConstructor);

	boolean isFinal();

	void setFinal(boolean isFinal);

	boolean isStatic();

	void setStatic(boolean isStatic);

	boolean hasEmptyBody();

	void setEmptyBody(boolean emptyBody);

	OperationBody getBody();

	boolean hasTestAnnotation();

	UMLJavadoc getJavadoc();

	void setJavadoc(UMLJavadoc javadoc);

	List<OperationInvocation> getAllOperationInvocations();

	List<LambdaExpressionObject> getAllLambdas();

	List<String> getAllVariables();

	List<VariableDeclaration> getAllVariableDeclarations();

	List<VariableDeclaration> getVariableDeclarationsInScope(LocationInfo location);

	VariableDeclaration getVariableDeclaration(String variableName);

	Map<String, UMLType> variableTypeMap();

	int statementCount();

	void setBody(OperationBody body);

	String getNonQualifiedClassName();

	String getClassName();

	void setClassName(String className);

	void addParameter(UMLParameter parameter);

	List<UMLParameter> getParameters();

	void addAnonymousClass(UMLAnonymousClass anonymous);

	List<UMLAnonymousClass> getAnonymousClassList();

	UMLParameter getReturnParameter();

	boolean equalReturnParameter(IUMLOperation operation);

	boolean equalQualifiedReturnParameter(IUMLOperation operation);

	boolean equalSignature(UMLOperation operation);

	boolean equalSignatureIgnoringOperationName(UMLOperation operation);

	boolean equalSignatureIgnoringChangedTypes(UMLOperation operation);

	boolean equalSignatureWithIdenticalNameIgnoringChangedTypes(UMLOperation operation);

	List<UMLParameter> getParametersWithoutReturnType();

	List<UMLType> commonParameterTypes(IUMLOperation operation);

	List<UMLType> getParameterTypeList();

	List<String> getParameterNameList();

	int getNumberOfNonVarargsParameters();

	boolean hasVarargsParameter();

	OperationInvocation isDelegate();

	boolean isGetter();

	boolean isSetter();

	boolean equalsIgnoringVisibility(UMLOperation operation);

	boolean equalsIgnoringNameCase(UMLOperation operation);

	boolean equals(Object o);

	boolean equalsQualified(UMLOperation operation);

	int hashCode();

	String toString();

	String toQualifiedString();

	String getKey();

	int compareTo(IUMLOperation operation);

	double normalizedNameDistance(IUMLOperation operation);

	boolean equalParameters(IUMLOperation operation);

	boolean equalParameterTypes(UMLOperation operation);

	boolean equalParameterNames(IUMLOperation operation);

	boolean overloadedParameters(IUMLOperation operation);

	boolean overloadedParameterTypes(IUMLOperation operation);

	boolean replacedParameterTypes(IUMLOperation operation);

	List<UMLOperation> getOperationsInsideAnonymousClass(List<UMLAnonymousClass> allAddedAnonymousClasses);

	CodeRange codeRange();

	boolean overridesObject();

	boolean compatibleSignature(UMLOperation removedOperation);

	boolean hasTwoParametersWithTheSameType();

	Map<String, Set<String>> aliasedAttributes();

	CompositeStatementObject loopWithVariables(String currentElementName, String collectionName);

}