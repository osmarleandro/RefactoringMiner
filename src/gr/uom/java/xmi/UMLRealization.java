package gr.uom.java.xmi;

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
    	
    	if(o instanceof UMLRealization)
			return extracted(o);
    	return false;
    }

	private boolean extracted(Object o) {
		{
    		UMLRealization umlRealization = (UMLRealization)o;
    		return this.client.equals(umlRealization.client) &&
    			this.supplier.equals(umlRealization.supplier);
    	}
	}

    public String toString() {
    	return client + "->" + supplier;
    }

	public int compareTo(UMLRealization realization) {
		return this.toString().compareTo(realization.toString());
	}
}
