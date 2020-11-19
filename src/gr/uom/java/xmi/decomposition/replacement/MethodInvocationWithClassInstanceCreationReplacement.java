package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.IObjectCreation;
import gr.uom.java.xmi.decomposition.OperationInvocation;

public class MethodInvocationWithClassInstanceCreationReplacement extends Replacement {
	private OperationInvocation invokedOperationBefore;
	private IObjectCreation objectCreationAfter;
	
	public MethodInvocationWithClassInstanceCreationReplacement(String before, String after, ReplacementType type,
			OperationInvocation invokedOperationBefore, IObjectCreation objectCreationAfter) {
		super(before, after, type);
		this.invokedOperationBefore = invokedOperationBefore;
		this.objectCreationAfter = objectCreationAfter;
	}

	public OperationInvocation getInvokedOperationBefore() {
		return invokedOperationBefore;
	}

	public IObjectCreation getObjectCreationAfter() {
		return objectCreationAfter;
	}

}
