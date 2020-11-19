package gr.uom.java.xmi.decomposition;

import java.util.Set;

import gr.uom.java.xmi.diff.ChangeVariableTypeRefactoring;

public interface ITypeReplacementAnalysis {

	Set<ChangeVariableTypeRefactoring> getChangedTypes();

}