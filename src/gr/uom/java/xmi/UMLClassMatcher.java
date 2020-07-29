package gr.uom.java.xmi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.refactoringminer.api.RefactoringMinerTimedOutException;

import gr.uom.java.xmi.diff.ClassRenameComparator;
import gr.uom.java.xmi.diff.UMLClassMoveDiff;
import gr.uom.java.xmi.diff.UMLClassRenameDiff;
import gr.uom.java.xmi.diff.UMLModelDiff;

public interface UMLClassMatcher {
	public boolean match(UMLClass removedClass, UMLClass addedClass, String renamedFile);

	default void checkForRenamedClasses(Map<String, String> renamedFileHints, UMLModelDiff umlModelDiff) throws RefactoringMinerTimedOutException {
	      for(Iterator<UMLClass> removedClassIterator = umlModelDiff.removedClasses.iterator(); removedClassIterator.hasNext();) {
	         UMLClass removedClass = removedClassIterator.next();
	         TreeSet<UMLClassRenameDiff> diffSet = new TreeSet<UMLClassRenameDiff>(new ClassRenameComparator());
	         for(Iterator<UMLClass> addedClassIterator = umlModelDiff.addedClasses.iterator(); addedClassIterator.hasNext();) {
	            UMLClass addedClass = addedClassIterator.next();
	            String renamedFile =  renamedFileHints.get(removedClass.getSourceFile());
	            if(match(removedClass, addedClass, renamedFile)) {
	               if(!umlModelDiff.conflictingMoveOfTopLevelClass(removedClass, addedClass) && !umlModelDiff.innerClassWithTheSameName(removedClass, addedClass)) {
	            	   UMLClassRenameDiff classRenameDiff = new UMLClassRenameDiff(removedClass, addedClass, umlModelDiff);
	            	   diffSet.add(classRenameDiff);
	               }
	            }
	         }
	         if(!diffSet.isEmpty()) {
	            UMLClassRenameDiff minClassRenameDiff = diffSet.first();
	            minClassRenameDiff.process();
	            umlModelDiff.classRenameDiffList.add(minClassRenameDiff);
	            umlModelDiff.addedClasses.remove(minClassRenameDiff.getRenamedClass());
	            removedClassIterator.remove();
	         }
	      }
	      
	      List<UMLClassMoveDiff> allClassMoves = new ArrayList<UMLClassMoveDiff>(umlModelDiff.classMoveDiffList);
	      Collections.sort(allClassMoves);
	      
	      for(UMLClassRenameDiff classRename : umlModelDiff.classRenameDiffList) {
	         for(UMLClassMoveDiff classMove : allClassMoves) {
	            if(classRename.isInnerClassMove(classMove)) {
	               umlModelDiff.innerClassMoveDiffList.add(classMove);
	            }
	         }
	      }
	      umlModelDiff.classMoveDiffList.removeAll(umlModelDiff.innerClassMoveDiffList);
	   }

	public static class Move implements UMLClassMatcher {
		public boolean match(UMLClass removedClass, UMLClass addedClass, String renamedFile) {
			return removedClass.hasSameNameAndKind(addedClass) 
					&& (removedClass.hasSameAttributesAndOperations(addedClass) || addedClass.getSourceFile().equals(renamedFile));
		}
	}

	public static class RelaxedMove implements UMLClassMatcher {
		public boolean match(UMLClass removedClass, UMLClass addedClass, String renamedFile) {
			return removedClass.hasSameNameAndKind(addedClass) 
					&& (removedClass.hasCommonAttributesAndOperations(addedClass) || addedClass.getSourceFile().equals(renamedFile));
		}
	}

	public static class ExtremelyRelaxedMove implements UMLClassMatcher {
		public boolean match(UMLClass removedClass, UMLClass addedClass, String renamedFile) {
			return removedClass.hasSameNameAndKind(addedClass) 
					&& (removedClass.hasAttributesAndOperationsWithCommonNames(addedClass) || addedClass.getSourceFile().equals(renamedFile));
		}
	}

	public static class Rename implements UMLClassMatcher {
		public boolean match(UMLClass removedClass, UMLClass addedClass, String renamedFile) {
			return removedClass.hasSameKind(addedClass) 
					&& (removedClass.hasSameAttributesAndOperations(addedClass) || addedClass.getSourceFile().equals(renamedFile));
		}
	}

	public static class RelaxedRename implements UMLClassMatcher {
		public boolean match(UMLClass removedClass, UMLClass addedClass, String renamedFile) {
			return removedClass.hasSameKind(addedClass) 
					&& (removedClass.hasCommonAttributesAndOperations(addedClass) || addedClass.getSourceFile().equals(renamedFile));
		}
	}

	public static class ExtremelyRelaxedRename implements UMLClassMatcher {
		public boolean match(UMLClass removedClass, UMLClass addedClass, String renamedFile) {
			return removedClass.hasSameKind(addedClass) 
					&& (removedClass.hasAttributesAndOperationsWithCommonNames(addedClass) || addedClass.getSourceFile().equals(renamedFile));
		}
	}
}
