package gr.uom.java.xmi;

import java.util.List;

import gr.uom.java.xmi.diff.CodeRange;

public interface IUMLType {

	LocationInfo getLocationInfo();

	int getArrayDimension();

	List<UMLAnnotation> getAnnotations();

	CodeRange codeRange();

	void setVarargs();

	boolean containsTypeArgument(String type);

	boolean isParameterized();

	boolean equals(Object o);

	int hashCode();

	String toString();

	String toQualifiedString();

	String getClassType();

	boolean equalsQualified(UMLType type);

	boolean equalsWithSubType(UMLType type);

	boolean equalClassType(IUMLType type);

	boolean compatibleTypes(IUMLType type);

	double normalizedNameDistance(IUMLType type);

}