package gr.uom.java.xmi.decomposition.replacement;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import gr.uom.java.xmi.diff.CandidateSplitVariableRefactoring;

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

	public void processSplit(Map<SplitVariableReplacement, Set<CandidateSplitVariableRefactoring>> splitMap, CandidateSplitVariableRefactoring candidate) {
		SplitVariableReplacement splitToBeRemoved = null;
		for(SplitVariableReplacement split : splitMap.keySet()) {
			if(split.subsumes(this)) {
				splitMap.get(split).add(candidate);
				return;
			}
			else if(split.equal(this)) {
				splitMap.get(split).add(candidate);
				return;
			}
			else if(split.commonBefore(this)) {
				splitToBeRemoved = split;
				Set<String> splitVariables = new LinkedHashSet<String>();
				splitVariables.addAll(split.getSplitVariables());
				splitVariables.addAll(getSplitVariables());
				SplitVariableReplacement replacement = new SplitVariableReplacement(split.getBefore(), splitVariables);
				Set<CandidateSplitVariableRefactoring> candidates = splitMap.get(splitToBeRemoved);
				candidates.add(candidate);
				splitMap.put(replacement, candidates);
				break;
			}
			else if(subsumes(split)) {
				splitToBeRemoved = split;
				Set<CandidateSplitVariableRefactoring> candidates = splitMap.get(splitToBeRemoved);
				candidates.add(candidate);
				splitMap.put(this, candidates);
				break;
			}
		}
		if(splitToBeRemoved != null) {
			splitMap.remove(splitToBeRemoved);
			return;
		}
		Set<CandidateSplitVariableRefactoring> set = new LinkedHashSet<CandidateSplitVariableRefactoring>();
		set.add(candidate);
		splitMap.put(this, set);
	}
}
