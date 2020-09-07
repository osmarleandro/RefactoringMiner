package gr.uom.java.xmi.diff;

public class CandidateExtractClassRefactoring {
	private UMLClassBaseDiff_RENAMED classDiff;
	private ExtractClassRefactoring refactoring;
	
	public CandidateExtractClassRefactoring(UMLClassBaseDiff_RENAMED classDiff, ExtractClassRefactoring refactoring) {
		this.classDiff = classDiff;
		this.refactoring = refactoring;
	}
	
	public boolean innerClassExtract() {
		return refactoring.getExtractedClass().getName().startsWith(refactoring.getOriginalClass().getName() + ".");
	}

	public UMLClassBaseDiff_RENAMED getClassDiff() {
		return classDiff;
	}

	public ExtractClassRefactoring getRefactoring() {
		return refactoring;
	}
}
