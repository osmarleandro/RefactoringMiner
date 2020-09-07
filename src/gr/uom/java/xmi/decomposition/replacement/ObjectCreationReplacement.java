package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.ObjectCreation_RENAMED;

public class ObjectCreationReplacement extends Replacement {
	private ObjectCreation_RENAMED createdObjectBefore;
	private ObjectCreation_RENAMED createdObjectAfter;

	public ObjectCreationReplacement(String before, String after,
			ObjectCreation_RENAMED createdObjectBefore, ObjectCreation_RENAMED createdObjectAfter,
			ReplacementType type) {
		super(before, after, type);
		this.createdObjectBefore = createdObjectBefore;
		this.createdObjectAfter = createdObjectAfter;
	}

	public ObjectCreation_RENAMED getCreatedObjectBefore() {
		return createdObjectBefore;
	}

	public ObjectCreation_RENAMED getCreatedObjectAfter() {
		return createdObjectAfter;
	}

}
