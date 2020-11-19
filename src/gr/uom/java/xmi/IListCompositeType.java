package gr.uom.java.xmi;

import java.util.List;

import gr.uom.java.xmi.ListCompositeType.Kind;

public interface IListCompositeType {

	List<UMLType> getTypes();

	Kind getKind();

	int hashCode();

	boolean equals(Object obj);

	String toString();

	String toQualifiedString();

	String getClassType();

}