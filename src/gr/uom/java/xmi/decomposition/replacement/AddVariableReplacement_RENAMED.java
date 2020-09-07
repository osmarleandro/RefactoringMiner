package gr.uom.java.xmi.decomposition.replacement;

import java.util.Set;

public class AddVariableReplacement_RENAMED extends Replacement {
	private Set<String> addedVariables;

	public AddVariableReplacement_RENAMED(Set<String> addedVariables) {
		super("", addedVariables.toString(), ReplacementType.ADD_VARIABLE);
		this.addedVariables = addedVariables;
	}

	public Set<String> getAddedVariables() {
		return addedVariables;
	}
}
