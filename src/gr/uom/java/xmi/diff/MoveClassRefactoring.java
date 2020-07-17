package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLClass;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringType;
import org.refactoringminer.util.PrefixSuffixUtils;

public class MoveClassRefactoring implements Refactoring {
	private UMLClass originalClass;
	private UMLClass movedClass;
	
	public MoveClassRefactoring(UMLClass originalClass,  UMLClass movedClass) {
		this.originalClass = originalClass;
		this.movedClass = movedClass;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName()).append("\t");
		sb.append(originalClass.getName_RENAMED());
		sb.append(" moved to ");
		sb.append(movedClass.getName_RENAMED());
		return sb.toString();
	}

	public RenamePattern getRenamePattern() {
		int separatorPos = PrefixSuffixUtils.separatorPosOfCommonSuffix('.', originalClass.getName_RENAMED(), movedClass.getName_RENAMED());
		if (separatorPos == -1) {
			return new RenamePattern(originalClass.getName_RENAMED(), movedClass.getName_RENAMED());
		}
		String originalPath = originalClass.getName_RENAMED().substring(0, originalClass.getName_RENAMED().length() - separatorPos);
		String movedPath = movedClass.getName_RENAMED().substring(0, movedClass.getName_RENAMED().length() - separatorPos);
		return new RenamePattern(originalPath, movedPath);
	}

	public String getName() {
		return this.getRefactoringType().getDisplayName();
	}

	public RefactoringType getRefactoringType() {
		return RefactoringType.MOVE_CLASS;
	}

	public String getOriginalClassName() {
		return originalClass.getName_RENAMED();
	}

	public String getMovedClassName() {
		return movedClass.getName_RENAMED();
	}

	public UMLClass getOriginalClass() {
		return originalClass;
	}

	public UMLClass getMovedClass() {
		return movedClass;
	}

	public Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring() {
		Set<ImmutablePair<String, String>> pairs = new LinkedHashSet<ImmutablePair<String, String>>();
		pairs.add(new ImmutablePair<String, String>(getOriginalClass().getLocationInfo().getFilePath(), getOriginalClass().getName_RENAMED()));
		return pairs;
	}

	public Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring() {
		Set<ImmutablePair<String, String>> pairs = new LinkedHashSet<ImmutablePair<String, String>>();
		pairs.add(new ImmutablePair<String, String>(getMovedClass().getLocationInfo().getFilePath(), getMovedClass().getName_RENAMED()));
		return pairs;
	}

	@Override
	public List<CodeRange> leftSide() {
		List<CodeRange> ranges = new ArrayList<CodeRange>();
		ranges.add(originalClass.codeRange()
				.setDescription("original type declaration")
				.setCodeElement(originalClass.getName_RENAMED()));
		return ranges;
	}

	@Override
	public List<CodeRange> rightSide() {
		List<CodeRange> ranges = new ArrayList<CodeRange>();
		ranges.add(movedClass.codeRange()
				.setDescription("moved type declaration")
				.setCodeElement(movedClass.getName_RENAMED()));
		return ranges;
	}
}
