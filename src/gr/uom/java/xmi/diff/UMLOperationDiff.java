package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLAnnotation;
import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.UMLParameter_RENAMED;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;
import gr.uom.java.xmi.decomposition.VariableDeclaration;
import gr.uom.java.xmi.decomposition.VariableReferenceExtractor;
import gr.uom.java.xmi.decomposition.replacement.Replacement;
import gr.uom.java.xmi.decomposition.replacement.Replacement.ReplacementType;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.refactoringminer.api.Refactoring;

public class UMLOperationDiff {
	private UMLOperation removedOperation;
	private UMLOperation addedOperation;
	private List<UMLParameter_RENAMED> addedParameters;
	private List<UMLParameter_RENAMED> removedParameters;
	private List<UMLParameterDiff> parameterDiffList;
	private boolean visibilityChanged;
	private boolean abstractionChanged;
	private boolean returnTypeChanged;
	private boolean qualifiedReturnTypeChanged;
	private boolean operationRenamed;
	private Set<AbstractCodeMapping> mappings = new LinkedHashSet<AbstractCodeMapping>();
	private UMLAnnotationListDiff annotationListDiff;
	
	public UMLOperationDiff(UMLOperation removedOperation, UMLOperation addedOperation) {
		this.removedOperation = removedOperation;
		this.addedOperation = addedOperation;
		this.addedParameters = new ArrayList<UMLParameter_RENAMED>();
		this.removedParameters = new ArrayList<UMLParameter_RENAMED>();
		this.parameterDiffList = new ArrayList<UMLParameterDiff>();
		this.visibilityChanged = false;
		this.abstractionChanged = false;
		this.returnTypeChanged = false;
		this.operationRenamed = false;
		if(!removedOperation.getName().equals(addedOperation.getName()))
			operationRenamed = true;
		if(!removedOperation.getVisibility().equals(addedOperation.getVisibility()))
			visibilityChanged = true;
		if(removedOperation.isAbstract() != addedOperation.isAbstract())
			abstractionChanged = true;
		if(!removedOperation.equalReturnParameter(addedOperation))
			returnTypeChanged = true;
		else if(!removedOperation.equalQualifiedReturnParameter(addedOperation))
			qualifiedReturnTypeChanged = true;
		this.annotationListDiff = new UMLAnnotationListDiff(removedOperation.getAnnotations(), addedOperation.getAnnotations());
		List<SimpleEntry<UMLParameter_RENAMED, UMLParameter_RENAMED>> matchedParameters = updateAddedRemovedParameters(removedOperation, addedOperation);
		for(SimpleEntry<UMLParameter_RENAMED, UMLParameter_RENAMED> matchedParameter : matchedParameters) {
			UMLParameter_RENAMED parameter1 = matchedParameter.getKey();
			UMLParameter_RENAMED parameter2 = matchedParameter.getValue();
			if(!parameter1.equalsQualified(parameter2)) {
				UMLParameterDiff parameterDiff = new UMLParameterDiff(parameter1, parameter2);
				parameterDiffList.add(parameterDiff);
			}
		}
		int matchedParameterCount = matchedParameters.size()/2;
		//first round match parameters with the same name
		for(Iterator<UMLParameter_RENAMED> removedParameterIterator = removedParameters.iterator(); removedParameterIterator.hasNext();) {
			UMLParameter_RENAMED removedParameter = removedParameterIterator.next();
			for(Iterator<UMLParameter_RENAMED> addedParameterIterator = addedParameters.iterator(); addedParameterIterator.hasNext();) {
				UMLParameter_RENAMED addedParameter = addedParameterIterator.next();
				if(removedParameter.getName().equals(addedParameter.getName())) {
					UMLParameterDiff parameterDiff = new UMLParameterDiff(removedParameter, addedParameter);
					parameterDiffList.add(parameterDiff);
					addedParameterIterator.remove();
					removedParameterIterator.remove();
					break;
				}
			}
		}
		//second round match parameters with the same type
		for(Iterator<UMLParameter_RENAMED> removedParameterIterator = removedParameters.iterator(); removedParameterIterator.hasNext();) {
			UMLParameter_RENAMED removedParameter = removedParameterIterator.next();
			for(Iterator<UMLParameter_RENAMED> addedParameterIterator = addedParameters.iterator(); addedParameterIterator.hasNext();) {
				UMLParameter_RENAMED addedParameter = addedParameterIterator.next();
				if(removedParameter.getType().equalsQualified(addedParameter.getType()) &&
						!existsAnotherAddedParameterWithTheSameType(addedParameter)) {
					UMLParameterDiff parameterDiff = new UMLParameterDiff(removedParameter, addedParameter);
					parameterDiffList.add(parameterDiff);
					addedParameterIterator.remove();
					removedParameterIterator.remove();
					break;
				}
			}
		}
		//third round match parameters with different type and name
		List<UMLParameter_RENAMED> removedParametersWithoutReturnType = removedOperation.getParametersWithoutReturnType();
		List<UMLParameter_RENAMED> addedParametersWithoutReturnType = addedOperation.getParametersWithoutReturnType();
		if(matchedParameterCount == removedParametersWithoutReturnType.size()-1 && matchedParameterCount == addedParametersWithoutReturnType.size()-1) {
			for(Iterator<UMLParameter_RENAMED> removedParameterIterator = removedParameters.iterator(); removedParameterIterator.hasNext();) {
				UMLParameter_RENAMED removedParameter = removedParameterIterator.next();
				int indexOfRemovedParameter = removedParametersWithoutReturnType.indexOf(removedParameter);
				for(Iterator<UMLParameter_RENAMED> addedParameterIterator = addedParameters.iterator(); addedParameterIterator.hasNext();) {
					UMLParameter_RENAMED addedParameter = addedParameterIterator.next();
					int indexOfAddedParameter = addedParametersWithoutReturnType.indexOf(addedParameter);
					if(indexOfRemovedParameter == indexOfAddedParameter) {
						UMLParameterDiff parameterDiff = new UMLParameterDiff(removedParameter, addedParameter);
						parameterDiffList.add(parameterDiff);
						addedParameterIterator.remove();
						removedParameterIterator.remove();
						break;
					}
				}
			}
		}
	}
	public UMLOperationDiff(UMLOperation removedOperation, UMLOperation addedOperation, Set<AbstractCodeMapping> mappings) {
		this(removedOperation, addedOperation);
		this.mappings = mappings;
	}

	private boolean existsAnotherAddedParameterWithTheSameType(UMLParameter_RENAMED parameter) {
		if(removedOperation.hasTwoParametersWithTheSameType() && addedOperation.hasTwoParametersWithTheSameType()) {
			return false;
		}
		for(UMLParameter_RENAMED addedParameter : addedParameters) {
			if(!addedParameter.getName().equals(parameter.getName()) &&
					addedParameter.getType().equalsQualified(parameter.getType())) {
				return true;
			}
		}
		return false;
	}

	private List<SimpleEntry<UMLParameter_RENAMED, UMLParameter_RENAMED>> updateAddedRemovedParameters(UMLOperation removedOperation, UMLOperation addedOperation) {
		List<SimpleEntry<UMLParameter_RENAMED, UMLParameter_RENAMED>> matchedParameters = new ArrayList<SimpleEntry<UMLParameter_RENAMED, UMLParameter_RENAMED>>();
		for(UMLParameter_RENAMED parameter1 : removedOperation.getParameters()) {
			if(!parameter1.getKind().equals("return")) {
				boolean found = false;
				for(UMLParameter_RENAMED parameter2 : addedOperation.getParameters()) {
					if(parameter1.equalsIncludingName(parameter2)) {
						matchedParameters.add(new SimpleEntry<UMLParameter_RENAMED, UMLParameter_RENAMED>(parameter1, parameter2));
						found = true;
						break;
					}
				}
				if(!found) {
					this.removedParameters.add(parameter1);
				}
			}
		}
		for(UMLParameter_RENAMED parameter1 : addedOperation.getParameters()) {
			if(!parameter1.getKind().equals("return")) {
				boolean found = false;
				for(UMLParameter_RENAMED parameter2 : removedOperation.getParameters()) {
					if(parameter1.equalsIncludingName(parameter2)) {
						matchedParameters.add(new SimpleEntry<UMLParameter_RENAMED, UMLParameter_RENAMED>(parameter2, parameter1));
						found = true;
						break;
					}
				}
				if(!found) {
					this.addedParameters.add(parameter1);
				}
			}
		}
		return matchedParameters;
	}

	public List<UMLParameterDiff> getParameterDiffList() {
		return parameterDiffList;
	}

	public UMLOperation getRemovedOperation() {
		return removedOperation;
	}

	public UMLOperation getAddedOperation() {
		return addedOperation;
	}

	public List<UMLParameter_RENAMED> getAddedParameters() {
		return addedParameters;
	}

	public List<UMLParameter_RENAMED> getRemovedParameters() {
		return removedParameters;
	}

	public boolean isOperationRenamed() {
		return operationRenamed;
	}

	public boolean isEmpty() {
		return addedParameters.isEmpty() && removedParameters.isEmpty() && parameterDiffList.isEmpty() &&
		!visibilityChanged && !abstractionChanged && !returnTypeChanged && !operationRenamed && annotationListDiff.isEmpty();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(!isEmpty())
			sb.append("\t").append(removedOperation).append("\n");
		if(operationRenamed)
			sb.append("\t").append("renamed from " + removedOperation.getName() + " to " + addedOperation.getName()).append("\n");
		if(visibilityChanged)
			sb.append("\t").append("visibility changed from " + removedOperation.getVisibility() + " to " + addedOperation.getVisibility()).append("\n");
		if(abstractionChanged)
			sb.append("\t").append("abstraction changed from " + (removedOperation.isAbstract() ? "abstract" : "concrete") + " to " +
					(addedOperation.isAbstract() ? "abstract" : "concrete")).append("\n");
		if(returnTypeChanged || qualifiedReturnTypeChanged)
			sb.append("\t").append("return type changed from " + removedOperation.getReturnParameter() + " to " + addedOperation.getReturnParameter()).append("\n");
		for(UMLParameter_RENAMED umlParameter : removedParameters) {
			sb.append("\t").append("parameter " + umlParameter + " removed").append("\n");
		}
		for(UMLParameter_RENAMED umlParameter : addedParameters) {
			sb.append("\t").append("parameter " + umlParameter + " added").append("\n");
		}
		for(UMLParameterDiff parameterDiff : parameterDiffList) {
			sb.append(parameterDiff);
		}
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

	public Set<Refactoring> getRefactorings() {
		Set<Refactoring> refactorings = new LinkedHashSet<Refactoring>();
		if(returnTypeChanged || qualifiedReturnTypeChanged) {
			UMLParameter_RENAMED removedOperationReturnParameter = removedOperation.getReturnParameter();
			UMLParameter_RENAMED addedOperationReturnParameter = addedOperation.getReturnParameter();
			if(removedOperationReturnParameter != null && addedOperationReturnParameter != null) {
				Set<AbstractCodeMapping> references = VariableReferenceExtractor.findReturnReferences(mappings);
				ChangeReturnTypeRefactoring refactoring = new ChangeReturnTypeRefactoring(removedOperationReturnParameter.getType(), addedOperationReturnParameter.getType(),
						removedOperation, addedOperation, references);
				refactorings.add(refactoring);
			}
		}
		for(UMLParameterDiff parameterDiff : getParameterDiffList()) {
			VariableDeclaration originalVariable = parameterDiff.getRemovedParameter().getVariableDeclaration();
			VariableDeclaration newVariable = parameterDiff.getAddedParameter().getVariableDeclaration();
			Set<AbstractCodeMapping> references = VariableReferenceExtractor.findReferences(originalVariable, newVariable, mappings);
			RenameVariableRefactoring renameRefactoring = null;
			if(parameterDiff.isNameChanged() && !inconsistentReplacement(originalVariable, newVariable)) {
				renameRefactoring = new RenameVariableRefactoring(originalVariable, newVariable, removedOperation, addedOperation, references);
				refactorings.add(renameRefactoring);
			}
			if((parameterDiff.isTypeChanged() || parameterDiff.isQualifiedTypeChanged()) && !inconsistentReplacement(originalVariable, newVariable)) {
				ChangeVariableTypeRefactoring refactoring = new ChangeVariableTypeRefactoring(originalVariable, newVariable, removedOperation, addedOperation, references);
				if(renameRefactoring != null) {
					refactoring.addRelatedRefactoring(renameRefactoring);
				}
				refactorings.add(refactoring);
			}
		}
		for(UMLAnnotation annotation : annotationListDiff.getAddedAnnotations()) {
			AddMethodAnnotationRefactoring refactoring = new AddMethodAnnotationRefactoring(annotation, removedOperation, addedOperation);
			refactorings.add(refactoring);
		}
		for(UMLAnnotation annotation : annotationListDiff.getRemovedAnnotations()) {
			RemoveMethodAnnotationRefactoring refactoring = new RemoveMethodAnnotationRefactoring(annotation, removedOperation, addedOperation);
			refactorings.add(refactoring);
		}
		for(UMLAnnotationDiff annotationDiff : annotationListDiff.getAnnotationDiffList()) {
			ModifyMethodAnnotationRefactoring refactoring = new ModifyMethodAnnotationRefactoring(annotationDiff.getRemovedAnnotation(), annotationDiff.getAddedAnnotation(), removedOperation, addedOperation);
			refactorings.add(refactoring);
		}
		return refactorings;
	}
	
	private boolean inconsistentReplacement(VariableDeclaration originalVariable, VariableDeclaration newVariable) {
		if(removedOperation.isStatic() || addedOperation.isStatic()) {
			for(AbstractCodeMapping mapping : mappings) {
				for(Replacement replacement : mapping.getReplacements()) {
					if(replacement.getType().equals(ReplacementType.VARIABLE_NAME)) {
						if(replacement.getBefore().equals(originalVariable.getVariableName()) && !replacement.getAfter().equals(newVariable.getVariableName())) {
							return true;
						}
						else if(!replacement.getBefore().equals(originalVariable.getVariableName()) && replacement.getAfter().equals(newVariable.getVariableName())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
