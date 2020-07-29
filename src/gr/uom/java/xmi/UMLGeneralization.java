package gr.uom.java.xmi;

public class UMLGeneralization implements Comparable<UMLGeneralization> {
    UMLClass child;
    String parent;

    public UMLGeneralization(UMLClass child, String parent) {
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
		return child.equals(this, o);
	}

    public String toString() {
    	return child + "->" + parent;
    }

	public int compareTo(UMLGeneralization generalization) {
		return this.toString().compareTo(generalization.toString());
	}
}
