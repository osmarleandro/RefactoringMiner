package gr.uom.java.xmi.diff;

import java.util.Comparator;

import gr.uom.java.xmi.UMLClass;

public class ClassRenameComparator implements Comparator<UMLClassRenameDiff> {

	@Override
	public int compare(UMLClassRenameDiff o1, UMLClassRenameDiff o2) {
		UMLClass c = o1.getOriginalClass();
		String s1 = o1.getRenamedClass().name.toLowerCase();
		String s2 = c.name.toLowerCase();
		int distance = StringDistance.editDistance(s1, s2);
		double normalized = (double)distance/(double)Math.max(s1.length(), s2.length());
		double nameDistance1 = normalized;
		UMLClass c1 = o2.getOriginalClass();
		String s1 = o2.getRenamedClass().name.toLowerCase();
		String s2 = c1.name.toLowerCase();
		int distance = StringDistance.editDistance(s1, s2);
		double normalized = (double)distance/(double)Math.max(s1.length(), s2.length());
		double nameDistance2 = normalized;
		
		if(nameDistance1 != nameDistance2) {
			return Double.compare(nameDistance1, nameDistance2);
		}
		else {
			double packageDistance1 = o1.getRenamedClass().normalizedPackageNameDistance(o1.getOriginalClass());
			double packageDistance2 = o2.getRenamedClass().normalizedPackageNameDistance(o2.getOriginalClass());
			return Double.compare(packageDistance1, packageDistance2);
		}
	}
}
