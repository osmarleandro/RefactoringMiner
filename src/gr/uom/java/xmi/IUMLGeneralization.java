package gr.uom.java.xmi;

public interface IUMLGeneralization {

	UMLClass getChild();

	String getParent();

	boolean equals(Object o);

	String toString();

	int compareTo(IUMLGeneralization generalization);

}