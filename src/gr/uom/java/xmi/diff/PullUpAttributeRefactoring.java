package gr.uom.java.xmi.diff;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.UMLAttribute;

public class PullUpAttributeRefactoring extends MoveAttributeRefactoring {

	public PullUpAttributeRefactoring(UMLAttribute originalAttribute, UMLAttribute movedAttribute) {
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
		return RefactoringType.PULL_UP_ATTRIBUTE;
	}

	@Override
	public List<CodeRange> rightSide() {
		List<CodeRange> ranges = new ArrayList<CodeRange>();
		ranges.add(movedAttribute.codeRange()
				.setDescription("pulled up attribute declaration")
				.setCodeElement(movedAttribute.toString()));
		return ranges;
	}

	public Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring() {
		Set<ImmutablePair<String, String>> pairs = new LinkedHashSet<ImmutablePair<String, String>>();
		pairs.add(new ImmutablePair<String, String>(getOriginalAttribute().getLocationInfo().getFilePath(), getOriginalAttribute().getClassName()));
		return pairs;
	}
}
