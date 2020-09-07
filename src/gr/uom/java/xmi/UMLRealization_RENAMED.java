package gr.uom.java.xmi;

public class UMLRealization_RENAMED implements Comparable<UMLRealization_RENAMED> {
    private UMLClass client;
    private String supplier;

    public UMLRealization_RENAMED(UMLClass client, String supplier) {
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
    	
    	if(o instanceof UMLRealization_RENAMED) {
    		UMLRealization_RENAMED umlRealization = (UMLRealization_RENAMED)o;
    		return this.client.equals(umlRealization.client) &&
    			this.supplier.equals(umlRealization.supplier);
    	}
    	return false;
    }

    public String toString() {
    	return client + "->" + supplier;
    }

	public int compareTo(UMLRealization_RENAMED realization) {
		return this.toString().compareTo(realization.toString());
	}
}
