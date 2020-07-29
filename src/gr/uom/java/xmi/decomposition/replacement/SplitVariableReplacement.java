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

	public boolean subsumes(SplitVariableReplacement other) {
		return this.getBefore().equals(other.getBefore()) &&
				this.splitVariables.containsAll(other.splitVariables) &&
				this.splitVariables.size() > other.splitVariables.size();
	}

	public boolean commonBefore(SplitVariableReplacement splitVariableReplacement) {
		Set<String> interestion = new LinkedHashSet<String>(splitVariableReplacement.splitVariables);
		interestion.retainAll(splitVariables);
		return splitVariableReplacement.getBefore().equals(getBefore()) && interestion.size() == 0;
	}
}
