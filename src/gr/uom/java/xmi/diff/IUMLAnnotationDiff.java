package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLAnnotation;

public interface IUMLAnnotationDiff {

	UMLAnnotation getRemovedAnnotation();

	UMLAnnotation getAddedAnnotation();

	boolean isEmpty();

}