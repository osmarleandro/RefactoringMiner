package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLParameter;

public class UMLParameterDiff {
	public UMLParameter removedParameter;
	public UMLParameter addedParameter;
	public boolean typeChanged;
	public boolean qualifiedTypeChanged;
	public boolean nameChanged;
	
	public UMLParameterDiff(UMLParameter removedParameter, UMLParameter addedParameter) {
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

	public UMLParameter getRemovedParameter() {
		return removedParameter;
	}

	public UMLParameter getAddedParameter() {
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
		return addedParameter.toString(this);
	}
}
