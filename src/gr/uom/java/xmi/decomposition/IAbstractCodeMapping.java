package gr.uom.java.xmi.decomposition;

import java.util.List;
import java.util.Set;

import org.refactoringminer.api.Refactoring;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.replacement.Replacement;
import gr.uom.java.xmi.decomposition.replacement.Replacement.ReplacementType;
import gr.uom.java.xmi.diff.UMLClassBaseDiff;

public interface IAbstractCodeMapping {

	AbstractCodeFragment getFragment1();

	AbstractCodeFragment getFragment2();

	UMLOperation getOperation1();

	UMLOperation getOperation2();

	boolean isIdenticalWithExtractedVariable();

	boolean isIdenticalWithInlinedVariable();

	boolean isExact();

	void addReplacement(Replacement replacement);

	void addReplacements(Set<Replacement> replacements);

	Set<Replacement> getReplacements();

	boolean containsReplacement(ReplacementType type);

	Set<ReplacementType> getReplacementTypes();

	String toString();

	void temporaryVariableAssignment(Set<Refactoring> refactorings);

	void temporaryVariableAssignment(AbstractCodeFragment statement,
			List<? extends AbstractCodeFragment> nonMappedLeavesT2, Set<Refactoring> refactorings,
			UMLClassBaseDiff classDiff);

	void inlinedVariableAssignment(AbstractCodeFragment statement,
			List<? extends AbstractCodeFragment> nonMappedLeavesT2, Set<Refactoring> refactorings);

	Set<Replacement> commonReplacements(AbstractCodeMapping other);

	int hashCode();

	boolean equals(Object obj);

}