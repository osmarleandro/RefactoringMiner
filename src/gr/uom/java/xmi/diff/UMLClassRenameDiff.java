package gr.uom.java.xmi.diff;

import java.util.TreeSet;

import org.refactoringminer.api.RefactoringMinerTimedOutException;

import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper;

public class UMLClassRenameDiff extends UMLClassBaseDiff {
	
	public UMLClassRenameDiff(UMLClass originalClass, UMLClass renamedClass, UMLModelDiff modelDiff) {
		super(originalClass, renamedClass, modelDiff);
	}

	public UMLClass getRenamedClass() {
		return nextClass;
	}

	public boolean samePackage() {
		return originalClass.getPackageName().equals(nextClass.getPackageName());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ");
		sb.append(originalClass.getName());
		sb.append(" was renamed to ");
		sb.append(nextClass.getName());
		sb.append("\n");
		return sb.toString();
	}

	private void updateMapperSet(TreeSet<UMLOperationBodyMapper> mapperSet, UMLOperation removedOperation, UMLOperation operationInsideAnonymousClass, UMLOperation addedOperation, int differenceInPosition)
			throws RefactoringMinerTimedOutException {
				UMLOperationBodyMapper operationBodyMapper = new UMLOperationBodyMapper(removedOperation, operationInsideAnonymousClass, this);
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
			}
}
