package gr.uom.java.xmi.diff;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.decomposition.AbstractCodeMapping;
import gr.uom.java.xmi.decomposition.VariableDeclaration;

public class ChangeAttributeTypeRefactoring implements Refactoring {
	public VariableDeclaration originalAttribute;
	public VariableDeclaration changedTypeAttribute;
	public String classNameBefore;
	public String classNameAfter;
	private Set<AbstractCodeMapping> attributeReferences;
	private Set<Refactoring> relatedRefactorings;
	
	public ChangeAttributeTypeRefactoring(VariableDeclaration originalAttribute,
			VariableDeclaration changedTypeAttribute, String classNameBefore, String classNameAfter, Set<AbstractCodeMapping> attributeReferences) {
		this.originalAttribute = originalAttribute;
		this.changedTypeAttribute = changedTypeAttribute;
		this.classNameBefore = classNameBefore;
		this.classNameAfter = classNameAfter;
		this.attributeReferences = attributeReferences;
		this.relatedRefactorings = new LinkedHashSet<Refactoring>();
	}

	public void addRelatedRefactoring(Refactoring refactoring) {
		this.relatedRefactorings.add(refactoring);
	}

	public Set<Refactoring> getRelatedRefactorings() {
		return relatedRefactorings;
	}

	public VariableDeclaration getOriginalAttribute() {
		return originalAttribute;
	}

	public VariableDeclaration getChangedTypeAttribute() {
		return changedTypeAttribute;
	}

	public String getClassNameBefore() {
		return classNameBefore;
	}

	public String getClassNameAfter() {
		return classNameAfter;
	}

	public Set<AbstractCodeMapping> getAttributeReferences() {
		return attributeReferences;
	}

	@Override
	public RefactoringType getRefactoringType() {
		return RefactoringType.CHANGE_ATTRIBUTE_TYPE;
	}

	@Override
	public String getName() {
		return this.getRefactoringType().getDisplayName();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean qualified = originalAttribute.getType().equals(changedTypeAttribute.getType()) && !originalAttribute.getType().equalsQualified(changedTypeAttribute.getType());
		sb.append(getName()).append("\t");
		sb.append(qualified ? originalAttribute.toQualifiedString() : originalAttribute.toString());
		sb.append(" to ");
		sb.append(qualified ? changedTypeAttribute.toQualifiedString() : changedTypeAttribute.toString());
		sb.append(" in class ").append(classNameAfter);
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((changedTypeAttribute == null) ? 0 : changedTypeAttribute.hashCode());
		result = prime * result + ((classNameAfter == null) ? 0 : classNameAfter.hashCode());
		result = prime * result + ((classNameBefore == null) ? 0 : classNameBefore.hashCode());
		result = prime * result + ((originalAttribute == null) ? 0 : originalAttribute.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return changedTypeAttribute.equals(this, obj);
	}

	@Override
	public Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring() {
		Set<ImmutablePair<String, String>> pairs = new LinkedHashSet<ImmutablePair<String, String>>();
		pairs.add(new ImmutablePair<String, String>(getOriginalAttribute().getLocationInfo().getFilePath(), getClassNameBefore()));
		return pairs;
	}

	@Override
	public Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring() {
		Set<ImmutablePair<String, String>> pairs = new LinkedHashSet<ImmutablePair<String, String>>();
		pairs.add(new ImmutablePair<String, String>(getChangedTypeAttribute().getLocationInfo().getFilePath(), getClassNameAfter()));
		return pairs;
	}

	@Override
	public List<CodeRange> leftSide() {
		List<CodeRange> ranges = new ArrayList<CodeRange>();
		ranges.add(originalAttribute.codeRange()
				.setDescription("original attribute declaration")
				.setCodeElement(originalAttribute.toString()));
		return ranges;
	}

	@Override
	public List<CodeRange> rightSide() {
		List<CodeRange> ranges = new ArrayList<CodeRange>();
		ranges.add(changedTypeAttribute.codeRange()
				.setDescription("changed-type attribute declaration")
				.setCodeElement(changedTypeAttribute.toString()));
		return ranges;
	}
}
