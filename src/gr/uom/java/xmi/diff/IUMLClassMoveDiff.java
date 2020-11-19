package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLClass;

public interface IUMLClassMoveDiff {

	UMLClass getMovedClass();

	String toString();

	boolean equals(Object o);

}