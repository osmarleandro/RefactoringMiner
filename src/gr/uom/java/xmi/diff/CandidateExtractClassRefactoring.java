package gr.uom.java.xmi.diff;

public class CandidateExtractClassRefactoring {
	private UMLClassBaseDiff classDiff;
	private ExtractClassRefactoring_RENAMED refactoring;
	
	public CandidateExtractClassRefactoring(UMLClassBaseDiff classDiff, ExtractClassRefactoring_RENAMED refactoring) {
		this.classDiff = classDiff;
		this.refactoring = refactoring;
	}
	
	public boolean innerClassExtract() {
		return refactoring.getExtractedClass().getName().startsWith(refactoring.getOriginalClass().getName() + ".");
	}

	public UMLClassBaseDiff getClassDiff() {
		return classDiff;
	}

	public ExtractClassRefactoring_RENAMED getRefactoring() {
		return refactoring;
	}
}
