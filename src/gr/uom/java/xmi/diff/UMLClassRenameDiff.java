package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLAnonymousClass;
import gr.uom.java.xmi.UMLClass;

public class UMLClassRenameDiff extends UMLClassBaseDiff {
	
	public UMLClassRenameDiff(UMLClass originalClass, UMLClass renamedClass, UMLModelDiff modelDiff) {
		super(originalClass, renamedClass, modelDiff);
	}

	public UMLClass getRenamedClass() {
		return nextClass;
	}

	public boolean samePackage() {
		return originalClass.getPackageName().equals(nextClass.getPackageName());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ");
		sb.append(originalClass.getName());
		sb.append(" was renamed to ");
		sb.append(nextClass.getName());
		sb.append("\n");
		return sb.toString();
	}

	protected void processAnonymousClasses() {
		for(UMLAnonymousClass umlAnonymousClass : originalClass.getAnonymousClassList()) {
			if(!nextClass.containsAnonymousWithSameAttributesAndOperations(umlAnonymousClass))
				this.removedAnonymousClasses.add(umlAnonymousClass);
		}
		for(UMLAnonymousClass umlAnonymousClass : nextClass.getAnonymousClassList()) {
			if(!originalClass.containsAnonymousWithSameAttributesAndOperations(umlAnonymousClass))
				this.addedAnonymousClasses.add(umlAnonymousClass);
		}
	}
}
