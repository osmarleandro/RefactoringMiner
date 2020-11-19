package gr.uom.java.xmi;

import java.util.List;

import gr.uom.java.xmi.decomposition.VariableDeclaration;

public interface IUMLParameter {

	UMLType getType();

	VariableDeclaration getVariableDeclaration();

	void setVariableDeclaration(VariableDeclaration variableDeclaration);

	String getName();

	String getKind();

	boolean isVarargs();

	List<UMLAnnotation> getAnnotations();

	boolean equalsExcludingType(UMLParameter parameter);

	boolean equalsIncludingName(UMLParameter parameter);

	boolean equals(Object o);

	boolean equalsQualified(UMLParameter parameter);

	int hashCode();

	String toString();

	String toQualifiedString();

}