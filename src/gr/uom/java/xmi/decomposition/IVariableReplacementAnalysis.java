package gr.uom.java.xmi.decomposition;

import java.util.Set;

import gr.uom.java.xmi.diff.CandidateAttributeRefactoring;
import gr.uom.java.xmi.diff.CandidateMergeVariableRefactoring;
import gr.uom.java.xmi.diff.CandidateSplitVariableRefactoring;
import gr.uom.java.xmi.diff.MergeVariableRefactoring;
import gr.uom.java.xmi.diff.RenameVariableRefactoring;
import gr.uom.java.xmi.diff.SplitVariableRefactoring;

public interface IVariableReplacementAnalysis {

	Set<RenameVariableRefactoring> getVariableRenames();

	Set<MergeVariableRefactoring> getVariableMerges();

	Set<SplitVariableRefactoring> getVariableSplits();

	Set<CandidateAttributeRefactoring> getCandidateAttributeRenames();

	Set<CandidateMergeVariableRefactoring> getCandidateAttributeMerges();

	Set<CandidateSplitVariableRefactoring> getCandidateAttributeSplits();

}