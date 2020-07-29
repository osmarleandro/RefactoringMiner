package gr.uom.java.xmi.diff;

import java.util.Comparator;

public class ClassMoveComparator implements Comparator<UMLClassMoveDiff> {

	@Override
	public int compare(UMLClassMoveDiff o1, UMLClassMoveDiff o2) {
		double sourceFolderDistance1 = o1.getMovedClass().normalizedSourceFolderDistance(o1.getModelDiff().getOriginalClass(this));
		double sourceFolderDistance2 = o2.getMovedClass().normalizedSourceFolderDistance(o2.getModelDiff().getOriginalClass(this));
		return Double.compare(sourceFolderDistance1, sourceFolderDistance2);
	}
}
