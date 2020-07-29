package gr.uom.java.xmi;

import gr.uom.java.xmi.diff.UMLRealizationDiff;

public class UMLRealization implements Comparable<UMLRealization> {
    private UMLClass client;
    private String supplier;

    public UMLRealization(UMLClass client, String supplier) {
        this.supplier = supplier;
        this.client = client;
    }

    public String getSupplier() {
        return supplier;
    }

	public UMLClass getClient() {
        return client;
    }

    public boolean equals(Object o) {
    	if(this == o) {
    		return true;
    	}
    	
    	if(o instanceof UMLRealization) {
    		UMLRealization umlRealization = (UMLRealization)o;
    		return this.client.equals(umlRealization.client) &&
    			this.supplier.equals(umlRealization.supplier);
    	}
    	return false;
    }

    public String toString() {
    	return client + "->" + supplier;
    }

	public int compareTo(UMLRealization realization) {
		return this.toString().compareTo(realization.toString());
	}

	public String toString(UMLRealizationDiff umlRealizationDiff) {
		StringBuilder sb = new StringBuilder();
		if(umlRealizationDiff.parentChanged || umlRealizationDiff.childChanged)
			sb.append("realization ").append(umlRealizationDiff.removedRealization).append(":").append("\n");
		if(umlRealizationDiff.childChanged)
			sb.append("\t").append("child changed from " + umlRealizationDiff.removedRealization.getClient() + " to " + getClient()).append("\n");
		if(umlRealizationDiff.parentChanged)
			sb.append("\t").append("parent changed from " + umlRealizationDiff.removedRealization.getSupplier() + " to " + getSupplier()).append("\n");
		return sb.toString();
	}
}
