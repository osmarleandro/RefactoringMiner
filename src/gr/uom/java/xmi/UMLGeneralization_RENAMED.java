package gr.uom.java.xmi;

public class UMLGeneralization_RENAMED implements Comparable<UMLGeneralization_RENAMED> {
    private UMLClass child;
    private String parent;

    public UMLGeneralization_RENAMED(UMLClass child, String parent) {
        this.child = child;
        this.parent = parent;
    }

    public UMLClass getChild() {
        return child;
    }

	public String getParent() {
        return parent;
    }

	public boolean equals(Object o) {
    	if(this == o) {
    		return true;
    	}
    	
    	if(o instanceof UMLGeneralization_RENAMED) {
    		UMLGeneralization_RENAMED umlGeneralization = (UMLGeneralization_RENAMED)o;
    		return this.child.equals(umlGeneralization.child) &&
    			this.parent.equals(umlGeneralization.parent);
    	}
    	return false;
    }

    public String toString() {
    	return child + "->" + parent;
    }

	public int compareTo(UMLGeneralization_RENAMED generalization) {
		return this.toString().compareTo(generalization.toString());
	}
}
