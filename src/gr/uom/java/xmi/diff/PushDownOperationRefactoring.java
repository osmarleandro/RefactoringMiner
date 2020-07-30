package gr.uom.java.xmi.diff;

import java.util.ArrayList;
import java.util.List;

import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper;

public class PushDownOperationRefactoring extends MoveOperationRefactoring {

	public PushDownOperationRefactoring(UMLOperationBodyMapper bodyMapper) {
		super(bodyMapper);
	}

	public PushDownOperationRefactoring(UMLOperation originalOperation, UMLOperation movedOperation) {
		super(originalOperation, movedOperation);
	}

	public RefactoringType getRefactoringType() {
		return RefactoringType.PUSH_DOWN_OPERATION;
	}

	@Override
	public List<CodeRange> rightSide() {
		List<CodeRange> ranges = new ArrayList<CodeRange>();
		ranges.add(movedOperation.codeRange()
				.setDescription("pushed down method declaration")
				.setCodeElement(movedOperation.toString()));
		return ranges;
	}

	public boolean compatibleWith(MoveAttributeRefactoring ref) {
		if(ref.getMovedAttribute().getClassName().equals(this.movedOperation.getClassName()) &&
				ref.getOriginalAttribute().getClassName().equals(this.originalOperation.getClassName())) {
			List<String> originalOperationVariables = this.originalOperation.getAllVariables();
			List<String> movedOperationVariables = this.movedOperation.getAllVariables();
			return originalOperationVariables.contains(ref.getOriginalAttribute().getName()) &&
					movedOperationVariables.contains(ref.getMovedAttribute().getName());
		}
		return false;
	}
}
