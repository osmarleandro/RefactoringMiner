package gr.uom.java.xmi.diff;

public interface ICandidateExtractClassRefactoring {

	boolean innerClassExtract();

	UMLClassBaseDiff getClassDiff();

	ExtractClassRefactoring getRefactoring();

}