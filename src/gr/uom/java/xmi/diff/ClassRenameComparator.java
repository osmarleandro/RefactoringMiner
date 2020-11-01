package gr.uom.java.xmi.diff;

import java.util.Comparator;

import gr.uom.java.xmi.UMLClass;

public class ClassRenameComparator implements Comparator<UMLClassRenameDiff> {

	@Override
	public int compare(UMLClassRenameDiff o1, UMLClassRenameDiff o2) {
		double nameDistance1 = o1.getRenamedClass().normalizedNameDistance(o1.getOriginalClass());
		double nameDistance2 = o2.getRenamedClass().normalizedNameDistance(o2.getOriginalClass());
		
		if(nameDistance1 != nameDistance2) {
			return Double.compare(nameDistance1, nameDistance2);
		}
		else {
			UMLClass c = o1.getOriginalClass();
			String s1 = o1.getRenamedClass().packageName.toLowerCase();
			String s2 = c.packageName.toLowerCase();
			int distance = StringDistance.editDistance(s1, s2);
			double normalized = (double)distance/(double)Math.max(s1.length(), s2.length());
			double packageDistance1 = normalized;
			UMLClass c1 = o2.getOriginalClass();
			String s1 = o2.getRenamedClass().packageName.toLowerCase();
			String s2 = c1.packageName.toLowerCase();
			int distance = StringDistance.editDistance(s1, s2);
			double normalized = (double)distance/(double)Math.max(s1.length(), s2.length());
			double packageDistance2 = normalized;
			return Double.compare(packageDistance1, packageDistance2);
		}
	}
}
