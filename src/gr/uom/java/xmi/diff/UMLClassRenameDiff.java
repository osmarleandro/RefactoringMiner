package gr.uom.java.xmi.diff;

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

	public int compare(UMLClassRenameDiff o2) {
		double nameDistance1 = getRenamedClass().normalizedNameDistance(getOriginalClass());
		double nameDistance2 = o2.getRenamedClass().normalizedNameDistance(o2.getOriginalClass());
		
		if(nameDistance1 != nameDistance2) {
			return Double.compare(nameDistance1, nameDistance2);
		}
		else {
			double packageDistance1 = getRenamedClass().normalizedPackageNameDistance(getOriginalClass());
			double packageDistance2 = o2.getRenamedClass().normalizedPackageNameDistance(o2.getOriginalClass());
			return Double.compare(packageDistance1, packageDistance2);
		}
	}
}
