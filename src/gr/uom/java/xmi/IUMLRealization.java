package gr.uom.java.xmi;

public interface IUMLRealization {

	String getSupplier();

	UMLClass getClient();

	boolean equals(Object o);

	String toString();

	int compareTo(IUMLRealization realization);

}