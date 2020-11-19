package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLClass;

public interface IUMLClassRenameDiff {

	UMLClass getRenamedClass();

	boolean samePackage();

	String toString();

}