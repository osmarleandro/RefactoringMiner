package gr.uom.java.xmi.decomposition;

import java.util.List;
import java.util.Set;

import org.refactoringminer.api.Refactoring;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.replacement.Replacement;
import gr.uom.java.xmi.diff.InlineVariableRefactoring;
import gr.uom.java.xmi.diff.StringDistance;

public class CompositeStatementObjectMapping extends AbstractCodeMapping implements Comparable<CompositeStatementObjectMapping> {

	private double compositeChildMatchingScore;
	
	public CompositeStatementObjectMapping(CompositeStatementObject statement1, CompositeStatementObject statement2,
			UMLOperation operation1, UMLOperation operation2, double score) {
		super(statement1, statement2, operation1, operation2);
		this.compositeChildMatchingScore = score;
	}

	@Override
	public int compareTo(CompositeStatementObjectMapping o) {
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
			return Double.compare(distance1, distance2);
		}
		else {
			if(this.compositeChildMatchingScore != o.compositeChildMatchingScore) {
				return -Double.compare(this.compositeChildMatchingScore, o.compositeChildMatchingScore);
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
					return Integer.valueOf(indexDiff1).compareTo(Integer.valueOf(indexDiff2));
				}
			}
		}
	}

	public void inlinedVariableAssignment(AbstractCodeFragment statement, List<? extends AbstractCodeFragment> nonMappedLeavesT2, Set<Refactoring> refactorings) {
		for(VariableDeclaration declaration : statement.getVariableDeclarations()) {
			for(Replacement replacement : getReplacements()) {
				String variableName = declaration.getVariableName();
				AbstractExpression initializer = declaration.getInitializer();
				if(replacement.getBefore().startsWith(variableName + ".")) {
					String suffixBefore = replacement.getBefore().substring(variableName.length(), replacement.getBefore().length());
					if(replacement.getAfter().endsWith(suffixBefore)) {
						String prefixAfter = replacement.getAfter().substring(0, replacement.getAfter().indexOf(suffixBefore));
						if(initializer != null) {
							if(initializer.toString().equals(prefixAfter) ||
									overlappingExtractVariable(initializer, prefixAfter, nonMappedLeavesT2, refactorings)) {
								InlineVariableRefactoring ref = new InlineVariableRefactoring(declaration, operation1, operation2);
								processInlineVariableRefactoring(ref, refactorings);
								if(getReplacements().size() == 1) {
									identicalWithInlinedVariable = true;
								}
							}
						}
					}
				}
				if(variableName.equals(replacement.getBefore()) && initializer != null) {
					if(initializer.toString().equals(replacement.getAfter()) ||
							(initializer.toString().equals("(" + declaration.getType() + ")" + replacement.getAfter()) && !containsVariableNameReplacement(variableName)) ||
							ternaryMatch(initializer, replacement.getAfter()) ||
							reservedTokenMatch(initializer, replacement, replacement.getAfter()) ||
							overlappingExtractVariable(initializer, replacement.getAfter(), nonMappedLeavesT2, refactorings)) {
						InlineVariableRefactoring ref = new InlineVariableRefactoring(declaration, operation1, operation2);
						processInlineVariableRefactoring(ref, refactorings);
						if(getReplacements().size() == 1) {
							identicalWithInlinedVariable = true;
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
				if(variable.endsWith(replacement.getBefore()) && initializer.equals(replacement.getAfter())) {
					List<VariableDeclaration> variableDeclarations = operation1.getVariableDeclarationsInScope(fragment1.getLocationInfo());
					for(VariableDeclaration declaration : variableDeclarations) {
						if(declaration.getVariableName().equals(variable)) {
							InlineVariableRefactoring ref = new InlineVariableRefactoring(declaration, operation1, operation2);
							processInlineVariableRefactoring(ref, refactorings);
							if(getReplacements().size() == 1) {
								identicalWithInlinedVariable = true;
							}
						}
					}
				}
			}
		}
	}

}
