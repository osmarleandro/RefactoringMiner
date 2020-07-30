package gr.uom.java.xmi.diff;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.refactoringminer.api.RefactoringMinerTimedOutException;

import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper;
import gr.uom.java.xmi.decomposition.replacement.MethodInvocationReplacement;

public class UMLClassMoveDiff extends UMLClassBaseDiff {
	
	public UMLClassMoveDiff(UMLClass originalClass, UMLClass movedClass, UMLModelDiff modelDiff) {
		super(originalClass, movedClass, modelDiff);
	}

	public UMLClass getMovedClass() {
		return nextClass;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ");
		sb.append(originalClass.getName());
		sb.append(" was moved to ");
		sb.append(nextClass.getName());
		sb.append("\n");
		return sb.toString();
	}

	public boolean equals(Object o) {
		if(this == o) {
    		return true;
    	}
		
		if(o instanceof UMLClassMoveDiff) {
			UMLClassMoveDiff classMoveDiff = (UMLClassMoveDiff)o;
			return this.originalClass.equals(classMoveDiff.originalClass) && this.nextClass.equals(classMoveDiff.nextClass);
		}
		return false;
	}

	private void updateMapperSet(TreeSet<UMLOperationBodyMapper> mapperSet, UMLOperation removedOperation, UMLOperation addedOperation, int differenceInPosition)
			throws RefactoringMinerTimedOutException {
				UMLOperationBodyMapper operationBodyMapper = new UMLOperationBodyMapper(removedOperation, addedOperation, this);
				List<AbstractCodeMapping> totalMappings = new ArrayList<AbstractCodeMapping>(operationBodyMapper.getMappings());
				int mappings = operationBodyMapper.mappingsWithoutBlocks();
				if(mappings > 0) {
					int absoluteDifferenceInPosition = computeAbsoluteDifferenceInPositionWithinClass(removedOperation, addedOperation);
					if(exactMappings(operationBodyMapper)) {
						mapperSet.add(operationBodyMapper);
					}
					else if(mappedElementsMoreThanNonMappedT1AndT2(mappings, operationBodyMapper) &&
							absoluteDifferenceInPosition <= differenceInPosition &&
							compatibleSignatures(removedOperation, addedOperation, absoluteDifferenceInPosition)) {
						mapperSet.add(operationBodyMapper);
					}
					else if(mappedElementsMoreThanNonMappedT2(mappings, operationBodyMapper) &&
							absoluteDifferenceInPosition <= differenceInPosition &&
							isPartOfMethodExtracted(removedOperation, addedOperation)) {
						mapperSet.add(operationBodyMapper);
					}
					else if(mappedElementsMoreThanNonMappedT1(mappings, operationBodyMapper) &&
							absoluteDifferenceInPosition <= differenceInPosition &&
							isPartOfMethodInlined(removedOperation, addedOperation)) {
						mapperSet.add(operationBodyMapper);
					}
				}
				else {
					for(MethodInvocationReplacement replacement : consistentMethodInvocationRenames) {
						if(replacement.getInvokedOperationBefore().matchesOperation(removedOperation) &&
								replacement.getInvokedOperationAfter().matchesOperation(addedOperation)) {
							mapperSet.add(operationBodyMapper);
							break;
						}
					}
				}
				if(totalMappings.size() > 0) {
					int absoluteDifferenceInPosition = computeAbsoluteDifferenceInPositionWithinClass(removedOperation, addedOperation);
					if(singleUnmatchedStatementCallsAddedOperation(operationBodyMapper) &&
							absoluteDifferenceInPosition <= differenceInPosition &&
							compatibleSignatures(removedOperation, addedOperation, absoluteDifferenceInPosition)) {
						mapperSet.add(operationBodyMapper);
					}
				}
			}
}
