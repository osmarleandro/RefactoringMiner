package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.OperationInvocation_RENAMED;

public class VariableReplacementWithMethodInvocation extends Replacement {
	private OperationInvocation_RENAMED invokedOperation;
	private Direction direction;
	
	public VariableReplacementWithMethodInvocation(String before, String after, OperationInvocation_RENAMED invocation, Direction direction) {
		super(before, after, ReplacementType.VARIABLE_REPLACED_WITH_METHOD_INVOCATION);
		this.invokedOperation = invocation;
		this.direction = direction;
	}

	public OperationInvocation_RENAMED getInvokedOperation() {
		return invokedOperation;
	}

	public Direction getDirection() {
		return direction;
	}

	public enum Direction {
		VARIABLE_TO_INVOCATION, INVOCATION_TO_VARIABLE;
	}
}
