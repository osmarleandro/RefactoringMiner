package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLGeneralization;

public class UMLGeneralizationDiff implements Comparable<UMLGeneralizationDiff> {
	public UMLGeneralization removedGeneralization;
	public UMLGeneralization addedGeneralization;
	public boolean parentChanged;
	public boolean childChanged;
	
	public UMLGeneralizationDiff(UMLGeneralization removedGeneralization, UMLGeneralization addedGeneralization) {
		this.removedGeneralization = removedGeneralization;
		this.addedGeneralization = addedGeneralization;
		this.parentChanged = false;
		this.childChanged = false;
		if(!removedGeneralization.getParent().equals(addedGeneralization.getParent()))
			parentChanged = true;
		if(!removedGeneralization.getChild().equals(addedGeneralization.getChild()))
			childChanged = true;
	}

	public UMLGeneralization getRemovedGeneralization() {
		return removedGeneralization;
	}

	public UMLGeneralization getAddedGeneralization() {
		return addedGeneralization;
	}

	public String toString() {
		return addedGeneralization.toString(this);
	}

	public int compareTo(UMLGeneralizationDiff generalizationDiff) {
		int compare = this.removedGeneralization.compareTo(generalizationDiff.removedGeneralization);
		if(compare == 0)
			return this.addedGeneralization.compareTo(generalizationDiff.addedGeneralization);
		else
			return compare;
	}
}
