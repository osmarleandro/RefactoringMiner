package gr.uom.java.xmi.decomposition.replacement;

import java.util.Set;

public class AddVariableReplacement extends Replacement {
	private Set<String> addedVariables;

	public AddVariableReplacement(Set<String> addedVariables) {
		super("", addedVariables.toString(), ReplacementType.ADD_VARIABLE);
		this.addedVariables = addedVariables;
	}

	public Set<String> getAddedVariables() {
		return addedVariables;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((after == null) ? 0 : after.hashCode());
		result = prime * result + ((before == null) ? 0 : before.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
}
