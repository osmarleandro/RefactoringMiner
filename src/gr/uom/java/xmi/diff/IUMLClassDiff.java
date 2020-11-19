package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLType;

public interface IUMLClassDiff {

	boolean matches(String className);

	boolean matches(UMLType type);

}