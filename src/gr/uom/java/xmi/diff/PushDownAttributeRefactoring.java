package gr.uom.java.xmi.diff;

import java.util.ArrayList;
import java.util.List;

import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.UMLAttribute;

public class PushDownAttributeRefactoring extends MoveAttributeRefactoring {

	public PushDownAttributeRefactoring(UMLAttribute originalAttribute, UMLAttribute movedAttribute) {
		super(originalAttribute, movedAttribute);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName()).append("\t");
		sb.append(getOriginalAttribute().toQualifiedString());
		sb.append(" from class ");
		sb.append(getSourceClassName());
		sb.append(" to ");
		sb.append(getMovedAttribute().toQualifiedString());
		sb.append(" from class ");
		sb.append(getTargetClassName());
		return sb.toString();
	}

	public RefactoringType getRefactoringType() {
		return RefactoringType.PUSH_DOWN_ATTRIBUTE;
	}

	@Override
	public List<CodeRange> rightSide() {
		List<CodeRange> ranges = new ArrayList<CodeRange>();
		ranges.add(movedAttribute.codeRange()
				.setDescription("pushed down attribute declaration")
				.setCodeElement(movedAttribute.toString()));
		return ranges;
	}

	public int hashCode() {
		if(hashCode == 0) {
			int result = 17;
			result = 37*result + originalAttribute.hashCode();
			result = 37*result + movedAttribute.hashCode();
			result = 37*result + getSourceClassName().hashCode();
			result = 37*result + getTargetClassName().hashCode();
			hashCode = result;
		}
		return hashCode;
	}
}
