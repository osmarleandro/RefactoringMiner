package gr.uom.java.xmi.diff;

import java.util.Comparator;

public class ClassMoveComparator implements Comparator<UMLClassMoveDiff_RENAMED> {

	@Override
	public int compare(UMLClassMoveDiff_RENAMED o1, UMLClassMoveDiff_RENAMED o2) {
		double sourceFolderDistance1 = o1.getMovedClass().normalizedSourceFolderDistance(o1.getOriginalClass());
		double sourceFolderDistance2 = o2.getMovedClass().normalizedSourceFolderDistance(o2.getOriginalClass());
		return Double.compare(sourceFolderDistance1, sourceFolderDistance2);
	}
}
