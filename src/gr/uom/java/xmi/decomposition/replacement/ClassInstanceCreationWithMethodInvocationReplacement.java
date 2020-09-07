package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.ObjectCreation_RENAMED;
import gr.uom.java.xmi.decomposition.OperationInvocation;

public class ClassInstanceCreationWithMethodInvocationReplacement extends Replacement {
	private ObjectCreation_RENAMED objectCreationBefore;
	private OperationInvocation invokedOperationAfter;

	public ClassInstanceCreationWithMethodInvocationReplacement(String before, String after, ReplacementType type,
			ObjectCreation_RENAMED objectCreationBefore, OperationInvocation invokedOperationAfter) {
		super(before, after, type);
		this.objectCreationBefore = objectCreationBefore;
		this.invokedOperationAfter = invokedOperationAfter;
	}

	public ObjectCreation_RENAMED getObjectCreationBefore() {
		return objectCreationBefore;
	}

	public OperationInvocation getInvokedOperationAfter() {
		return invokedOperationAfter;
	}

}
