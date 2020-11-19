package gr.uom.java.xmi;

public interface ILeafType {

	String getClassType();

	boolean equals(Object o);

	boolean equalsQualified(UMLType type);

	boolean equalsWithSubType(UMLType type);

	boolean equalClassType(UMLType type);

	boolean compatibleTypes(UMLType type);

	int hashCode();

	String toString();

	String toQualifiedString();

}