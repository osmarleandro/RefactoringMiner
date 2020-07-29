package gr.uom.java.xmi;

import java.util.ListIterator;

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

	public UMLRealization matchRealization(UMLModel umlModel) {
		ListIterator<UMLRealization> realizationIt = umlModel.realizationList.listIterator();
		while(realizationIt.hasNext()) {
			UMLRealization realization = realizationIt.next();
			if(realization.getClient().equals(getClient())) {
				String thisSupplier = realization.getSupplier();
				String otherSupplier = getSupplier();
				String thisSupplierComparedString = null;
				if(thisSupplier.contains("."))
					thisSupplierComparedString = thisSupplier.substring(thisSupplier.lastIndexOf(".")+1);
				else
					thisSupplierComparedString = thisSupplier;
				String otherSupplierComparedString = null;
				if(otherSupplier.contains("."))
					otherSupplierComparedString = otherSupplier.substring(otherSupplier.lastIndexOf(".")+1);
				else
					otherSupplierComparedString = otherSupplier;
				if(thisSupplierComparedString.equals(otherSupplierComparedString))
					return realization;
			}
		}
		return null;
	}
}
