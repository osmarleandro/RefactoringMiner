package gr.uom.java.xmi;

import java.util.List;
import java.util.Map;

import org.refactoringminer.api.RefactoringMinerTimedOutException;

import gr.uom.java.xmi.diff.UMLModelDiff;

public interface IUMLModel {

	void addClass(UMLClass umlClass);

	void addGeneralization(UMLGeneralization umlGeneralization);

	void addRealization(UMLRealization umlRealization);

	UMLClass getClass(UMLClass umlClassFromOtherModel);

	List<UMLClass> getClassList();

	List<UMLGeneralization> getGeneralizationList();

	List<UMLRealization> getRealizationList();

	UMLGeneralization matchGeneralization(UMLGeneralization otherGeneralization);

	UMLRealization matchRealization(UMLRealization otherRealization);

	UMLModelDiff diff(UMLModel umlModel) throws RefactoringMinerTimedOutException;

	UMLModelDiff diff(UMLModel umlModel, Map<String, String> renamedFileHints) throws RefactoringMinerTimedOutException;

}