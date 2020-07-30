package gr.uom.java.xmi.decomposition.replacement;

import java.util.Set;

import gr.uom.java.xmi.decomposition.AbstractCodeFragment;

public class CompositeReplacement extends Replacement {
	private Set<AbstractCodeFragment> additionallyMatchedStatements1;
	private Set<AbstractCodeFragment> additionallyMatchedStatements2;
	
	public CompositeReplacement(String before, String after,
			Set<AbstractCodeFragment> additionallyMatchedStatements1, Set<AbstractCodeFragment> additionallyMatchedStatements2) {
		super(before, after, ReplacementType.COMPOSITE);
		this.additionallyMatchedStatements1 = additionallyMatchedStatements1;
		this.additionallyMatchedStatements2 = additionallyMatchedStatements2;
	}

	public Set<AbstractCodeFragment> getAdditionallyMatchedStatements1() {
		return additionallyMatchedStatements1;
	}

	public Set<AbstractCodeFragment> getAdditionallyMatchedStatements2() {
		return additionallyMatchedStatements2;
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
