package gr.uom.java.xmi.decomposition.replacement;

import java.util.LinkedHashSet;
import java.util.Set;

public class MergeVariableReplacement_RENAMED extends Replacement {
	private Set<String> mergedVariables;
	
	public MergeVariableReplacement_RENAMED(Set<String> mergedVariables, String newVariable) {
		super(mergedVariables.toString(), newVariable, ReplacementType.MERGE_VARIABLES);
		this.mergedVariables = mergedVariables;
	}

	public Set<String> getMergedVariables() {
		return mergedVariables;
	}

	public boolean equal(MergeVariableReplacement_RENAMED other) {
		return this.getAfter().equals(other.getAfter()) &&
				this.mergedVariables.containsAll(other.mergedVariables) &&
				other.mergedVariables.containsAll(this.mergedVariables);
	}

	public boolean commonAfter(MergeVariableReplacement_RENAMED other) {
		Set<String> interestion = new LinkedHashSet<String>(this.mergedVariables);
		interestion.retainAll(other.mergedVariables);
		return this.getAfter().equals(other.getAfter()) && interestion.size() == 0;
	}

	public boolean subsumes(MergeVariableReplacement_RENAMED other) {
		return this.getAfter().equals(other.getAfter()) &&
				this.mergedVariables.containsAll(other.mergedVariables) &&
				this.mergedVariables.size() > other.mergedVariables.size();
	}
}
