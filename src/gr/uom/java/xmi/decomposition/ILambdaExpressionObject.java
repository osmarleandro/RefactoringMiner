package gr.uom.java.xmi.decomposition;

import gr.uom.java.xmi.LocationInfo;
import gr.uom.java.xmi.diff.CodeRange;

public interface ILambdaExpressionObject {

	OperationBody getBody();

	AbstractExpression getExpression();

	LocationInfo getLocationInfo();

	CodeRange codeRange();

}