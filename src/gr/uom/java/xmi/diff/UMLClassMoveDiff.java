package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLClass_RENAMED;

public class UMLClassMoveDiff extends UMLClassBaseDiff {
	
	public UMLClassMoveDiff(UMLClass_RENAMED originalClass, UMLClass_RENAMED movedClass, UMLModelDiff modelDiff) {
		super(originalClass, movedClass, modelDiff);
	}

	public UMLClass_RENAMED getMovedClass() {
		return nextClass;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ");
		sb.append(originalClass.getName());
		sb.append(" was moved to ");
		sb.append(nextClass.getName());
		sb.append("\n");
		return sb.toString();
	}

	public boolean equals(Object o) {
		if(this == o) {
    		return true;
    	}
		
		if(o instanceof UMLClassMoveDiff) {
			UMLClassMoveDiff classMoveDiff = (UMLClassMoveDiff)o;
			return this.originalClass.equals(classMoveDiff.originalClass) && this.nextClass.equals(classMoveDiff.nextClass);
		}
		return false;
	}
}
