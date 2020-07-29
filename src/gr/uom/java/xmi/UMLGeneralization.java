package gr.uom.java.xmi;

import java.util.ListIterator;

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

	public UMLGeneralization matchGeneralization(UMLModel umlModel) {
		ListIterator<UMLGeneralization> generalizationIt = umlModel.generalizationList.listIterator();
		while(generalizationIt.hasNext()) {
			UMLGeneralization generalization = generalizationIt.next();
			if(generalization.getChild().equals(getChild())) {
				String thisParent = generalization.getParent();
				String otherParent = getParent();
				String thisParentComparedString = null;
				if(thisParent.contains("."))
					thisParentComparedString = thisParent.substring(thisParent.lastIndexOf(".")+1);
				else
					thisParentComparedString = thisParent;
				String otherParentComparedString = null;
				if(otherParent.contains("."))
					otherParentComparedString = otherParent.substring(otherParent.lastIndexOf(".")+1);
				else
					otherParentComparedString = otherParent;
				if(thisParentComparedString.equals(otherParentComparedString))
					return generalization;
			}
		}
		return null;
	}
}
