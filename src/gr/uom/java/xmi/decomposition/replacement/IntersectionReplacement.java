package gr.uom.java.xmi.decomposition.replacement;

import java.util.Set;

public class IntersectionReplacement extends Replacement {
	private Set<String> commonElements;
	public IntersectionReplacement(String before, String after, Set<String> commonElements, ReplacementType type) {
		super(before, after, type);
		this.commonElements = commonElements;
	}
	public Set<String> getCommonElements() {
		return commonElements;
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
