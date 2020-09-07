package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.ObjectCreation_RENAMED;
import gr.uom.java.xmi.decomposition.OperationInvocation;

public class MethodInvocationWithClassInstanceCreationReplacement extends Replacement {
	private OperationInvocation invokedOperationBefore;
	private ObjectCreation_RENAMED objectCreationAfter;
	
	public MethodInvocationWithClassInstanceCreationReplacement(String before, String after, ReplacementType type,
			OperationInvocation invokedOperationBefore, ObjectCreation_RENAMED objectCreationAfter) {
		super(before, after, type);
		this.invokedOperationBefore = invokedOperationBefore;
		this.objectCreationAfter = objectCreationAfter;
	}

	public OperationInvocation getInvokedOperationBefore() {
		return invokedOperationBefore;
	}

	public ObjectCreation_RENAMED getObjectCreationAfter() {
		return objectCreationAfter;
	}

}
