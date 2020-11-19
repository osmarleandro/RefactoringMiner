package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.ObjectCreation;

public interface IObjectCreationReplacement {

	ObjectCreation getCreatedObjectBefore();

	ObjectCreation getCreatedObjectAfter();

}