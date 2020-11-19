package gr.uom.java.xmi.decomposition;

import java.util.List;

import gr.uom.java.xmi.LocationInfo;
import gr.uom.java.xmi.UMLAnnotation;
import gr.uom.java.xmi.UMLType;
import gr.uom.java.xmi.diff.CodeRange;

public interface IVariableDeclaration {

	String getVariableName();

	AbstractExpression getInitializer();

	UMLType getType();

	VariableScope getScope();

	boolean isParameter();

	void setParameter(boolean isParameter);

	boolean isAttribute();

	void setAttribute(boolean isAttribute);

	boolean isVarargsParameter();

	List<UMLAnnotation> getAnnotations();

	int hashCode();

	boolean equals(Object obj);

	String toString();

	String toQualifiedString();

	LocationInfo getLocationInfo();

	CodeRange codeRange();

	boolean equalVariableDeclarationType(VariableDeclaration other);

	IVariableDeclaration getVariableDeclaration();

}