package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.ObjectCreation;
import gr.uom.java.xmi.decomposition.OperationInvocation_RENAMED;

public class ClassInstanceCreationWithMethodInvocationReplacement extends Replacement {
	private ObjectCreation objectCreationBefore;
	private OperationInvocation_RENAMED invokedOperationAfter;

	public ClassInstanceCreationWithMethodInvocationReplacement(String before, String after, ReplacementType type,
			ObjectCreation objectCreationBefore, OperationInvocation_RENAMED invokedOperationAfter) {
		super(before, after, type);
		this.objectCreationBefore = objectCreationBefore;
		this.invokedOperationAfter = invokedOperationAfter;
	}

	public ObjectCreation getObjectCreationBefore() {
		return objectCreationBefore;
	}

	public OperationInvocation_RENAMED getInvokedOperationAfter() {
		return invokedOperationAfter;
	}

}
