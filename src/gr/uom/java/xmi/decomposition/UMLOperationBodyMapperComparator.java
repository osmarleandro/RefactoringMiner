package gr.uom.java.xmi.decomposition;

import java.util.Comparator;

public class UMLOperationBodyMapperComparator implements Comparator<UMLOperationBodyMapper> {

	@Override
	public int compare(UMLOperationBodyMapper o1, UMLOperationBodyMapper o2) {
		int thisOperationNameEditDistance = o1.operationNameEditDistance_RENAMED();
		int otherOperationNameEditDistance = o2.operationNameEditDistance_RENAMED();
		if(thisOperationNameEditDistance != otherOperationNameEditDistance)
			return Integer.compare(thisOperationNameEditDistance, otherOperationNameEditDistance);
		else
			return o1.compareTo(o2);
	}

}
