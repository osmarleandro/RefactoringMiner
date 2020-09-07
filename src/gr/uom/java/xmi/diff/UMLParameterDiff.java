package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLParameter_RENAMED;

public class UMLParameterDiff {
	private UMLParameter_RENAMED removedParameter;
	private UMLParameter_RENAMED addedParameter;
	private boolean typeChanged;
	private boolean qualifiedTypeChanged;
	private boolean nameChanged;
	
	public UMLParameterDiff(UMLParameter_RENAMED removedParameter, UMLParameter_RENAMED addedParameter) {
		this.removedParameter = removedParameter;
		this.addedParameter = addedParameter;
		this.typeChanged = false;
		this.nameChanged = false;
		if(!removedParameter.getType().equals(addedParameter.getType()))
			typeChanged = true;
		else if(!removedParameter.getType().equalsQualified(addedParameter.getType()))
			qualifiedTypeChanged = true;
		if(!removedParameter.getName().equals(addedParameter.getName()))
			nameChanged = true;
	}

	public UMLParameter_RENAMED getRemovedParameter() {
		return removedParameter;
	}

	public UMLParameter_RENAMED getAddedParameter() {
		return addedParameter;
	}

	public boolean isTypeChanged() {
		return typeChanged;
	}

	public boolean isQualifiedTypeChanged() {
		return qualifiedTypeChanged;
	}

	public boolean isNameChanged() {
		return nameChanged;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(typeChanged || nameChanged || qualifiedTypeChanged)
			sb.append("\t\t").append("parameter ").append(removedParameter).append(":").append("\n");
		if(typeChanged || qualifiedTypeChanged)
			sb.append("\t\t").append("type changed from " + removedParameter.getType() + " to " + addedParameter.getType()).append("\n");
		if(nameChanged)
			sb.append("\t\t").append("name changed from " + removedParameter.getName() + " to " + addedParameter.getName()).append("\n");
		return sb.toString();
	}
}
