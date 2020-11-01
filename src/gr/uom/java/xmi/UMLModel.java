package gr.uom.java.xmi;

import gr.uom.java.xmi.diff.ClassMoveComparator;
import gr.uom.java.xmi.diff.UMLClassDiff;
import gr.uom.java.xmi.diff.UMLClassMoveDiff;
import gr.uom.java.xmi.diff.UMLModelDiff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.refactoringminer.api.RefactoringMinerTimedOutException;

public class UMLModel {
	private Set<String> repositoryDirectories;
    private List<UMLClass> classList;
    private List<UMLGeneralization> generalizationList;
    private List<UMLRealization> realizationList;

    public UMLModel(Set<String> repositoryDirectories) {
    	this.repositoryDirectories = repositoryDirectories;
        classList = new ArrayList<UMLClass>();
        generalizationList = new ArrayList<UMLGeneralization>();
        realizationList = new ArrayList<UMLRealization>();
    }

	public void addClass(UMLClass umlClass) {
        classList.add(umlClass);
    }

    public void addGeneralization(UMLGeneralization umlGeneralization) {
        generalizationList.add(umlGeneralization);
    }

    public void addRealization(UMLRealization umlRealization) {
    	realizationList.add(umlRealization);
    }

    public UMLClass getClass(UMLClass umlClassFromOtherModel) {
    	ListIterator<UMLClass> it = classList.listIterator();
        while(it.hasNext()) {
            UMLClass umlClass = it.next();
            if(umlClass.equals(umlClassFromOtherModel))
                return umlClass;
        }
        return null;
    }

    public List<UMLClass> getClassList() {
        return this.classList;
    }

    public List<UMLGeneralization> getGeneralizationList() {
        return this.generalizationList;
    }

    public List<UMLRealization> getRealizationList() {
		return realizationList;
	}

	public UMLGeneralization matchGeneralization(UMLGeneralization otherGeneralization) {
    	ListIterator<UMLGeneralization> generalizationIt = generalizationList.listIterator();
    	while(generalizationIt.hasNext()) {
    		UMLGeneralization generalization = generalizationIt.next();
    		if(generalization.getChild().equals(otherGeneralization.getChild())) {
    			String thisParent = generalization.getParent();
    			String otherParent = otherGeneralization.getParent();
    			String thisParentComparedString = null;
    			if(thisParent.contains("."))
    				thisParentComparedString = thisParent.substring(thisParent.lastIndexOf(".")+1);
    			else
    				thisParentComparedString = thisParent;
    			String otherParentComparedString = null;
    			if(otherParent.contains("."))
    				otherParentComparedString = otherParent.substring(otherParent.lastIndexOf(".")+1);
    			else
    				otherParentComparedString = otherParent;
    			if(thisParentComparedString.equals(otherParentComparedString))
    				return generalization;
    		}
    	}
    	return null;
    }

    public UMLRealization matchRealization(UMLRealization otherRealization) {
    	ListIterator<UMLRealization> realizationIt = realizationList.listIterator();
    	while(realizationIt.hasNext()) {
    		UMLRealization realization = realizationIt.next();
    		if(realization.getClient().equals(otherRealization.getClient())) {
    			String thisSupplier = realization.getSupplier();
    			String otherSupplier = otherRealization.getSupplier();
    			String thisSupplierComparedString = null;
    			if(thisSupplier.contains("."))
    				thisSupplierComparedString = thisSupplier.substring(thisSupplier.lastIndexOf(".")+1);
    			else
    				thisSupplierComparedString = thisSupplier;
    			String otherSupplierComparedString = null;
    			if(otherSupplier.contains("."))
    				otherSupplierComparedString = otherSupplier.substring(otherSupplier.lastIndexOf(".")+1);
    			else
    				otherSupplierComparedString = otherSupplier;
    			if(thisSupplierComparedString.equals(otherSupplierComparedString))
    				return realization;
    		}
    	}
    	return null;
    }

    public UMLModelDiff diff(UMLModel umlModel) throws RefactoringMinerTimedOutException {
    	return this.diff(umlModel, Collections.<String, String>emptyMap());
    }

	public UMLModelDiff diff(UMLModel umlModel, Map<String, String> renamedFileHints) throws RefactoringMinerTimedOutException {
    	UMLModelDiff modelDiff = new UMLModelDiff();
    	for(UMLClass umlClass : classList) {
    		if(!umlModel.classList.contains(umlClass))
    			modelDiff.reportRemovedClass(umlClass);
    	}
    	for(UMLClass umlClass : umlModel.classList) {
    		if(!this.classList.contains(umlClass))
    			modelDiff.reportAddedClass(umlClass);
    	}
		Set<String> repositoryDirectories = umlModel.repositoryDirectories;
		UMLClassMatcher matcher = new UMLClassMatcher.Move();
    	for(Iterator<UMLClass> removedClassIterator = modelDiff.removedClasses.iterator(); removedClassIterator.hasNext();) {
			   UMLClass removedClass = removedClassIterator.next();
			   TreeSet<UMLClassMoveDiff> diffSet = new TreeSet<UMLClassMoveDiff>(new ClassMoveComparator());
			   for(Iterator<UMLClass> addedClassIterator = modelDiff.addedClasses.iterator(); addedClassIterator.hasNext();) {
				   UMLClass addedClass = addedClassIterator.next();
				   String removedClassSourceFile = removedClass.getSourceFile();
				   String renamedFile =  renamedFileHints.get(removedClassSourceFile);
				   String removedClassSourceFolder = "";
				   if(removedClassSourceFile.contains("/")) {
					   removedClassSourceFolder = removedClassSourceFile.substring(0, removedClassSourceFile.lastIndexOf("/"));
				   }
				   if(!repositoryDirectories.contains(removedClassSourceFolder)) {
					   modelDiff.deletedFolderPaths.add(removedClassSourceFolder);
					   //add deleted sub-directories
					   String subDirectory = new String(removedClassSourceFolder);
					   while(subDirectory.contains("/")) {
						   subDirectory = subDirectory.substring(0, subDirectory.lastIndexOf("/"));
						   if(!repositoryDirectories.contains(subDirectory)) {
							   modelDiff.deletedFolderPaths.add(subDirectory);
						   }
					   }
				   }
				   if(matcher.match(removedClass, addedClass, renamedFile)) {
					   if(!modelDiff.conflictingMoveOfTopLevelClass(removedClass, addedClass)) {
						   UMLClassMoveDiff classMoveDiff = new UMLClassMoveDiff(removedClass, addedClass, modelDiff);
						   diffSet.add(classMoveDiff);
					   }
				   }
			   }
			   if(!diffSet.isEmpty()) {
				   UMLClassMoveDiff minClassMoveDiff = diffSet.first();
				   minClassMoveDiff.process();
				   modelDiff.classMoveDiffList.add(minClassMoveDiff);
				   modelDiff.addedClasses.remove(minClassMoveDiff.getMovedClass());
				   removedClassIterator.remove();
			   }
		   }
		
		   List<UMLClassMoveDiff> allClassMoves = new ArrayList<UMLClassMoveDiff>(modelDiff.classMoveDiffList);
		   Collections.sort(allClassMoves);
		
		   for(int i=0; i<allClassMoves.size(); i++) {
			   UMLClassMoveDiff classMoveI = allClassMoves.get(i);
			   for(int j=i+1; j<allClassMoves.size(); j++) {
				   UMLClassMoveDiff classMoveJ = allClassMoves.get(j);
				   if(classMoveI.isInnerClassMove(classMoveJ)) {
					   modelDiff.innerClassMoveDiffList.add(classMoveJ);
				   }
			   }
		   }
		   modelDiff.classMoveDiffList.removeAll(modelDiff.innerClassMoveDiffList);
    	modelDiff.checkForRenamedClasses(renamedFileHints, new UMLClassMatcher.Rename());
    	for(UMLGeneralization umlGeneralization : generalizationList) {
    		if(!umlModel.generalizationList.contains(umlGeneralization))
    			modelDiff.reportRemovedGeneralization(umlGeneralization);
    	}
    	for(UMLGeneralization umlGeneralization : umlModel.generalizationList) {
    		if(!this.generalizationList.contains(umlGeneralization))
    			modelDiff.reportAddedGeneralization(umlGeneralization);
    	}
    	modelDiff.checkForGeneralizationChanges();
    	for(UMLRealization umlRealization : realizationList) {
    		if(!umlModel.realizationList.contains(umlRealization))
    			modelDiff.reportRemovedRealization(umlRealization);
    	}
    	for(UMLRealization umlRealization : umlModel.realizationList) {
    		if(!this.realizationList.contains(umlRealization))
    			modelDiff.reportAddedRealization(umlRealization);
    	}
    	modelDiff.checkForRealizationChanges();
    	for(UMLClass umlClass : classList) {
    		if(umlModel.classList.contains(umlClass)) {
    			UMLClassDiff classDiff = new UMLClassDiff(umlClass, umlModel.getClass(umlClass), modelDiff);
    			classDiff.process();
    			if(!classDiff.isEmpty())
    				modelDiff.addUMLClassDiff(classDiff);
    		}
    	}
		Set<String> repositoryDirectories1 = umlModel.repositoryDirectories;
		UMLClassMatcher matcher1 = new UMLClassMatcher.RelaxedMove();
    	for(Iterator<UMLClass> removedClassIterator = modelDiff.removedClasses.iterator(); removedClassIterator.hasNext();) {
			   UMLClass removedClass = removedClassIterator.next();
			   TreeSet<UMLClassMoveDiff> diffSet = new TreeSet<UMLClassMoveDiff>(new ClassMoveComparator());
			   for(Iterator<UMLClass> addedClassIterator = modelDiff.addedClasses.iterator(); addedClassIterator.hasNext();) {
				   UMLClass addedClass = addedClassIterator.next();
				   String removedClassSourceFile = removedClass.getSourceFile();
				   String renamedFile =  renamedFileHints.get(removedClassSourceFile);
				   String removedClassSourceFolder = "";
				   if(removedClassSourceFile.contains("/")) {
					   removedClassSourceFolder = removedClassSourceFile.substring(0, removedClassSourceFile.lastIndexOf("/"));
				   }
				   if(!repositoryDirectories1.contains(removedClassSourceFolder)) {
					   modelDiff.deletedFolderPaths.add(removedClassSourceFolder);
					   //add deleted sub-directories
					   String subDirectory = new String(removedClassSourceFolder);
					   while(subDirectory.contains("/")) {
						   subDirectory = subDirectory.substring(0, subDirectory.lastIndexOf("/"));
						   if(!repositoryDirectories1.contains(subDirectory)) {
							   modelDiff.deletedFolderPaths.add(subDirectory);
						   }
					   }
				   }
				   if(matcher1.match(removedClass, addedClass, renamedFile)) {
					   if(!modelDiff.conflictingMoveOfTopLevelClass(removedClass, addedClass)) {
						   UMLClassMoveDiff classMoveDiff = new UMLClassMoveDiff(removedClass, addedClass, modelDiff);
						   diffSet.add(classMoveDiff);
					   }
				   }
			   }
			   if(!diffSet.isEmpty()) {
				   UMLClassMoveDiff minClassMoveDiff = diffSet.first();
				   minClassMoveDiff.process();
				   modelDiff.classMoveDiffList.add(minClassMoveDiff);
				   modelDiff.addedClasses.remove(minClassMoveDiff.getMovedClass());
				   removedClassIterator.remove();
			   }
		   }
		
		   List<UMLClassMoveDiff> allClassMoves = new ArrayList<UMLClassMoveDiff>(modelDiff.classMoveDiffList);
		   Collections.sort(allClassMoves);
		
		   for(int i=0; i<allClassMoves.size(); i++) {
			   UMLClassMoveDiff classMoveI = allClassMoves.get(i);
			   for(int j=i+1; j<allClassMoves.size(); j++) {
				   UMLClassMoveDiff classMoveJ = allClassMoves.get(j);
				   if(classMoveI.isInnerClassMove(classMoveJ)) {
					   modelDiff.innerClassMoveDiffList.add(classMoveJ);
				   }
			   }
		   }
		   modelDiff.classMoveDiffList.removeAll(modelDiff.innerClassMoveDiffList);
    	modelDiff.checkForRenamedClasses(renamedFileHints, new UMLClassMatcher.RelaxedRename());
    	return modelDiff;
    }
}
