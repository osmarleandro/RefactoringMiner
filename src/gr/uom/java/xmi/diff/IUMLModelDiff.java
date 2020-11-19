package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringMinerTimedOutException;

import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.UMLClassMatcher;
import gr.uom.java.xmi.UMLGeneralization;
import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.UMLRealization;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper;

public interface IUMLModelDiff {

	void reportAddedClass(UMLClass umlClass);

	void reportRemovedClass(UMLClass umlClass);

	void reportAddedGeneralization(UMLGeneralization umlGeneralization);

	void reportRemovedGeneralization(UMLGeneralization umlGeneralization);

	void reportAddedRealization(UMLRealization umlRealization);

	void reportRemovedRealization(UMLRealization umlRealization);

	void addUMLClassDiff(UMLClassDiff classDiff);

	boolean commonlyImplementedOperations(UMLOperation operation1, UMLOperation operation2,
			UMLClassBaseDiff classDiff2);

	boolean isSubclassOf(String subclass, String finalSuperclass);

	UMLClass getAddedClass(String className);

	UMLClass getRemovedClass(String className);

	void checkForGeneralizationChanges();

	void checkForRealizationChanges();

	void checkForMovedClasses(Map<String, String> renamedFileHints, Set<String> repositoryDirectories,
			UMLClassMatcher matcher) throws RefactoringMinerTimedOutException;

	void checkForRenamedClasses(Map<String, String> renamedFileHints, UMLClassMatcher matcher)
			throws RefactoringMinerTimedOutException;

	List<UMLGeneralization> getAddedGeneralizations();

	List<UMLRealization> getAddedRealizations();

	List<Refactoring> getRefactorings() throws RefactoringMinerTimedOutException;

	List<UMLOperationBodyMapper> findMappersWithMatchingSignature2(UMLOperation operation2);

}