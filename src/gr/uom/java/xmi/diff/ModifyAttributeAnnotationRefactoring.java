package gr.uom.java.xmi.diff;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.UMLAnnotation;
import gr.uom.java.xmi.UMLAttribute;

public class ModifyAttributeAnnotationRefactoring implements Refactoring {
	public UMLAnnotation annotationBefore;
	public UMLAnnotation annotationAfter;
	public UMLAttribute attributeBefore;
	public UMLAttribute attributeAfter;

	public ModifyAttributeAnnotationRefactoring(UMLAnnotation annotationBefore, UMLAnnotation annotationAfter,
			UMLAttribute attributeBefore, UMLAttribute attributeAfter) {
		this.annotationBefore = annotationBefore;
		this.annotationAfter = annotationAfter;
		this.attributeBefore = attributeBefore;
		this.attributeAfter = attributeAfter;
	}

	public UMLAnnotation getAnnotationBefore() {
		return annotationBefore;
	}

	public UMLAnnotation getAnnotationAfter() {
		return annotationAfter;
	}

	public UMLAttribute getAttributeBefore() {
		return attributeBefore;
	}

	public UMLAttribute getAttributeAfter() {
		return attributeAfter;
	}

	@Override
	public List<CodeRange> leftSide() {
		List<CodeRange> ranges = new ArrayList<CodeRange>();
		ranges.add(annotationBefore.codeRange()
				.setDescription("original annotation")
				.setCodeElement(annotationBefore.toString()));
		ranges.add(attributeBefore.codeRange()
				.setDescription("original attribute declaration")
				.setCodeElement(attributeBefore.toString()));
		return ranges;
	}

	@Override
	public List<CodeRange> rightSide() {
		List<CodeRange> ranges = new ArrayList<CodeRange>();
		ranges.add(annotationAfter.codeRange()
				.setDescription("modified annotation")
				.setCodeElement(annotationAfter.toString()));
		ranges.add(attributeAfter.codeRange()
				.setDescription("attribute declaration with modified annotation")
				.setCodeElement(attributeAfter.toString()));
		return ranges;
	}

	@Override
	public RefactoringType getRefactoringType() {
		return RefactoringType.MODIFY_ATTRIBUTE_ANNOTATION;
	}

	@Override
	public String getName() {
		return this.getRefactoringType().getDisplayName();
	}

	@Override
	public Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring() {
		Set<ImmutablePair<String, String>> pairs = new LinkedHashSet<ImmutablePair<String, String>>();
		pairs.add(new ImmutablePair<String, String>(getAttributeBefore().getLocationInfo().getFilePath(), getAttributeBefore().getClassName()));
		return pairs;
	}

	@Override
	public Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring() {
		Set<ImmutablePair<String, String>> pairs = new LinkedHashSet<ImmutablePair<String, String>>();
		pairs.add(new ImmutablePair<String, String>(getAttributeAfter().getLocationInfo().getFilePath(), getAttributeAfter().getClassName()));
		return pairs;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName()).append("\t");
		sb.append(annotationBefore);
		sb.append(" to ");
		sb.append(annotationAfter);
		sb.append(" in attribute ");
		sb.append(attributeAfter);
		sb.append(" from class ");
		sb.append(attributeAfter.getClassName());
		return sb.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((annotationAfter == null) ? 0 : annotationAfter.hashCode());
		result = prime * result + ((annotationBefore == null) ? 0 : annotationBefore.hashCode());
		result = prime * result + ((attributeAfter == null) ? 0 : attributeAfter.hashCode());
		result = prime * result + ((attributeBefore == null) ? 0 : attributeBefore.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return annotationAfter.equals(this, obj);
	}
}
