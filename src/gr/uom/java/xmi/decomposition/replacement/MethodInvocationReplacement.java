package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.OperationInvocation;

public class MethodInvocationReplacement extends Replacement {
	private OperationInvocation invokedOperationBefore;
	private OperationInvocation invokedOperationAfter;
	
	public MethodInvocationReplacement(String before, String after,
			OperationInvocation invokedOperationBefore, OperationInvocation invokedOperationAfter,
			ReplacementType type) {
		super(before, after, type);
		this.invokedOperationBefore = invokedOperationBefore;
		this.invokedOperationAfter = invokedOperationAfter;
	}

	public OperationInvocation getInvokedOperationBefore() {
		return invokedOperationBefore;
	}

	public OperationInvocation getInvokedOperationAfter() {
		return invokedOperationAfter;
	}

	public boolean differentExpressionNameAndArguments() {
		return invokedOperationBefore.differentExpressionNameAndArguments(invokedOperationAfter);
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
}
