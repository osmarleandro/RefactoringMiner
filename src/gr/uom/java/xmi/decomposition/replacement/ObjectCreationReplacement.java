package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.IObjectCreation;

public class ObjectCreationReplacement extends Replacement {
	private IObjectCreation createdObjectBefore;
	private IObjectCreation createdObjectAfter;

	public ObjectCreationReplacement(String before, String after,
			IObjectCreation createdObjectBefore, IObjectCreation createdObjectAfter,
			ReplacementType type) {
		super(before, after, type);
		this.createdObjectBefore = createdObjectBefore;
		this.createdObjectAfter = createdObjectAfter;
	}

	public IObjectCreation getCreatedObjectBefore() {
		return createdObjectBefore;
	}

	public IObjectCreation getCreatedObjectAfter() {
		return createdObjectAfter;
	}

}
