package gr.uom.java.xmi;

import java.util.List;

import gr.uom.java.xmi.decomposition.VariableDeclaration;
import gr.uom.java.xmi.diff.CodeRange;

public interface IUMLAttribute {

	LocationInfo getLocationInfo();

	UMLType getType();

	void setType(UMLType type);

	String getVisibility();

	void setVisibility(String visibility);

	boolean isFinal();

	void setFinal(boolean isFinal);

	boolean isStatic();

	void setStatic(boolean isStatic);

	String getNonQualifiedClassName();

	String getClassName();

	void setClassName(String className);

	String getName();

	VariableDeclaration getVariableDeclaration();

	void setVariableDeclaration(VariableDeclaration variableDeclaration);

	UMLJavadoc getJavadoc();

	void setJavadoc(UMLJavadoc javadoc);

	List<UMLAnnotation> getAnnotations();

	boolean equalsIgnoringChangedType(UMLAttribute attribute);

	boolean equalsIgnoringChangedVisibility(UMLAttribute attribute);

	CodeRange codeRange();

	boolean equals(Object o);

	boolean equalsQualified(UMLAttribute umlAttribute);

	String toString();

	String toQualifiedString();

	int compareTo(IUMLAttribute attribute);

	double normalizedNameDistance(IUMLAttribute attribute);

}