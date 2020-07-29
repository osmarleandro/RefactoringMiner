package gr.uom.java.xmi.diff;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.refactoringminer.api.Refactoring;

import gr.uom.java.xmi.UMLAnnotation;
import gr.uom.java.xmi.UMLAttribute;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper;
import gr.uom.java.xmi.decomposition.VariableReferenceExtractor;

public class UMLAttributeDiff {
	public UMLAttribute removedAttribute;
	public UMLAttribute addedAttribute;
	public boolean visibilityChanged;
	public boolean typeChanged;
	public boolean qualifiedTypeChanged;
	public boolean renamed;
	public boolean staticChanged;
	public boolean finalChanged;
	private List<UMLOperationBodyMapper> operationBodyMapperList;
	public UMLAnnotationListDiff annotationListDiff;

	public UMLAttributeDiff(UMLAttribute removedAttribute, UMLAttribute addedAttribute, List<UMLOperationBodyMapper> operationBodyMapperList) {
		this.removedAttribute = removedAttribute;
		this.addedAttribute = addedAttribute;
		this.operationBodyMapperList = operationBodyMapperList;
		this.visibilityChanged = false;
		this.typeChanged = false;
		this.renamed = false;
		this.staticChanged = false;
		this.finalChanged = false;
		if(!removedAttribute.getName().equals(addedAttribute.getName()))
			renamed = true;
		if(!removedAttribute.getVisibility().equals(addedAttribute.getVisibility()))
			visibilityChanged = true;
		if(!removedAttribute.getType().equals(addedAttribute.getType()))
			typeChanged = true;
		else if(!removedAttribute.getType().equalsQualified(addedAttribute.getType()))
			qualifiedTypeChanged = true;
		if(removedAttribute.isStatic() != addedAttribute.isStatic())
			staticChanged = true;
		if(removedAttribute.isFinal() != addedAttribute.isFinal())
			finalChanged = true;
		this.annotationListDiff = new UMLAnnotationListDiff(removedAttribute.getAnnotations(), addedAttribute.getAnnotations());
	}

	public UMLAttribute getRemovedAttribute() {
		return removedAttribute;
	}

	public UMLAttribute getAddedAttribute() {
		return addedAttribute;
	}

	public boolean isRenamed() {
		return renamed;
	}

	public boolean isVisibilityChanged() {
		return visibilityChanged;
	}

	public boolean isTypeChanged() {
		return typeChanged;
	}

	public boolean isQualifiedTypeChanged() {
		return qualifiedTypeChanged;
	}

	public boolean isEmpty() {
		return !visibilityChanged && !typeChanged && !renamed && !qualifiedTypeChanged && annotationListDiff.isEmpty();
	}

	public String toString() {
		return addedAttribute.toString(this);
	}

	private Set<Refactoring> getAnnotationRefactorings() {
		Set<Refactoring> refactorings = new LinkedHashSet<Refactoring>();
		for(UMLAnnotation annotation : annotationListDiff.getAddedAnnotations()) {
			AddAttributeAnnotationRefactoring refactoring = new AddAttributeAnnotationRefactoring(annotation, removedAttribute, addedAttribute);
			refactorings.add(refactoring);
		}
		for(UMLAnnotation annotation : annotationListDiff.getRemovedAnnotations()) {
			RemoveAttributeAnnotationRefactoring refactoring = new RemoveAttributeAnnotationRefactoring(annotation, removedAttribute, addedAttribute);
			refactorings.add(refactoring);
		}
		for(UMLAnnotationDiff annotationDiff : annotationListDiff.getAnnotationDiffList()) {
			ModifyAttributeAnnotationRefactoring refactoring = new ModifyAttributeAnnotationRefactoring(annotationDiff.getRemovedAnnotation(), annotationDiff.getAddedAnnotation(), removedAttribute, addedAttribute);
			refactorings.add(refactoring);
		}
		return refactorings;
	}

	public Set<Refactoring> getRefactorings() {
		Set<Refactoring> refactorings = new LinkedHashSet<Refactoring>();
		if(isTypeChanged() || isQualifiedTypeChanged()) {
			ChangeAttributeTypeRefactoring ref = new ChangeAttributeTypeRefactoring(removedAttribute.getVariableDeclaration(), addedAttribute.getVariableDeclaration(), removedAttribute.getClassName(), addedAttribute.getClassName(),
					VariableReferenceExtractor.findReferences(removedAttribute.getVariableDeclaration(), addedAttribute.getVariableDeclaration(), operationBodyMapperList));
			refactorings.add(ref);
		}
		refactorings.addAll(getAnnotationRefactorings());
		return refactorings;
	}
	
	public Set<Refactoring> getRefactorings(Set<CandidateAttributeRefactoring> set) {
		Set<Refactoring> refactorings = new LinkedHashSet<Refactoring>();
		RenameAttributeRefactoring rename = null;
		if(isRenamed()) {
			rename = new RenameAttributeRefactoring(removedAttribute.getVariableDeclaration(), addedAttribute.getVariableDeclaration(), removedAttribute.getClassName(), addedAttribute.getClassName(), set);
			refactorings.add(rename);
		}
		if(isTypeChanged() || isQualifiedTypeChanged()) {
			ChangeAttributeTypeRefactoring ref = new ChangeAttributeTypeRefactoring(removedAttribute.getVariableDeclaration(), addedAttribute.getVariableDeclaration(), removedAttribute.getClassName(), addedAttribute.getClassName(),
					VariableReferenceExtractor.findReferences(removedAttribute.getVariableDeclaration(), addedAttribute.getVariableDeclaration(), operationBodyMapperList));
			refactorings.add(ref);
			if(rename != null) {
				ref.addRelatedRefactoring(rename);
			}
		}
		refactorings.addAll(getAnnotationRefactorings());
		return refactorings;
	}
}
