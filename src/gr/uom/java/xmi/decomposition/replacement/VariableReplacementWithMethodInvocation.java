package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.OperationInvocation;

public class VariableReplacementWithMethodInvocation extends Replacement {
	private OperationInvocation invokedOperation;
	public Direction direction;
	
	public VariableReplacementWithMethodInvocation(String before, String after, OperationInvocation invocation, Direction direction) {
		super(before, after, ReplacementType.VARIABLE_REPLACED_WITH_METHOD_INVOCATION);
		this.invokedOperation = invocation;
		this.direction = direction;
	}

	public OperationInvocation getInvokedOperation() {
		return invokedOperation;
	}

	public enum Direction {
		VARIABLE_TO_INVOCATION, INVOCATION_TO_VARIABLE;
	}
}
