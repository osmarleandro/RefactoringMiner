package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLClass_RENAMED;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringType;
import org.refactoringminer.util.PrefixSuffixUtils;

public class MoveClassRefactoring implements Refactoring {
	private UMLClass_RENAMED originalClass;
	private UMLClass_RENAMED movedClass;
	
	public MoveClassRefactoring(UMLClass_RENAMED originalClass,  UMLClass_RENAMED movedClass) {
		this.originalClass = originalClass;
		this.movedClass = movedClass;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName()).append("\t");
		sb.append(originalClass.getName());
		sb.append(" moved to ");
		sb.append(movedClass.getName());
		return sb.toString();
	}

	public RenamePattern getRenamePattern() {
		int separatorPos = PrefixSuffixUtils.separatorPosOfCommonSuffix('.', originalClass.getName(), movedClass.getName());
		if (separatorPos == -1) {
			return new RenamePattern(originalClass.getName(), movedClass.getName());
		}
		String originalPath = originalClass.getName().substring(0, originalClass.getName().length() - separatorPos);
		String movedPath = movedClass.getName().substring(0, movedClass.getName().length() - separatorPos);
		return new RenamePattern(originalPath, movedPath);
	}

	public String getName() {
		return this.getRefactoringType().getDisplayName();
	}

	public RefactoringType getRefactoringType() {
		return RefactoringType.MOVE_CLASS;
	}

	public String getOriginalClassName() {
		return originalClass.getName();
	}

	public String getMovedClassName() {
		return movedClass.getName();
	}

	public UMLClass_RENAMED getOriginalClass() {
		return originalClass;
	}

	public UMLClass_RENAMED getMovedClass() {
		return movedClass;
	}

	public Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring() {
		Set<ImmutablePair<String, String>> pairs = new LinkedHashSet<ImmutablePair<String, String>>();
		pairs.add(new ImmutablePair<String, String>(getOriginalClass().getLocationInfo().getFilePath(), getOriginalClass().getName()));
		return pairs;
	}

	public Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring() {
		Set<ImmutablePair<String, String>> pairs = new LinkedHashSet<ImmutablePair<String, String>>();
		pairs.add(new ImmutablePair<String, String>(getMovedClass().getLocationInfo().getFilePath(), getMovedClass().getName()));
		return pairs;
	}

	@Override
	public List<CodeRange> leftSide() {
		List<CodeRange> ranges = new ArrayList<CodeRange>();
		ranges.add(originalClass.codeRange()
				.setDescription("original type declaration")
				.setCodeElement(originalClass.getName()));
		return ranges;
	}

	@Override
	public List<CodeRange> rightSide() {
		List<CodeRange> ranges = new ArrayList<CodeRange>();
		ranges.add(movedClass.codeRange()
				.setDescription("moved type declaration")
				.setCodeElement(movedClass.getName()));
		return ranges;
	}
}
