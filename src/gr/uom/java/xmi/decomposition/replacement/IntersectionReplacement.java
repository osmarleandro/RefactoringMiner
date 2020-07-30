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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((after == null) ? 0 : after.hashCode());
		result = prime * result + ((before == null) ? 0 : before.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
}
