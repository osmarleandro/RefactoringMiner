package gr.uom.java.xmi.diff;

import org.refactoringminer.util.PrefixSuffixUtils;

import gr.uom.java.xmi.UMLClass_RENAMED;

public class MovedClassToAnotherSourceFolder {
	//private String className;
	private UMLClass_RENAMED originalClass;
	private UMLClass_RENAMED movedClass;
	private String originalPath;
	private String movedPath;

	public MovedClassToAnotherSourceFolder(UMLClass_RENAMED originalClass, UMLClass_RENAMED movedClass,
			String originalPath, String movedPath) {
		this.originalClass = originalClass;
		this.movedClass = movedClass;
		this.originalPath = originalPath;
		this.movedPath = movedPath;
	}
	
	public String getOriginalClassName() {
		return originalClass.getName();
	}

	public String getMovedClassName() {
		return movedClass.getName();
	}

	public UMLClass_RENAMED getOriginalClass() {
		return originalClass;
	}

	public UMLClass_RENAMED getMovedClass() {
		return movedClass;
	}

	public RenamePattern getRenamePattern() {
		int separatorPos = PrefixSuffixUtils.separatorPosOfCommonSuffix('/', originalPath, movedPath);
		if (separatorPos == -1) {
			return new RenamePattern(originalPath, movedPath);
		}
		String original = originalPath.substring(0, originalPath.length() - separatorPos);
		String moved = movedPath.substring(0, movedPath.length() - separatorPos);
		return new RenamePattern(original, moved);
	}
}
