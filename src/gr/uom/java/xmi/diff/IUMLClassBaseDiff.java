package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Set;

import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringMinerTimedOutException;

import gr.uom.java.xmi.UMLAnonymousClass;
import gr.uom.java.xmi.UMLAttribute;
import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.UMLType;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper;

public interface IUMLClassBaseDiff {

	void process() throws RefactoringMinerTimedOutException;

	UMLOperationDiff getOperationDiff(UMLOperation operation1, UMLOperation operation2);

	UMLOperationBodyMapper findMapperWithMatchingSignatures(UMLOperation operation1, UMLOperation operation2);

	UMLOperationBodyMapper findMapperWithMatchingSignature2(UMLOperation operation2);

	Set<UMLType> nextClassCommonInterfaces(UMLClassBaseDiff other);

	boolean matches(String className);

	boolean matches(UMLType type);

	String getOriginalClassName();

	String getNextClassName();

	UMLClass getOriginalClass();

	UMLClass getNextClass();

	List<UMLOperationBodyMapper> getOperationBodyMapperList();

	List<UMLOperation> getAddedOperations();

	List<UMLOperation> getRemovedOperations();

	List<UMLAttribute> getAddedAttributes();

	List<UMLAttribute> getRemovedAttributes();

	//return true if "classMoveDiff" represents the move of a class that is inner to this.originalClass
	boolean isInnerClassMove(UMLClassBaseDiff classDiff);

	boolean nextClassImportsType(String targetClass);

	boolean originalClassImportsType(String targetClass);

	List<UMLAttribute> nextClassAttributesOfType(String targetClass);

	List<UMLAttribute> originalClassAttributesOfType(String targetClass);

	void reportAddedAnonymousClass(UMLAnonymousClass umlClass);

	void reportRemovedAnonymousClass(UMLAnonymousClass umlClass);

	UMLType getSuperclass();

	UMLType getOldSuperclass();

	UMLType getNewSuperclass();

	List<UMLType> getAddedImplementedInterfaces();

	List<UMLType> getRemovedImplementedInterfaces();

	List<UMLAnonymousClass> getAddedAnonymousClasses();

	List<UMLAnonymousClass> getRemovedAnonymousClasses();

	Set<CandidateAttributeRefactoring> getCandidateAttributeRenames();

	Set<CandidateMergeVariableRefactoring> getCandidateAttributeMerges();

	Set<CandidateSplitVariableRefactoring> getCandidateAttributeSplits();

	boolean containsOperationWithTheSameSignatureInOriginalClass(UMLOperation operation);

	boolean containsOperationWithTheSameSignatureInNextClass(UMLOperation operation);

	UMLOperation containsRemovedOperationWithTheSameSignature(UMLOperation operation);

	UMLAttribute containsRemovedAttributeWithTheSameSignature(UMLAttribute attribute);

	void addOperationBodyMapper(UMLOperationBodyMapper operationBodyMapper);

	List<Refactoring> getRefactoringsBeforePostProcessing();

	List<Refactoring> getRefactorings();

	UMLAttribute findAttributeInOriginalClass(String attributeName);

	UMLAttribute findAttributeInNextClass(String attributeName);

	boolean isEmpty();

	String toString();

	int compareTo(IUMLClassBaseDiff other);

	boolean containsExtractOperationRefactoring(UMLOperation sourceOperationBeforeExtraction,
			UMLOperation extractedOperation);

	UMLModelDiff getModelDiff();

}