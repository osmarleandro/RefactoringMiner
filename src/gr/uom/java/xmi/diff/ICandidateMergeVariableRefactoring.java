package gr.uom.java.xmi.diff;

import java.util.Set;

import gr.uom.java.xmi.UMLAttribute;
import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;

public interface ICandidateMergeVariableRefactoring {

	Set<String> getMergedVariables();

	String getNewVariable();

	UMLOperation getOperationBefore();

	UMLOperation getOperationAfter();

	Set<AbstractCodeMapping> getVariableReferences();

	Set<UMLAttribute> getMergedAttributes();

	void setMergedAttributes(Set<UMLAttribute> mergedAttributes);

	UMLAttribute getNewAttribute();

	void setNewAttribute(UMLAttribute newAttribute);

	String toString();

	int hashCode();

	boolean equals(Object obj);

}