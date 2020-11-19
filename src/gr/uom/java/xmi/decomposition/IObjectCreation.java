package gr.uom.java.xmi.decomposition;

import gr.uom.java.xmi.UMLType;

public interface IObjectCreation {

	String getName();

	UMLType getType();

	boolean isArray();

	String getAnonymousClassDeclaration();

	IObjectCreation update(String oldExpression, String newExpression);

	boolean equals(Object o);

	String toString();

	int hashCode();

	boolean identicalArrayInitializer(ObjectCreation other);

	double normalizedNameDistance(AbstractCall call);

	boolean identicalName(AbstractCall call);

}