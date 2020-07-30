package gr.uom.java.xmi.diff;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.UMLAttribute;

public class MoveAndRenameAttributeRefactoring extends MoveAttributeRefactoring {
	private Set<CandidateAttributeRefactoring> attributeRenames;
	
	public MoveAndRenameAttributeRefactoring(UMLAttribute originalAttribute, UMLAttribute movedAttribute,
			Set<CandidateAttributeRefactoring> attributeRenames) {
		super(originalAttribute, movedAttribute);
		this.attributeRenames = attributeRenames;
	}

	public Set<CandidateAttributeRefactoring> getAttributeRenames() {
		return attributeRenames;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName()).append("\t");
		sb.append(originalAttribute.toQualifiedString());
		sb.append(" renamed to ");
		sb.append(movedAttribute.toQualifiedString());
		sb.append(" and moved from class ");
		sb.append(getSourceClassName());
		sb.append(" to class ");
		sb.append(getTargetClassName());
		return sb.toString();
	}

	public String getName() {
		return this.getRefactoringType().getDisplayName();
	}

	public RefactoringType getRefactoringType() {
		return RefactoringType.MOVE_RENAME_ATTRIBUTE;
	}

	@Override
	public List<CodeRange> rightSide() {
		List<CodeRange> ranges = new ArrayList<CodeRange>();
		ranges.add(movedAttribute.codeRange()
				.setDescription("moved and renamed attribute declaration")
				.setCodeElement(movedAttribute.toString()));
		return ranges;
	}

	public Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring() {
		Set<ImmutablePair<String, String>> pairs = new LinkedHashSet<ImmutablePair<String, String>>();
		pairs.add(new ImmutablePair<String, String>(getOriginalAttribute().getLocationInfo().getFilePath(), getOriginalAttribute().getClassName()));
		return pairs;
	}
}
