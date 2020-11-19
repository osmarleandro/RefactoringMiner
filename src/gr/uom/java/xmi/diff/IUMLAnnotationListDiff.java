package gr.uom.java.xmi.diff;

import java.util.List;

import gr.uom.java.xmi.UMLAnnotation;

public interface IUMLAnnotationListDiff {

	List<UMLAnnotation> getRemovedAnnotations();

	List<UMLAnnotation> getAddedAnnotations();

	List<UMLAnnotationDiff> getAnnotationDiffList();

	boolean isEmpty();

}