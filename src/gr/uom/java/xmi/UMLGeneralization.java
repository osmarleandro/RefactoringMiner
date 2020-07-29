package gr.uom.java.xmi;

public class UMLGeneralization implements Comparable<UMLGeneralization> {
    UMLClass child;
    private String parent;

    public UMLGeneralization(UMLClass child, String parent) {
        this.child = child;
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }

	public boolean equals(Object o) {
    	if(this == o) {
    		return true;
    	}
    	
    	if(o instanceof UMLGeneralization) {
    		UMLGeneralization umlGeneralization = (UMLGeneralization)o;
    		return this.child.equals(umlGeneralization.child) &&
    			this.parent.equals(umlGeneralization.parent);
    	}
    	return false;
    }

    public String toString() {
    	return child + "->" + parent;
    }

	public int compareTo(UMLGeneralization generalization) {
		return this.toString().compareTo(generalization.toString());
	}
}
