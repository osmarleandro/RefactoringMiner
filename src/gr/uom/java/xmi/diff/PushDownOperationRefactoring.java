package gr.uom.java.xmi.diff;

import java.util.ArrayList;
import java.util.List;

import org.refactoringminer.api.RefactoringType_RENAMED;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper;

public class PushDownOperationRefactoring extends MoveOperationRefactoring {

	public PushDownOperationRefactoring(UMLOperationBodyMapper bodyMapper) {
		super(bodyMapper);
	}

	public PushDownOperationRefactoring(UMLOperation originalOperation, UMLOperation movedOperation) {
		super(originalOperation, movedOperation);
	}

	public RefactoringType_RENAMED getRefactoringType() {
		return RefactoringType_RENAMED.PUSH_DOWN_OPERATION;
	}

	@Override
	public List<CodeRange> rightSide() {
		List<CodeRange> ranges = new ArrayList<CodeRange>();
		ranges.add(movedOperation.codeRange()
				.setDescription("pushed down method declaration")
				.setCodeElement(movedOperation.toString()));
		return ranges;
	}
}
