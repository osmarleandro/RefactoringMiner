package gr.uom.java.xmi.diff;

public class CandidateExtractClassRefactoring_RENAMED {
	private UMLClassBaseDiff classDiff;
	private ExtractClassRefactoring refactoring;
	
	public CandidateExtractClassRefactoring_RENAMED(UMLClassBaseDiff classDiff, ExtractClassRefactoring refactoring) {
		this.classDiff = classDiff;
		this.refactoring = refactoring;
	}
	
	public boolean innerClassExtract() {
		return refactoring.getExtractedClass().getName().startsWith(refactoring.getOriginalClass().getName() + ".");
	}

	public UMLClassBaseDiff getClassDiff() {
		return classDiff;
	}

	public ExtractClassRefactoring getRefactoring() {
		return refactoring;
	}
}
