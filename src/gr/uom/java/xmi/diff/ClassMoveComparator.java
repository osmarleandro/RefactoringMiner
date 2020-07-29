package gr.uom.java.xmi.diff;

import java.util.Comparator;

public class ClassMoveComparator implements Comparator<UMLClassMoveDiff> {

	@Override
	public int compare(UMLClassMoveDiff o1, UMLClassMoveDiff o2) {
		double sourceFolderDistance1 = o1.getOriginalClass().normalizedSourceFolderDistance(o1.getMovedClass());
		double sourceFolderDistance2 = o2.getOriginalClass().normalizedSourceFolderDistance(o2.getMovedClass());
		return Double.compare(sourceFolderDistance1, sourceFolderDistance2);
	}
}
