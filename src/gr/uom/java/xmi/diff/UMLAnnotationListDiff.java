package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import gr.uom.java.xmi.UMLAnnotation_RENAMED;

public class UMLAnnotationListDiff {
	private List<UMLAnnotation_RENAMED> removedAnnotations;
	private List<UMLAnnotation_RENAMED> addedAnnotations;
	private List<UMLAnnotationDiff> annotationDiffList;

	public UMLAnnotationListDiff(List<UMLAnnotation_RENAMED> annotations1, List<UMLAnnotation_RENAMED> annotations2) {
		this.removedAnnotations = new ArrayList<UMLAnnotation_RENAMED>();
		this.addedAnnotations = new ArrayList<UMLAnnotation_RENAMED>();
		this.annotationDiffList = new ArrayList<UMLAnnotationDiff>();
		List<SimpleEntry<UMLAnnotation_RENAMED, UMLAnnotation_RENAMED>> matchedAnnotations = new ArrayList<SimpleEntry<UMLAnnotation_RENAMED,UMLAnnotation_RENAMED>>();
		for(UMLAnnotation_RENAMED annotation1 : annotations1) {
			boolean found = false;
			for(UMLAnnotation_RENAMED annotation2 : annotations2) {
				if(annotation1.getTypeName().equals(annotation2.getTypeName())) {
					matchedAnnotations.add(new SimpleEntry<UMLAnnotation_RENAMED, UMLAnnotation_RENAMED>(annotation1, annotation2));
					found = true;
					break;
				}
			}
			if(!found) {
				removedAnnotations.add(annotation1);
			}
		}
		for(UMLAnnotation_RENAMED annotation2 : annotations2) {
			boolean found = false;
			for(UMLAnnotation_RENAMED annotation1 : annotations1) {
				if(annotation1.getTypeName().equals(annotation2.getTypeName())) {
					matchedAnnotations.add(new SimpleEntry<UMLAnnotation_RENAMED, UMLAnnotation_RENAMED>(annotation1, annotation2));
					found = true;
					break;
				}
			}
			if(!found) {
				addedAnnotations.add(annotation2);
			}
		}
		for(SimpleEntry<UMLAnnotation_RENAMED, UMLAnnotation_RENAMED> entry : matchedAnnotations) {
			UMLAnnotationDiff annotationDiff = new UMLAnnotationDiff(entry.getKey(), entry.getValue());
			if(!annotationDiff.isEmpty()) {
				annotationDiffList.add(annotationDiff);
			}
		}
	}
	
	public List<UMLAnnotation_RENAMED> getRemovedAnnotations() {
		return removedAnnotations;
	}

	public List<UMLAnnotation_RENAMED> getAddedAnnotations() {
		return addedAnnotations;
	}

	public List<UMLAnnotationDiff> getAnnotationDiffList() {
		return annotationDiffList;
	}

	public boolean isEmpty() {
		return removedAnnotations.isEmpty() && addedAnnotations.isEmpty() && annotationDiffList.isEmpty();
	}
}
