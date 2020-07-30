package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.ObjectCreation;

public class ObjectCreationReplacement extends Replacement {
	private ObjectCreation createdObjectBefore;
	private ObjectCreation createdObjectAfter;

	public ObjectCreationReplacement(String before, String after,
			ObjectCreation createdObjectBefore, ObjectCreation createdObjectAfter,
			ReplacementType type) {
		super(before, after, type);
		this.createdObjectBefore = createdObjectBefore;
		this.createdObjectAfter = createdObjectAfter;
	}

	public ObjectCreation getCreatedObjectBefore() {
		return createdObjectBefore;
	}

	public ObjectCreation getCreatedObjectAfter() {
		return createdObjectAfter;
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
