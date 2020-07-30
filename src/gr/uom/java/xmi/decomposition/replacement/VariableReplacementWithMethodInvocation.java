package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.OperationInvocation;

public class VariableReplacementWithMethodInvocation extends Replacement {
	private OperationInvocation invokedOperation;
	private Direction direction;
	
	public VariableReplacementWithMethodInvocation(String before, String after, OperationInvocation invocation, Direction direction) {
		super(before, after, ReplacementType.VARIABLE_REPLACED_WITH_METHOD_INVOCATION);
		this.invokedOperation = invocation;
		this.direction = direction;
	}

	public OperationInvocation getInvokedOperation() {
		return invokedOperation;
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if(obj instanceof Replacement) {
			Replacement other = (Replacement)obj;
			return this.before.equals(other.before) && this.after.equals(other.after) && this.type.equals(other.type);
		}
		return false;
	}

	public enum Direction {
		VARIABLE_TO_INVOCATION, INVOCATION_TO_VARIABLE;
	}
}
