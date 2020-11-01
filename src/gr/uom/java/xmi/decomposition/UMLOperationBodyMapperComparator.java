package gr.uom.java.xmi.decomposition;

import java.util.Comparator;

import gr.uom.java.xmi.diff.StringDistance;

public class UMLOperationBodyMapperComparator implements Comparator<UMLOperationBodyMapper> {

	@Override
	public int compare(UMLOperationBodyMapper o1, UMLOperationBodyMapper o2) {
		int thisOperationNameEditDistance = StringDistance.editDistance(o1.operation1.getName(), o1.operation2.getName());
		int otherOperationNameEditDistance = StringDistance.editDistance(o2.operation1.getName(), o2.operation2.getName());
		if(thisOperationNameEditDistance != otherOperationNameEditDistance)
			return Integer.compare(thisOperationNameEditDistance, otherOperationNameEditDistance);
		else
			return o1.compareTo(o2);
	}

}
