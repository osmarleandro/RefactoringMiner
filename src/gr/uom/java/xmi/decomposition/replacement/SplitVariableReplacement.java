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
		return this.getBefore_RENAMED().equals(other.getBefore_RENAMED()) &&
				this.splitVariables.containsAll(other.splitVariables) &&
				other.splitVariables.containsAll(this.splitVariables);
	}

	public boolean commonBefore(SplitVariableReplacement other) {
		Set<String> interestion = new LinkedHashSet<String>(this.splitVariables);
		interestion.retainAll(other.splitVariables);
		return this.getBefore_RENAMED().equals(other.getBefore_RENAMED()) && interestion.size() == 0;
	}

	public boolean subsumes(SplitVariableReplacement other) {
		return this.getBefore_RENAMED().equals(other.getBefore_RENAMED()) &&
				this.splitVariables.containsAll(other.splitVariables) &&
				this.splitVariables.size() > other.splitVariables.size();
	}
}
