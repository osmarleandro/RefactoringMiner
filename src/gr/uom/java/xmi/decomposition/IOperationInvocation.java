package gr.uom.java.xmi.decomposition;

import java.util.Map;
import java.util.Set;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.UMLType;
import gr.uom.java.xmi.diff.UMLModelDiff;

public interface IOperationInvocation {

	IOperationInvocation update(String oldExpression, String newExpression);

	String getName();

	String getMethodName();

	int numberOfSubExpressions();

	boolean matchesOperation(UMLOperation operation);

	boolean matchesOperation(UMLOperation operation, Map<String, UMLType> variableTypeMap, UMLModelDiff modelDiff);

	boolean compatibleExpression(OperationInvocation other);

	boolean containsVeryLongSubExpression();

	Set<String> callChainIntersection(OperationInvocation other);

	double normalizedNameDistance(AbstractCall call);

	boolean equals(Object o);

	String toString();

	int hashCode();

	boolean identicalName(AbstractCall call);

	boolean typeInferenceMatch(UMLOperation operationToBeMatched, Map<String, UMLType> typeInferenceMapFromContext);

	boolean differentExpressionNameAndArguments(OperationInvocation other);

	boolean identicalWithExpressionCallChainDifference(OperationInvocation other);

}