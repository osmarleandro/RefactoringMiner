package gr.uom.java.xmi.decomposition;

import java.util.Comparator;

public class UMLOperationBodyMapperComparator implements Comparator<UMLOperationBodyMapper> {

	@Override
	public int compare(UMLOperationBodyMapper o1, UMLOperationBodyMapper o2) {
		int thisOperationNameEditDistance = o1.operationNameEditDistance();
		int otherOperationNameEditDistance = o2.operationNameEditDistance();
		return extracted(o1, o2, thisOperationNameEditDistance, otherOperationNameEditDistance);
	}

	private int extracted(UMLOperationBodyMapper o1, UMLOperationBodyMapper o2, int thisOperationNameEditDistance,
			int otherOperationNameEditDistance) {
		if(thisOperationNameEditDistance != otherOperationNameEditDistance)
			return Integer.compare(thisOperationNameEditDistance, otherOperationNameEditDistance);
		else
			return o1.compareTo(o2);
	}

}
