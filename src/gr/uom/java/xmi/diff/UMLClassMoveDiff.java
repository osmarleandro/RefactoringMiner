package gr.uom.java.xmi.diff;

import java.util.List;

import org.refactoringminer.api.Refactoring;

import gr.uom.java.xmi.UMLAttribute;
import gr.uom.java.xmi.UMLClass;

public class UMLClassMoveDiff extends UMLClassBaseDiff {
	
	public UMLClassMoveDiff(UMLClass originalClass, UMLClass movedClass, UMLModelDiff modelDiff) {
		super(originalClass, movedClass, modelDiff);
	}

	public UMLClass getMovedClass() {
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

	private boolean attributeSplit(UMLAttribute a1, UMLAttribute a2, List<Refactoring> refactorings) {
		for(Refactoring refactoring : refactorings) {
			if(refactoring instanceof SplitAttributeRefactoring) {
				SplitAttributeRefactoring split = (SplitAttributeRefactoring)refactoring;
				if(split.getSplitAttributes().contains(a2.getVariableDeclaration()) && split.getOldAttribute().equals(a1.getVariableDeclaration())) {
					return true;
				}
			}
		}
		return false;
	}
}
