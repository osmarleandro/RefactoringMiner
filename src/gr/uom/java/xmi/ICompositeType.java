package gr.uom.java.xmi;

public interface ICompositeType {

	UMLType getLeftType();

	LeafType getRightType();

	int hashCode();

	boolean equals(Object obj);

	String toString();

	String toQualifiedString();

	String getClassType();

}