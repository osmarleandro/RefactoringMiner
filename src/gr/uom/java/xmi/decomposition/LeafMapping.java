package gr.uom.java.xmi.decomposition;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.refactoringminer.api.Refactoring;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.replacement.Replacement;
import gr.uom.java.xmi.LocationInfo.CodeElementType;
import gr.uom.java.xmi.diff.ExtractVariableRefactoring;
import gr.uom.java.xmi.diff.RenameOperationRefactoring;
import gr.uom.java.xmi.diff.StringDistance;
import gr.uom.java.xmi.diff.UMLClassBaseDiff;

public class LeafMapping extends AbstractCodeMapping implements Comparable<LeafMapping> {

	public LeafMapping(AbstractCodeFragment statement1, AbstractCodeFragment statement2,
			UMLOperation operation1, UMLOperation operation2) {
		super(statement1, statement2, operation1, operation2);
	}

	@Override
	public int compareTo(LeafMapping o) {
		double distance1;
		double distance2;
		if(this.getFragment1().getString().equals(this.getFragment2().getString())) {
			distance1 = 0;
		}
		else {
			String s1 = this.getFragment1().getString().toLowerCase();
			String s2 = this.getFragment2().getString().toLowerCase();
			int distance = StringDistance.editDistance(s1, s2);
			distance1 = (double)distance/(double)Math.max(s1.length(), s2.length());
		}
		
		if(o.getFragment1().getString().equals(o.getFragment2().getString())) {
			distance2 = 0;
		}
		else {
			String s1 = o.getFragment1().getString().toLowerCase();
			String s2 = o.getFragment2().getString().toLowerCase();
			int distance = StringDistance.editDistance(s1, s2);
			distance2 = (double)distance/(double)Math.max(s1.length(), s2.length());
		}
		
		if(distance1 != distance2) {
			if(this.isIdenticalWithExtractedVariable() && !o.isIdenticalWithExtractedVariable()) {
				return -1;
			}
			else if(!this.isIdenticalWithExtractedVariable() && o.isIdenticalWithExtractedVariable()) {
				return 1;
			}
			if(this.isIdenticalWithInlinedVariable() && !o.isIdenticalWithInlinedVariable()) {
				return -1;
			}
			else if(!this.isIdenticalWithInlinedVariable() && o.isIdenticalWithInlinedVariable()) {
				return 1;
			}
			return Double.compare(distance1, distance2);
		}
		else {
			int depthDiff1 = Math.abs(this.getFragment1().getDepth() - this.getFragment2().getDepth());
			int depthDiff2 = Math.abs(o.getFragment1().getDepth() - o.getFragment2().getDepth());

			if(depthDiff1 != depthDiff2) {
				return Integer.valueOf(depthDiff1).compareTo(Integer.valueOf(depthDiff2));
			}
			else {
				int indexDiff1 = Math.abs(this.getFragment1().getIndex() - this.getFragment2().getIndex());
				int indexDiff2 = Math.abs(o.getFragment1().getIndex() - o.getFragment2().getIndex());
				if(indexDiff1 != indexDiff2) {
					return Integer.valueOf(indexDiff1).compareTo(Integer.valueOf(indexDiff2));
				}
				else {
					double parentEditDistance1 = this.parentEditDistance();
					double parentEditDistance2 = o.parentEditDistance();
					return Double.compare(parentEditDistance1, parentEditDistance2);
				}
			}
		}
	}

	private double parentEditDistance() {
		CompositeStatementObject parent1 = getFragment1().getParent();
		while(parent1 != null && parent1.getLocationInfo().getCodeElementType().equals(CodeElementType.BLOCK)) {
			parent1 = parent1.getParent();
		}
		CompositeStatementObject parent2 = getFragment2().getParent();
		while(parent2 != null && parent2.getLocationInfo().getCodeElementType().equals(CodeElementType.BLOCK)) {
			parent2 = parent2.getParent();
		}
		if(parent1 == null && parent2 == null) {
			//method signature is the parent
			return 0;
		}
		else if(parent1 == null && parent2 != null) {
			String s2 = parent2.getString();
			int distance = StringDistance.editDistance("{", s2);
			double normalized = (double)distance/(double)Math.max(1, s2.length());
			return normalized;
		}
		else if(parent1 != null && parent2 == null) {
			String s1 = parent1.getString();
			int distance = StringDistance.editDistance(s1, "{");
			double normalized = (double)distance/(double)Math.max(s1.length(), 1);
			return normalized;
		}
		String s1 = parent1.getString();
		String s2 = parent2.getString();
		int distance = StringDistance.editDistance(s1, s2);
		double normalized = (double)distance/(double)Math.max(s1.length(), s2.length());
		return normalized;
	}

	public Set<String> callChainIntersection() {
		OperationInvocation invocation1 = this.getFragment1().invocationCoveringEntireFragment();
		OperationInvocation invocation2 = this.getFragment2().invocationCoveringEntireFragment();
		if(invocation1 != null && invocation2 != null) {
			return invocation1.callChainIntersection(invocation2);
		}
		return new LinkedHashSet<String>();
	}

	public void temporaryVariableAssignment(AbstractCodeFragment statement, List<? extends AbstractCodeFragment> nonMappedLeavesT2, Set<Refactoring> refactorings, UMLClassBaseDiff classDiff) {
		for(VariableDeclaration declaration : statement.getVariableDeclarations()) {
			String variableName = declaration.getVariableName();
			AbstractExpression initializer = declaration.getInitializer();
			for(Replacement replacement : getReplacements()) {
				if(replacement.getAfter().startsWith(variableName + ".")) {
					String suffixAfter = replacement.getAfter().substring(variableName.length(), replacement.getAfter().length());
					if(replacement.getBefore().endsWith(suffixAfter)) {
						String prefixBefore = replacement.getBefore().substring(0, replacement.getBefore().indexOf(suffixAfter));
						if(initializer != null) {
							if(initializer.toString().equals(prefixBefore) ||
									overlappingExtractVariable(initializer, prefixBefore, nonMappedLeavesT2, refactorings)) {
								ExtractVariableRefactoring ref = new ExtractVariableRefactoring(declaration, operation1, operation2);
								processExtractVariableRefactoring(ref, refactorings);
								if(getReplacements().size() == 1) {
									identicalWithExtractedVariable = true;
								}
							}
						}
					}
				}
				if(variableName.equals(replacement.getAfter()) && initializer != null) {
					if(initializer.toString().equals(replacement.getBefore()) ||
							(initializer.toString().equals("(" + declaration.getType() + ")" + replacement.getBefore()) && !containsVariableNameReplacement(variableName)) ||
							ternaryMatch(initializer, replacement.getBefore()) ||
							reservedTokenMatch(initializer, replacement, replacement.getBefore()) ||
							overlappingExtractVariable(initializer, replacement.getBefore(), nonMappedLeavesT2, refactorings)) {
						ExtractVariableRefactoring ref = new ExtractVariableRefactoring(declaration, operation1, operation2);
						processExtractVariableRefactoring(ref, refactorings);
						if(getReplacements().size() == 1) {
							identicalWithExtractedVariable = true;
						}
					}
				}
			}
			if(classDiff != null && initializer != null) {
				OperationInvocation invocation = initializer.invocationCoveringEntireFragment();
				if(invocation != null) {
					for(Refactoring refactoring : classDiff.getRefactoringsBeforePostProcessing()) {
						if(refactoring instanceof RenameOperationRefactoring) {
							RenameOperationRefactoring rename = (RenameOperationRefactoring)refactoring;
							if(invocation.getMethodName().equals(rename.getRenamedOperation().getName())) {
								String initializerBeforeRename = initializer.getString().replace(rename.getRenamedOperation().getName(), rename.getOriginalOperation().getName());
								if(getFragment1().getString().contains(initializerBeforeRename) && getFragment2().getString().contains(variableName)) {
									ExtractVariableRefactoring ref = new ExtractVariableRefactoring(declaration, operation1, operation2);
									processExtractVariableRefactoring(ref, refactorings);
								}
							}
						}
					}
				}
			}
		}
		String argumentizedString = statement.getArgumentizedString();
		if(argumentizedString.contains("=")) {
			String beforeAssignment = argumentizedString.substring(0, argumentizedString.indexOf("="));
			String[] tokens = beforeAssignment.split("\\s");
			String variable = tokens[tokens.length-1];
			String initializer = null;
			if(argumentizedString.endsWith(";\n")) {
				initializer = argumentizedString.substring(argumentizedString.indexOf("=")+1, argumentizedString.length()-2);
			}
			else {
				initializer = argumentizedString.substring(argumentizedString.indexOf("=")+1, argumentizedString.length());
			}
			for(Replacement replacement : getReplacements()) {
				if(variable.endsWith(replacement.getAfter()) &&	initializer.equals(replacement.getBefore())) {
					List<VariableDeclaration> variableDeclarations = operation2.getVariableDeclarationsInScope(fragment2.getLocationInfo());
					for(VariableDeclaration declaration : variableDeclarations) {
						if(declaration.getVariableName().equals(variable)) {
							ExtractVariableRefactoring ref = new ExtractVariableRefactoring(declaration, operation1, operation2);
							processExtractVariableRefactoring(ref, refactorings);
							if(getReplacements().size() == 1) {
								identicalWithExtractedVariable = true;
							}
						}
					}
				}
			}
		}
	}
}
