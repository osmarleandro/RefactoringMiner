package gr.uom.java.xmi.decomposition;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;

import gr.uom.java.xmi.LocationInfo.CodeElementType;
import gr.uom.java.xmi.decomposition.replacement.Replacement;
import gr.uom.java.xmi.decomposition.replacement.Replacement.ReplacementType;

public class TernaryOperatorExpression {

	private AbstractExpression_RENAMED condition;
	private AbstractExpression_RENAMED thenExpression;
	private AbstractExpression_RENAMED elseExpression;
	private String expression;

	public TernaryOperatorExpression(CompilationUnit cu, String filePath, ConditionalExpression expression) {
		this.condition = new AbstractExpression_RENAMED(cu, filePath, expression.getExpression(), CodeElementType.TERNARY_OPERATOR_CONDITION);
		this.thenExpression = new AbstractExpression_RENAMED(cu, filePath, expression.getThenExpression(), CodeElementType.TERNARY_OPERATOR_THEN_EXPRESSION);
		this.elseExpression = new AbstractExpression_RENAMED(cu, filePath, expression.getElseExpression(), CodeElementType.TERNARY_OPERATOR_ELSE_EXPRESSION);
		this.expression = expression.toString();
	}

	public AbstractExpression_RENAMED getCondition() {
		return condition;
	}

	public AbstractExpression_RENAMED getThenExpression() {
		return thenExpression;
	}

	public AbstractExpression_RENAMED getElseExpression() {
		return elseExpression;
	}

	public String getExpression() {
		return expression;
	}

	public Replacement makeReplacementWithTernaryOnTheRight(String statement) {
		if(getElseExpression().getString().equals(statement)) {
			return new Replacement(statement, getExpression(), ReplacementType.EXPRESSION_REPLACED_WITH_TERNARY_ELSE);
		}
		if(getThenExpression().getString().equals(statement)) {
			return new Replacement(statement, getExpression(), ReplacementType.EXPRESSION_REPLACED_WITH_TERNARY_THEN);
		}
		return null;
	}

	public Replacement makeReplacementWithTernaryOnTheLeft(String statement) {
		if(getElseExpression().getString().equals(statement)) {
			return new Replacement(getExpression(), statement, ReplacementType.EXPRESSION_REPLACED_WITH_TERNARY_ELSE);
		}
		if(getThenExpression().getString().equals(statement)) {
			return new Replacement(getExpression(), statement, ReplacementType.EXPRESSION_REPLACED_WITH_TERNARY_THEN);
		}
		return null;
	}
}
