package gr.uom.java.xmi;

import java.util.List;

public interface IUMLTypeParameter {

	String getName();

	List<UMLType> getTypeBounds();

	void addTypeBound(UMLType type);

	List<UMLAnnotation> getAnnotations();

	void addAnnotation(UMLAnnotation annotation);

	int hashCode();

	boolean equals(Object obj);

}