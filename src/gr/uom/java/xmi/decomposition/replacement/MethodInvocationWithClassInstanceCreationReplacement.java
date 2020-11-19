package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.IOperationInvocation;
import gr.uom.java.xmi.decomposition.ObjectCreation;

public class MethodInvocationWithClassInstanceCreationReplacement extends Replacement {
	private IOperationInvocation invokedOperationBefore;
	private ObjectCreation objectCreationAfter;
	
	public MethodInvocationWithClassInstanceCreationReplacement(String before, String after, ReplacementType type,
			IOperationInvocation invokedOperationBefore, ObjectCreation objectCreationAfter) {
		super(before, after, type);
		this.invokedOperationBefore = invokedOperationBefore;
		this.objectCreationAfter = objectCreationAfter;
	}

	public IOperationInvocation getInvokedOperationBefore() {
		return invokedOperationBefore;
	}

	public ObjectCreation getObjectCreationAfter() {
		return objectCreationAfter;
	}

}
