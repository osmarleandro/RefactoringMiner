package gr.uom.java.xmi.diff;

import java.util.Set;

import gr.uom.java.xmi.UMLAttribute;
import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;

public class CandidateMergeVariableRefactoring {
	public Set<String> mergedVariables;
	public String newVariable;
	public UMLOperation operationBefore;
	public UMLOperation operationAfter;
	private Set<AbstractCodeMapping> variableReferences;
	private Set<UMLAttribute> mergedAttributes;
	private UMLAttribute newAttribute;

	public CandidateMergeVariableRefactoring(Set<String> mergedVariables, String newVariable,
			UMLOperation operationBefore, UMLOperation operationAfter, Set<AbstractCodeMapping> variableReferences) {
		this.mergedVariables = mergedVariables;
		this.newVariable = newVariable;
		this.operationBefore = operationBefore;
		this.operationAfter = operationAfter;
		this.variableReferences = variableReferences;
	}

	public Set<String> getMergedVariables() {
		return mergedVariables;
	}

	public String getNewVariable() {
		return newVariable;
	}

	public UMLOperation getOperationBefore() {
		return operationBefore;
	}

	public UMLOperation getOperationAfter() {
		return operationAfter;
	}

	public Set<AbstractCodeMapping> getVariableReferences() {
		return variableReferences;
	}

	public Set<UMLAttribute> getMergedAttributes() {
		return mergedAttributes;
	}

	public void setMergedAttributes(Set<UMLAttribute> mergedAttributes) {
		this.mergedAttributes = mergedAttributes;
	}

	public UMLAttribute getNewAttribute() {
		return newAttribute;
	}

	public void setNewAttribute(UMLAttribute newAttribute) {
		this.newAttribute = newAttribute;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Merge Attribute").append("\t");
		sb.append(mergedVariables);
		sb.append(" to ");
		sb.append(newVariable);
		sb.append(" in method ");
		sb.append(operationAfter);
		sb.append(" in class ").append(operationAfter.getClassName());
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mergedVariables == null) ? 0 : mergedVariables.hashCode());
		result = prime * result + ((newVariable == null) ? 0 : newVariable.hashCode());
		result = prime * result + ((operationAfter == null) ? 0 : operationAfter.hashCode());
		result = prime * result + ((operationBefore == null) ? 0 : operationBefore.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return newAttribute.equals(this, obj);
	}
}
