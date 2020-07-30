package gr.uom.java.xmi.decomposition.replacement;

import java.util.Set;

import gr.uom.java.xmi.decomposition.AbstractCodeFragment;

public class CompositeReplacement extends Replacement {
	private Set<AbstractCodeFragment> additionallyMatchedStatements1;
	private Set<AbstractCodeFragment> additionallyMatchedStatements2;
	
	public CompositeReplacement(String before, String after,
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if(obj instanceof Replacement) {
			Replacement other = (Replacement)obj;
			return this.before.equals(other.before) && this.after.equals(other.after) && this.type.equals(other.type);
		}
		return false;
	}
}
