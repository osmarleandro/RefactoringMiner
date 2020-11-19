package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLParameter;

public interface IUMLParameterDiff {

	UMLParameter getRemovedParameter();

	UMLParameter getAddedParameter();

	boolean isTypeChanged();

	boolean isQualifiedTypeChanged();

	boolean isNameChanged();

	String toString();

}