package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLRealization;

public class UMLRealizationDiff implements Comparable<UMLRealizationDiff> {
	private UMLRealization removedRealization;
	private UMLRealization addedRealization;
	private boolean parentChanged;
	private boolean childChanged;
	
	public UMLRealizationDiff(UMLRealization removedRealization, UMLRealization addedRealization) {
		this.removedRealization = removedRealization;
		this.addedRealization = addedRealization;
		this.parentChanged = false;
		this.childChanged = false;
		if(!removedRealization.getSupplier().equals(addedRealization.getSupplier()))
			parentChanged = true;
		if(!removedRealization.client.equals(addedRealization.client))
			childChanged = true;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(parentChanged || childChanged)
			sb.append("realization ").append(removedRealization).append(":").append("\n");
		if(childChanged)
			sb.append("\t").append("child changed from " + removedRealization.client + " to " + addedRealization.client).append("\n");
		if(parentChanged)
			sb.append("\t").append("parent changed from " + removedRealization.getSupplier() + " to " + addedRealization.getSupplier()).append("\n");
		return sb.toString();
	}

	public int compareTo(UMLRealizationDiff generalizationDiff) {
		int compare = this.removedRealization.compareTo(generalizationDiff.removedRealization);
		if(compare == 0)
			return this.addedRealization.compareTo(generalizationDiff.addedRealization);
		else
			return compare;
	}

}
