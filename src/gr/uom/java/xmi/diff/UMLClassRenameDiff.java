package gr.uom.java.xmi.diff;

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

	protected void processOperations() throws RefactoringMinerTimedOutException {
		for(UMLOperation operation : originalClass.getOperations()) {
			UMLOperation operationWithTheSameSignature = nextClass.operationWithTheSameSignatureIgnoringChangedTypes(operation);
			if(operationWithTheSameSignature == null) {
				this.removedOperations.add(operation);
			}
			else if(!mapperListContainsOperation(operation, operationWithTheSameSignature)) {
				UMLOperationBodyMapper mapper = new UMLOperationBodyMapper(operation, operationWithTheSameSignature, this);
				this.operationBodyMapperList.add(mapper);
			}
		}
		for(UMLOperation operation : nextClass.getOperations()) {
			UMLOperation operationWithTheSameSignature = originalClass.operationWithTheSameSignatureIgnoringChangedTypes(operation);
			if(operationWithTheSameSignature == null) {
				this.addedOperations.add(operation);
			}
			else if(!mapperListContainsOperation(operationWithTheSameSignature, operation)) {
				UMLOperationBodyMapper mapper = new UMLOperationBodyMapper(operationWithTheSameSignature, operation, this);
				this.operationBodyMapperList.add(mapper);
			}
		}
	}
}
