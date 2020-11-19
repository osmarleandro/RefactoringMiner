package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLClass;

public interface IMovedClassToAnotherSourceFolder {

	String getOriginalClassName();

	String getMovedClassName();

	UMLClass getOriginalClass();

	UMLClass getMovedClass();

	RenamePattern getRenamePattern();

}