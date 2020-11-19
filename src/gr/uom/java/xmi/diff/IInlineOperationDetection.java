package gr.uom.java.xmi.diff;

import java.util.List;

import org.refactoringminer.api.RefactoringMinerTimedOutException;

import gr.uom.java.xmi.UMLOperation;

public interface IInlineOperationDetection {

	List<InlineOperationRefactoring> check(UMLOperation removedOperation) throws RefactoringMinerTimedOutException;

}