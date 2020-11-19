package gr.uom.java.xmi.diff;

import java.util.Set;

import org.refactoringminer.api.Refactoring;

import gr.uom.java.xmi.UMLAttribute;

public interface IUMLAttributeDiff {

	UMLAttribute getRemovedAttribute();

	UMLAttribute getAddedAttribute();

	boolean isRenamed();

	boolean isVisibilityChanged();

	boolean isTypeChanged();

	boolean isQualifiedTypeChanged();

	boolean isEmpty();

	String toString();

	Set<Refactoring> getRefactorings();

	Set<Refactoring> getRefactorings(Set<CandidateAttributeRefactoring> set);

}