package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.refactoringminer.api.Refactoring;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.UMLParameter;

public interface IUMLOperationDiff {

	List<UMLParameterDiff> getParameterDiffList();

	UMLOperation getRemovedOperation();

	UMLOperation getAddedOperation();

	List<UMLParameter> getAddedParameters();

	List<UMLParameter> getRemovedParameters();

	boolean isOperationRenamed();

	boolean isEmpty();

	String toString();

	Set<Refactoring> getRefactorings();

}