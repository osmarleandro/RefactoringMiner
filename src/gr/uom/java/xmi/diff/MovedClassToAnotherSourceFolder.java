package gr.uom.java.xmi.diff;

import org.refactoringminer.util.PrefixSuffixUtils;

import gr.uom.java.xmi.UMLClass;

public class MovedClassToAnotherSourceFolder {
	//private String className;
	private UMLClass originalClass;
	private UMLClass movedClass;
	private String originalPath;
	private String movedPath;

	public MovedClassToAnotherSourceFolder(UMLClass originalClass, UMLClass movedClass,
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

	public UMLClass getOriginalClass() {
		return originalClass;
	}

	public UMLClass getMovedClass() {
		return movedClass;
	}

	public RenamePattern getRenamePattern() {
		int l1 = originalPath.length();
		int l2 = movedPath.length();
		int separatorPos1 = -1; 
		int lmin = Math.min(originalPath.length(), movedPath.length());
		boolean equal = true;
		for (int i = 0; i < lmin; i++) {
			char c1 = originalPath.charAt(l1 - i - 1);
			char c2 = movedPath.charAt(l2 - i - 1);
			equal = equal && c1 == c2;
			if (equal && c1 == '/') {
				separatorPos1 = i;
			}
		}
		int separatorPos = separatorPos1;
		if (separatorPos == -1) {
			return new RenamePattern(originalPath, movedPath);
		}
		String original = originalPath.substring(0, originalPath.length() - separatorPos);
		String moved = movedPath.substring(0, movedPath.length() - separatorPos);
		return new RenamePattern(original, moved);
	}
}
