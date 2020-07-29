package gr.uom.java.xmi.diff;

public class CandidateExtractClassRefactoring {
	private UMLClassBaseDiff classDiff;
	private ExtractClassRefactoring refactoring;
	
	public CandidateExtractClassRefactoring(UMLClassBaseDiff classDiff, ExtractClassRefactoring refactoring) {
		this.classDiff = classDiff;
		this.refactoring = refactoring;
	}
	
	public boolean innerClassExtract() {
		return refactoring.getExtractedClass().getName().startsWith(refactoring.getAttributeOfExtractedClassTypeInOriginalClass().getOriginalClass(this).getName() + ".");
	}

	public UMLClassBaseDiff getClassDiff() {
		return classDiff;
	}

	public ExtractClassRefactoring getRefactoring() {
		return refactoring;
	}
}
