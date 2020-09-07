package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.OperationInvocation_RENAMED;

public class MethodInvocationReplacement extends Replacement {
	private OperationInvocation_RENAMED invokedOperationBefore;
	private OperationInvocation_RENAMED invokedOperationAfter;
	
	public MethodInvocationReplacement(String before, String after,
			OperationInvocation_RENAMED invokedOperationBefore, OperationInvocation_RENAMED invokedOperationAfter,
			ReplacementType type) {
		super(before, after, type);
		this.invokedOperationBefore = invokedOperationBefore;
		this.invokedOperationAfter = invokedOperationAfter;
	}

	public OperationInvocation_RENAMED getInvokedOperationBefore() {
		return invokedOperationBefore;
	}

	public OperationInvocation_RENAMED getInvokedOperationAfter() {
		return invokedOperationAfter;
	}

	public boolean differentExpressionNameAndArguments() {
		return invokedOperationBefore.differentExpressionNameAndArguments(invokedOperationAfter);
	}
}
