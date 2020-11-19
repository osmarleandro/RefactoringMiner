package gr.uom.java.xmi.diff;

import java.util.Set;

import gr.uom.java.xmi.UMLAttribute;
import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;
import gr.uom.java.xmi.decomposition.VariableDeclaration;

public interface ICandidateAttributeRefactoring {

	String getOriginalVariableName();

	String getRenamedVariableName();

	UMLOperation getOperationBefore();

	UMLOperation getOperationAfter();

	Set<AbstractCodeMapping> getAttributeReferences();

	int getOccurrences();

	VariableDeclaration getOriginalVariableDeclaration();

	void setOriginalVariableDeclaration(VariableDeclaration originalVariableDeclaration);

	VariableDeclaration getRenamedVariableDeclaration();

	void setRenamedVariableDeclaration(VariableDeclaration renamedVariableDeclaration);

	UMLAttribute getOriginalAttribute();

	void setOriginalAttribute(UMLAttribute originalAttribute);

	UMLAttribute getRenamedAttribute();

	void setRenamedAttribute(UMLAttribute renamedAttribute);

	String toString();

	int hashCode();

	boolean equals(Object obj);

}