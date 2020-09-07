package gr.uom.java.xmi.decomposition.replacement;

import java.util.Set;

import gr.uom.java.xmi.decomposition.AbstractCodeFragment;

public class CompositeReplacement_RENAMED extends Replacement {
	private Set<AbstractCodeFragment> additionallyMatchedStatements1;
	private Set<AbstractCodeFragment> additionallyMatchedStatements2;
	
	public CompositeReplacement_RENAMED(String before, String after,
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
}
