package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLRealization;

public class UMLRealizationDiff implements Comparable<UMLRealizationDiff> {
	public UMLRealization removedRealization;
	public UMLRealization addedRealization;
	public boolean parentChanged;
	public boolean childChanged;
	
	public UMLRealizationDiff(UMLRealization removedRealization, UMLRealization addedRealization) {
		this.removedRealization = removedRealization;
		this.addedRealization = addedRealization;
		this.parentChanged = false;
		this.childChanged = false;
		if(!removedRealization.getSupplier().equals(addedRealization.getSupplier()))
			parentChanged = true;
		if(!removedRealization.getClient().equals(addedRealization.getClient()))
			childChanged = true;
	}

	public String toString() {
		return addedRealization.toString(this);
	}

	public int compareTo(UMLRealizationDiff generalizationDiff) {
		int compare = this.removedRealization.compareTo(generalizationDiff.removedRealization);
		if(compare == 0)
			return this.addedRealization.compareTo(generalizationDiff.addedRealization);
		else
			return compare;
	}

}
