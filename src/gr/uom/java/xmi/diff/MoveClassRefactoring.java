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
		sb.append(originalClass.getJavadoc().getName(this));
		sb.append(" moved to ");
		sb.append(movedClass.getJavadoc().getName(this));
		return sb.toString();
	}

	public RenamePattern getRenamePattern() {
		int separatorPos = PrefixSuffixUtils.separatorPosOfCommonSuffix('.', originalClass.getJavadoc().getName(this), movedClass.getJavadoc().getName(this));
		if (separatorPos == -1) {
			return new RenamePattern(originalClass.getJavadoc().getName(this), movedClass.getJavadoc().getName(this));
		}
		String originalPath = originalClass.getJavadoc().getName(this).substring(0, originalClass.getJavadoc().getName(this).length() - separatorPos);
		String movedPath = movedClass.getJavadoc().getName(this).substring(0, movedClass.getJavadoc().getName(this).length() - separatorPos);
		return new RenamePattern(originalPath, movedPath);
	}

	public String getName() {
		return this.getRefactoringType().getDisplayName();
	}

	public RefactoringType getRefactoringType() {
		return RefactoringType.MOVE_CLASS;
	}

	public String getOriginalClassName() {
		return originalClass.getJavadoc().getName(this);
	}

	public String getMovedClassName() {
		return movedClass.getJavadoc().getName(this);
	}

	public UMLClass getOriginalClass() {
		return originalClass;
	}

	public UMLClass getMovedClass() {
		return movedClass;
	}

	public Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring() {
		Set<ImmutablePair<String, String>> pairs = new LinkedHashSet<ImmutablePair<String, String>>();
		pairs.add(new ImmutablePair<String, String>(getOriginalClass().getLocationInfo().getFilePath(), getOriginalClass().getJavadoc().getName(this)));
		return pairs;
	}

	public Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring() {
		Set<ImmutablePair<String, String>> pairs = new LinkedHashSet<ImmutablePair<String, String>>();
		pairs.add(new ImmutablePair<String, String>(getMovedClass().getLocationInfo().getFilePath(), getMovedClass().getJavadoc().getName(this)));
		return pairs;
	}

	@Override
	public List<CodeRange> leftSide() {
		List<CodeRange> ranges = new ArrayList<CodeRange>();
		ranges.add(originalClass.codeRange()
				.setDescription("original type declaration")
				.setCodeElement(originalClass.getJavadoc().getName(this)));
		return ranges;
	}

	@Override
	public List<CodeRange> rightSide() {
		List<CodeRange> ranges = new ArrayList<CodeRange>();
		ranges.add(movedClass.codeRange()
				.setDescription("moved type declaration")
				.setCodeElement(movedClass.getJavadoc().getName(this)));
		return ranges;
	}
}
