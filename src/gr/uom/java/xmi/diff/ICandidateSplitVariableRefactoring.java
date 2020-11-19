package gr.uom.java.xmi.diff;

import java.util.Set;

import gr.uom.java.xmi.UMLAttribute;
import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;

public interface ICandidateSplitVariableRefactoring {

	String getOldVariable();

	Set<String> getSplitVariables();

	UMLOperation getOperationBefore();

	UMLOperation getOperationAfter();

	Set<AbstractCodeMapping> getVariableReferences();

	Set<UMLAttribute> getSplitAttributes();

	void setSplitAttributes(Set<UMLAttribute> splitAttributes);

	UMLAttribute getOldAttribute();

	void setOldAttribute(UMLAttribute oldAttribute);

	String toString();

	int hashCode();

	boolean equals(Object obj);

}