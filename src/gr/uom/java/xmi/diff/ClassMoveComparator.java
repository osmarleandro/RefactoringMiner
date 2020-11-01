package gr.uom.java.xmi.diff;

import java.util.Comparator;

import gr.uom.java.xmi.UMLClass;

public class ClassMoveComparator implements Comparator<UMLClassMoveDiff> {

	@Override
	public int compare(UMLClassMoveDiff o1, UMLClassMoveDiff o2) {
		UMLClass c = o1.getOriginalClass();
		String s1 = o1.getMovedClass().sourceFolder.toLowerCase();
		String s2 = c.sourceFolder.toLowerCase();
		int distance = StringDistance.editDistance(s1, s2);
		double normalized = (double)distance/(double)Math.max(s1.length(), s2.length());
		double sourceFolderDistance1 = normalized;
		UMLClass c1 = o2.getOriginalClass();
		String s1 = o2.getMovedClass().sourceFolder.toLowerCase();
		String s2 = c1.sourceFolder.toLowerCase();
		int distance = StringDistance.editDistance(s1, s2);
		double normalized = (double)distance/(double)Math.max(s1.length(), s2.length());
		double sourceFolderDistance2 = normalized;
		return Double.compare(sourceFolderDistance1, sourceFolderDistance2);
	}
}
