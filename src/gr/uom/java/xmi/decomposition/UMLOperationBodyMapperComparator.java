package gr.uom.java.xmi.decomposition;

import java.util.Comparator;

public class UMLOperationBodyMapperComparator implements Comparator<UMLOperationBodyMapper> {

	@Override
	public int compare(UMLOperationBodyMapper o1, UMLOperationBodyMapper o2) {
		int thisOperationNameEditDistance = o1.operationNameEditDistance();
		int otherOperationNameEditDistance = o2.operationNameEditDistance();
		if(thisOperationNameEditDistance != otherOperationNameEditDistance)
			return Integer.compare(thisOperationNameEditDistance, otherOperationNameEditDistance);
		else {
			int thisCallChainIntersectionSum = 0;
			for(AbstractCodeMapping mapping : o1.mappings) {
				if(mapping instanceof LeafMapping) {
					thisCallChainIntersectionSum += ((LeafMapping)mapping).callChainIntersection().size();
				}
			}
			int otherCallChainIntersectionSum = 0;
			for(AbstractCodeMapping mapping : o2.mappings) {
				if(mapping instanceof LeafMapping) {
					otherCallChainIntersectionSum += ((LeafMapping)mapping).callChainIntersection().size();
				}
			}
			if(thisCallChainIntersectionSum != otherCallChainIntersectionSum) {
				return -Integer.compare(thisCallChainIntersectionSum, otherCallChainIntersectionSum);
			}
			int thisMappings = o1.mappingsWithoutBlocks();
			for(AbstractCodeMapping mapping : o1.getMappings()) {
				if(mapping.isIdenticalWithExtractedVariable() || mapping.isIdenticalWithInlinedVariable()) {
					thisMappings++;
				}
			}
			int otherMappings = o2.mappingsWithoutBlocks();
			for(AbstractCodeMapping mapping : o2.getMappings()) {
				if(mapping.isIdenticalWithExtractedVariable() || mapping.isIdenticalWithInlinedVariable()) {
					otherMappings++;
				}
			}
			if(thisMappings != otherMappings) {
				return -Integer.compare(thisMappings, otherMappings);
			}
			else {
				int thisExactMatches = o1.exactMatches();
				int otherExactMatches = o2.exactMatches();
				if(thisExactMatches != otherExactMatches) {
					return -Integer.compare(thisExactMatches, otherExactMatches);
				}
				else {
					int thisEditDistance = o1.editDistance();
					int otherEditDistance = o2.editDistance();
					if(thisEditDistance != otherEditDistance) {
						return Integer.compare(thisEditDistance, otherEditDistance);
					}
					else {
						int thisOperationNameEditDistance1 = o1.operationNameEditDistance();
						int otherOperationNameEditDistance1 = o2.operationNameEditDistance();
						return Integer.compare(thisOperationNameEditDistance1, otherOperationNameEditDistance1);
					}
				}
			}
		}
	}

}
