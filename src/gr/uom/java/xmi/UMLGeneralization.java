package gr.uom.java.xmi;

import gr.uom.java.xmi.diff.UMLGeneralizationDiff;

public class UMLGeneralization implements Comparable<UMLGeneralization> {
    private UMLClass child;
    private String parent;

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

	public String toString(UMLGeneralizationDiff umlGeneralizationDiff) {
		StringBuilder sb = new StringBuilder();
		if(umlGeneralizationDiff.parentChanged || umlGeneralizationDiff.childChanged)
			sb.append("generalization ").append(umlGeneralizationDiff.removedGeneralization).append(":").append("\n");
		if(umlGeneralizationDiff.childChanged)
			sb.append("\t").append("child changed from " + umlGeneralizationDiff.removedGeneralization.getChild() + " to " + getChild()).append("\n");
		if(umlGeneralizationDiff.parentChanged)
			sb.append("\t").append("parent changed from " + umlGeneralizationDiff.removedGeneralization.getParent() + " to " + getParent()).append("\n");
		return sb.toString();
	}
}
