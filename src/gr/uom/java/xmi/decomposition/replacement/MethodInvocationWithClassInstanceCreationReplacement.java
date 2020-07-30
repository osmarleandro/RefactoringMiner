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

	public boolean involvesVariable() {
		return type.equals(ReplacementType.VARIABLE_NAME) ||
				type.equals(ReplacementType.BOOLEAN_REPLACED_WITH_VARIABLE) ||
				type.equals(ReplacementType.TYPE_LITERAL_REPLACED_WITH_VARIABLE) ||
				type.equals(ReplacementType.ARGUMENT_REPLACED_WITH_VARIABLE) ||
				type.equals(ReplacementType.VARIABLE_REPLACED_WITH_METHOD_INVOCATION) ||
				type.equals(ReplacementType.VARIABLE_REPLACED_WITH_EXPRESSION_OF_METHOD_INVOCATION) ||
				type.equals(ReplacementType.VARIABLE_REPLACED_WITH_ARRAY_ACCESS) ||
				type.equals(ReplacementType.VARIABLE_REPLACED_WITH_PREFIX_EXPRESSION) ||
				type.equals(ReplacementType.VARIABLE_REPLACED_WITH_STRING_LITERAL) ||
				type.equals(ReplacementType.VARIABLE_REPLACED_WITH_NULL_LITERAL) ||
				type.equals(ReplacementType.VARIABLE_REPLACED_WITH_NUMBER_LITERAL);
	}

}
