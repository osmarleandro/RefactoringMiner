package gr.uom.java.xmi.decomposition.replacement;

import java.util.LinkedHashSet;
import java.util.Set;

public class SplitVariableReplacement extends Replacement {
	private Set<String> splitVariables;

	public SplitVariableReplacement(String oldVariable, Set<String> newVariables) {
		super(oldVariable, newVariables.toString(), ReplacementType.SPLIT_VARIABLE);
		this.splitVariables = newVariables;
	}

	public Set<String> getSplitVariables() {
		return splitVariables;
	}

	public boolean equal(SplitVariableReplacement other) {
		return this.getBefore().equals(other.getBefore()) &&
				this.splitVariables.containsAll(other.splitVariables) &&
				other.splitVariables.containsAll(this.splitVariables);
	}

	public boolean commonBefore(SplitVariableReplacement other) {
		Set<String> interestion = new LinkedHashSet<String>(this.splitVariables);
		interestion.retainAll(other.splitVariables);
		return this.getBefore().equals(other.getBefore()) && interestion.size() == 0;
	}

	public boolean subsumes(SplitVariableReplacement other) {
		return this.getBefore().equals(other.getBefore()) &&
				this.splitVariables.containsAll(other.splitVariables) &&
				this.splitVariables.size() > other.splitVariables.size();
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
