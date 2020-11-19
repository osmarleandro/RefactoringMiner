package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.IOperationInvocation;
import gr.uom.java.xmi.decomposition.ObjectCreation;

public class ClassInstanceCreationWithMethodInvocationReplacement extends Replacement {
	private ObjectCreation objectCreationBefore;
	private IOperationInvocation invokedOperationAfter;

	public ClassInstanceCreationWithMethodInvocationReplacement(String before, String after, ReplacementType type,
			ObjectCreation objectCreationBefore, IOperationInvocation invokedOperationAfter) {
		super(before, after, type);
		this.objectCreationBefore = objectCreationBefore;
		this.invokedOperationAfter = invokedOperationAfter;
	}

	public ObjectCreation getObjectCreationBefore() {
		return objectCreationBefore;
	}

	public IOperationInvocation getInvokedOperationAfter() {
		return invokedOperationAfter;
	}

}
