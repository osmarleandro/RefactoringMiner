package gr.uom.java.xmi;

public class UMLRealization implements Comparable<UMLRealization> {
    UMLClass client;
    String supplier;

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
		return client.equals(this, o);
	}

    public String toString() {
    	return client + "->" + supplier;
    }

	public int compareTo(UMLRealization realization) {
		return this.toString().compareTo(realization.toString());
	}
}
