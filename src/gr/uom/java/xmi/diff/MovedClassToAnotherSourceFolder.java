package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLClass;

public class MovedClassToAnotherSourceFolder {
	//private String className;
	private UMLClass originalClass;
	private UMLClass movedClass;
	public String originalPath;
	public String movedPath;

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
}
