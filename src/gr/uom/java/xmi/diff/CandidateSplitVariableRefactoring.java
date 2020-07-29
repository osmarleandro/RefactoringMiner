package gr.uom.java.xmi.diff;

import java.util.Set;

import gr.uom.java.xmi.UMLAttribute;
import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;

public class CandidateSplitVariableRefactoring {
	public String oldVariable;
	public Set<String> splitVariables;
	public UMLOperation operationBefore;
	public UMLOperation operationAfter;
	private Set<AbstractCodeMapping> variableReferences;
	private UMLAttribute oldAttribute;
	private Set<UMLAttribute> splitAttributes;

	public CandidateSplitVariableRefactoring(String oldVariable, Set<String> splitVariables,
			UMLOperation operationBefore, UMLOperation operationAfter, Set<AbstractCodeMapping> variableReferences) {
		this.oldVariable = oldVariable;
		this.splitVariables = splitVariables;
		this.operationBefore = operationBefore;
		this.operationAfter = operationAfter;
		this.variableReferences = variableReferences;
	}

	public String getOldVariable() {
		return oldVariable;
	}

	public Set<String> getSplitVariables() {
		return splitVariables;
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

	public Set<UMLAttribute> getSplitAttributes() {
		return splitAttributes;
	}

	public void setSplitAttributes(Set<UMLAttribute> splitAttributes) {
		this.splitAttributes = splitAttributes;
	}

	public UMLAttribute getOldAttribute() {
		return oldAttribute;
	}

	public void setOldAttribute(UMLAttribute oldAttribute) {
		this.oldAttribute = oldAttribute;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Split Attribute").append("\t");
		sb.append(oldVariable);
		sb.append(" to ");
		sb.append(splitVariables);
		sb.append(" in method ");
		sb.append(operationAfter);
		sb.append(" in class ").append(operationAfter.getClassName());
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oldVariable == null) ? 0 : oldVariable.hashCode());
		result = prime * result + ((operationAfter == null) ? 0 : operationAfter.hashCode());
		result = prime * result + ((operationBefore == null) ? 0 : operationBefore.hashCode());
		result = prime * result + ((splitVariables == null) ? 0 : splitVariables.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return oldAttribute.equals(this, obj);
	}

}
