package gr.uom.java.xmi.decomposition.replacement;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import gr.uom.java.xmi.diff.CandidateMergeVariableRefactoring;

public class MergeVariableReplacement extends Replacement {
	private Set<String> mergedVariables;
	
	public MergeVariableReplacement(Set<String> mergedVariables, String newVariable) {
		super(mergedVariables.toString(), newVariable, ReplacementType.MERGE_VARIABLES);
		this.mergedVariables = mergedVariables;
	}

	public Set<String> getMergedVariables() {
		return mergedVariables;
	}

	public boolean equal(MergeVariableReplacement other) {
		return this.getAfter().equals(other.getAfter()) &&
				this.mergedVariables.containsAll(other.mergedVariables) &&
				other.mergedVariables.containsAll(this.mergedVariables);
	}

	public boolean commonAfter(MergeVariableReplacement other) {
		Set<String> interestion = new LinkedHashSet<String>(this.mergedVariables);
		interestion.retainAll(other.mergedVariables);
		return this.getAfter().equals(other.getAfter()) && interestion.size() == 0;
	}

	public boolean subsumes(MergeVariableReplacement other) {
		return this.getAfter().equals(other.getAfter()) &&
				this.mergedVariables.containsAll(other.mergedVariables) &&
				this.mergedVariables.size() > other.mergedVariables.size();
	}

	public void processMerge(Map<MergeVariableReplacement, Set<CandidateMergeVariableRefactoring>> mergeMap, CandidateMergeVariableRefactoring candidate) {
		MergeVariableReplacement mergeToBeRemoved = null;
		for(MergeVariableReplacement merge : mergeMap.keySet()) {
			if(merge.subsumes(this)) {
				mergeMap.get(merge).add(candidate);
				return;
			}
			else if(merge.equal(this)) {
				mergeMap.get(merge).add(candidate);
				return;
			}
			else if(merge.commonAfter(this)) {
				mergeToBeRemoved = merge;
				Set<String> mergedVariables = new LinkedHashSet<String>();
				mergedVariables.addAll(merge.getMergedVariables());
				mergedVariables.addAll(getMergedVariables());
				MergeVariableReplacement replacement = new MergeVariableReplacement(mergedVariables, merge.getAfter());
				Set<CandidateMergeVariableRefactoring> candidates = mergeMap.get(mergeToBeRemoved);
				candidates.add(candidate);
				mergeMap.put(replacement, candidates);
				break;
			}
			else if(subsumes(merge)) {
				mergeToBeRemoved = merge;
				Set<CandidateMergeVariableRefactoring> candidates = mergeMap.get(mergeToBeRemoved);
				candidates.add(candidate);
				mergeMap.put(this, candidates);
				break;
			}
		}
		if(mergeToBeRemoved != null) {
			mergeMap.remove(mergeToBeRemoved);
			return;
		}
		Set<CandidateMergeVariableRefactoring> set = new LinkedHashSet<CandidateMergeVariableRefactoring>();
		set.add(candidate);
		mergeMap.put(this, set);
	}
}
