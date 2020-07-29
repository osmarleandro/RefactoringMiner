package gr.uom.java.xmi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.refactoringminer.api.RefactoringMinerTimedOutException;

import gr.uom.java.xmi.diff.ClassMoveComparator;
import gr.uom.java.xmi.diff.UMLClassMoveDiff;
import gr.uom.java.xmi.diff.UMLModelDiff;

public interface UMLClassMatcher {
	public boolean match(UMLClass removedClass, UMLClass addedClass, String renamedFile);

	default void checkForMovedClasses(Map<String, String> renamedFileHints, Set<String> repositoryDirectories, UMLModelDiff umlModelDiff) throws RefactoringMinerTimedOutException {
		   for(Iterator<UMLClass> removedClassIterator = umlModelDiff.removedClasses.iterator(); removedClassIterator.hasNext();) {
			   UMLClass removedClass = removedClassIterator.next();
			   TreeSet<UMLClassMoveDiff> diffSet = new TreeSet<UMLClassMoveDiff>(new ClassMoveComparator());
			   for(Iterator<UMLClass> addedClassIterator = umlModelDiff.addedClasses.iterator(); addedClassIterator.hasNext();) {
				   UMLClass addedClass = addedClassIterator.next();
				   String removedClassSourceFile = removedClass.getSourceFile();
				   String renamedFile =  renamedFileHints.get(removedClassSourceFile);
				   String removedClassSourceFolder = "";
				   if(removedClassSourceFile.contains("/")) {
					   removedClassSourceFolder = removedClassSourceFile.substring(0, removedClassSourceFile.lastIndexOf("/"));
				   }
				   if(!repositoryDirectories.contains(removedClassSourceFolder)) {
					   umlModelDiff.deletedFolderPaths.add(removedClassSourceFolder);
					   //add deleted sub-directories
					   String subDirectory = new String(removedClassSourceFolder);
					   while(subDirectory.contains("/")) {
						   subDirectory = subDirectory.substring(0, subDirectory.lastIndexOf("/"));
						   if(!repositoryDirectories.contains(subDirectory)) {
							   umlModelDiff.deletedFolderPaths.add(subDirectory);
						   }
					   }
				   }
				   if(match(removedClass, addedClass, renamedFile)) {
					   if(!umlModelDiff.conflictingMoveOfTopLevelClass(removedClass, addedClass)) {
						   UMLClassMoveDiff classMoveDiff = new UMLClassMoveDiff(removedClass, addedClass, umlModelDiff);
						   diffSet.add(classMoveDiff);
					   }
				   }
			   }
			   if(!diffSet.isEmpty()) {
				   UMLClassMoveDiff minClassMoveDiff = diffSet.first();
				   minClassMoveDiff.process();
				   umlModelDiff.classMoveDiffList.add(minClassMoveDiff);
				   umlModelDiff.addedClasses.remove(minClassMoveDiff.getMovedClass());
				   removedClassIterator.remove();
			   }
		   }
	
		   List<UMLClassMoveDiff> allClassMoves = new ArrayList<UMLClassMoveDiff>(umlModelDiff.classMoveDiffList);
		   Collections.sort(allClassMoves);
	
		   for(int i=0; i<allClassMoves.size(); i++) {
			   UMLClassMoveDiff classMoveI = allClassMoves.get(i);
			   for(int j=i+1; j<allClassMoves.size(); j++) {
				   UMLClassMoveDiff classMoveJ = allClassMoves.get(j);
				   if(classMoveI.isInnerClassMove(classMoveJ)) {
					   umlModelDiff.innerClassMoveDiffList.add(classMoveJ);
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
