package gr.uom.java.xmi.decomposition;

import gr.uom.java.xmi.decomposition.replacement.Replacement;

public interface ITernaryOperatorExpression {

	AbstractExpression getCondition();

	AbstractExpression getThenExpression();

	AbstractExpression getElseExpression();

	String getExpression();

	Replacement makeReplacementWithTernaryOnTheRight(String statement);

	Replacement makeReplacementWithTernaryOnTheLeft(String statement);

}