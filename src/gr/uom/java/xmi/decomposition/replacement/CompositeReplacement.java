package gr.uom.java.xmi.decomposition.replacement;

import java.util.Set;

import gr.uom.java.xmi.decomposition.AbstractCodeFragment_RENAMED;

public class CompositeReplacement extends Replacement {
	private Set<AbstractCodeFragment_RENAMED> additionallyMatchedStatements1;
	private Set<AbstractCodeFragment_RENAMED> additionallyMatchedStatements2;
	
	public CompositeReplacement(String before, String after,
			Set<AbstractCodeFragment_RENAMED> additionallyMatchedStatements1, Set<AbstractCodeFragment_RENAMED> additionallyMatchedStatements2) {
		super(before, after, ReplacementType.COMPOSITE);
		this.additionallyMatchedStatements1 = additionallyMatchedStatements1;
		this.additionallyMatchedStatements2 = additionallyMatchedStatements2;
	}

	public Set<AbstractCodeFragment_RENAMED> getAdditionallyMatchedStatements1() {
		return additionallyMatchedStatements1;
	}

	public Set<AbstractCodeFragment_RENAMED> getAdditionallyMatchedStatements2() {
		return additionallyMatchedStatements2;
	}
}
