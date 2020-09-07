package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.ObjectCreation;
import gr.uom.java.xmi.decomposition.OperationInvocation;

public class ClassInstanceCreationWithMethodInvocationReplacement_RENAMED extends Replacement {
	private ObjectCreation objectCreationBefore;
	private OperationInvocation invokedOperationAfter;

	public ClassInstanceCreationWithMethodInvocationReplacement_RENAMED(String before, String after, ReplacementType type,
			ObjectCreation objectCreationBefore, OperationInvocation invokedOperationAfter) {
		super(before, after, type);
		this.objectCreationBefore = objectCreationBefore;
		this.invokedOperationAfter = invokedOperationAfter;
	}

	public ObjectCreation getObjectCreationBefore() {
		return objectCreationBefore;
	}

	public OperationInvocation getInvokedOperationAfter() {
		return invokedOperationAfter;
	}

}
