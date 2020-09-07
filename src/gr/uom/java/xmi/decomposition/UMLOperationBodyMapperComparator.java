package gr.uom.java.xmi.decomposition;

import java.util.Comparator;

public class UMLOperationBodyMapperComparator implements Comparator<UMLOperationBodyMapper_RENAMED> {

	@Override
	public int compare(UMLOperationBodyMapper_RENAMED o1, UMLOperationBodyMapper_RENAMED o2) {
		int thisOperationNameEditDistance = o1.operationNameEditDistance();
		int otherOperationNameEditDistance = o2.operationNameEditDistance();
		if(thisOperationNameEditDistance != otherOperationNameEditDistance)
			return Integer.compare(thisOperationNameEditDistance, otherOperationNameEditDistance);
		else
			return o1.compareTo(o2);
	}

}
