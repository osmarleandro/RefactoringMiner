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
	private UMLAttribute removedAttribute;
	private UMLAttribute addedAttribute;
	private boolean visibilityChanged;
	private boolean typeChanged;
	private boolean qualifiedTypeChanged;
	private boolean renamed;
	private boolean staticChanged;
	private boolean finalChanged;
	private List<UMLOperationBodyMapper> operationBodyMapperList;
	private UMLAnnotationListDiff annotationListDiff;

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
		return !visibilityChanged && !typeChanged && !renamed && !qualifiedTypeChanged && annotationListDiff.isEmpty_RENAMED();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(!isEmpty())
			sb.append("\t").append(removedAttribute).append("\n");
		if(renamed)
			sb.append("\t").append("renamed from " + removedAttribute.getName() + " to " + addedAttribute.getName()).append("\n");
		if(visibilityChanged)
			sb.append("\t").append("visibility changed from " + removedAttribute.getVisibility() + " to " + addedAttribute.getVisibility()).append("\n");
		if(typeChanged || qualifiedTypeChanged)
			sb.append("\t").append("type changed from " + removedAttribute.getType() + " to " + addedAttribute.getType()).append("\n");
		if(staticChanged)
			sb.append("\t").append("modifier changed from " + (removedAttribute.isStatic() ? "static" : "non-static") + " to " +
					(addedAttribute.isStatic() ? "static" : "non-static")).append("\n");
		if(finalChanged)
			sb.append("\t").append("modifier changed from " + (removedAttribute.isFinal() ? "final" : "non-final") + " to " +
					(addedAttribute.isFinal() ? "final" : "non-final")).append("\n");
		for(UMLAnnotation annotation : annotationListDiff.getRemovedAnnotations()) {
			sb.append("\t").append("annotation " + annotation + " removed").append("\n");
		}
		for(UMLAnnotation annotation : annotationListDiff.getAddedAnnotations()) {
			sb.append("\t").append("annotation " + annotation + " added").append("\n");
		}
		for(UMLAnnotationDiff annotationDiff : annotationListDiff.getAnnotationDiffList()) {
			sb.append("\t").append("annotation " + annotationDiff.getRemovedAnnotation() + " modified to " + annotationDiff.getAddedAnnotation()).append("\n");
		}
		return sb.toString();
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
