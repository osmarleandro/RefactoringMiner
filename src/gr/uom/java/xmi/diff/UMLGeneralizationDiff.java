package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLGeneralization;

public class UMLGeneralizationDiff implements Comparable<UMLGeneralizationDiff> {
	private UMLGeneralization removedGeneralization;
	private UMLGeneralization addedGeneralization;
	private boolean parentChanged;
	private boolean childChanged;
	
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
		StringBuilder sb = new StringBuilder();
		if(parentChanged || childChanged)
			sb.append("generalization ").append(removedGeneralization).append(":").append("\n");
		if(childChanged)
			sb.append("\t").append("child changed from " + removedGeneralization.getChild() + " to " + addedGeneralization.getChild()).append("\n");
		if(parentChanged)
			sb.append("\t").append("parent changed from " + removedGeneralization.getParent() + " to " + addedGeneralization.getParent()).append("\n");
		return sb.toString();
	}

	public int compareTo(UMLGeneralizationDiff generalizationDiff) {
		UMLGeneralization generalization = generalizationDiff.removedGeneralization;
		int compare = this.removedGeneralization.toString().compareTo(generalization.toString());
		if(compare == 0) {
			UMLGeneralization generalization1 = generalizationDiff.addedGeneralization;
			return this.addedGeneralization.toString().compareTo(generalization1.toString());
		} else
			return compare;
	}
}
