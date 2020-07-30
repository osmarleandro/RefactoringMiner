package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.ObjectCreation;

public class ObjectCreationReplacement extends Replacement {
	private ObjectCreation createdObjectBefore;
	private ObjectCreation createdObjectAfter;

	public ObjectCreationReplacement(String before, String after,
			ObjectCreation createdObjectBefore, ObjectCreation createdObjectAfter,
			ReplacementType type) {
		super(before, after, type);
		this.createdObjectBefore = createdObjectBefore;
		this.createdObjectAfter = createdObjectAfter;
	}

	public ObjectCreation getCreatedObjectBefore() {
		return createdObjectBefore;
	}

	public ObjectCreation getCreatedObjectAfter() {
		return createdObjectAfter;
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
