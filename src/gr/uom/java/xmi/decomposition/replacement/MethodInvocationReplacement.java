package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.OperationInvocation;

public class MethodInvocationReplacement extends Replacement {
	public OperationInvocation invokedOperationBefore;
	private OperationInvocation invokedOperationAfter;
	
	public MethodInvocationReplacement(String before, String after,
			OperationInvocation invokedOperationBefore, OperationInvocation invokedOperationAfter,
			ReplacementType type) {
		super(before, after, type);
		this.invokedOperationBefore = invokedOperationBefore;
		this.invokedOperationAfter = invokedOperationAfter;
	}

	public OperationInvocation getInvokedOperationAfter() {
		return invokedOperationAfter;
	}

	public boolean differentExpressionNameAndArguments() {
		return invokedOperationBefore.differentExpressionNameAndArguments(invokedOperationAfter);
	}
}
