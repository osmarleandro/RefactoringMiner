package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.ObjectCreation;
import gr.uom.java.xmi.decomposition.OperationInvocation;

public class MethodInvocationWithClassInstanceCreationReplacement extends Replacement {
	private OperationInvocation invokedOperationBefore;
	private ObjectCreation objectCreationAfter;
	
	public MethodInvocationWithClassInstanceCreationReplacement(String before, String after, ReplacementType type,
			OperationInvocation invokedOperationBefore, ObjectCreation objectCreationAfter) {
		super(before, after, type);
		this.invokedOperationBefore = invokedOperationBefore;
		this.objectCreationAfter = objectCreationAfter;
	}

	public OperationInvocation getInvokedOperationBefore() {
		return invokedOperationBefore;
	}

	public ObjectCreation getObjectCreationAfter() {
		return objectCreationAfter;
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
