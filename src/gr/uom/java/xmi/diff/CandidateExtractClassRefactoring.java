package gr.uom.java.xmi.diff;

public class CandidateExtractClassRefactoring {
	UMLClassBaseDiff classDiff;
	private ExtractClassRefactoring refactoring;
	
	public CandidateExtractClassRefactoring(UMLClassBaseDiff classDiff, ExtractClassRefactoring refactoring) {
		this.classDiff = classDiff;
		this.refactoring = refactoring;
	}
	
	public boolean innerClassExtract() {
		return refactoring.getExtractedClass().getName().startsWith(refactoring.getOriginalClass().getName() + ".");
	}

	public ExtractClassRefactoring getRefactoring() {
		return refactoring;
	}
}
