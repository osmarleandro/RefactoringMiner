package gr.uom.java.xmi.decomposition.replacement;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashSet;
import java.util.Set;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.UMLParameter;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;
import gr.uom.java.xmi.decomposition.VariableDeclaration;
import gr.uom.java.xmi.decomposition.VariableReplacementAnalysis;

public class SplitVariableReplacement extends Replacement {
	private Set<String> splitVariables;

	public SplitVariableReplacement(String oldVariable, Set<String> newVariables) {
		super(oldVariable, newVariables.toString(), ReplacementType.SPLIT_VARIABLE);
		this.splitVariables = newVariables;
	}

	public Set<String> getSplitVariables() {
		return splitVariables;
	}

	public boolean equal(SplitVariableReplacement other) {
		return this.getBefore().equals(other.getBefore()) &&
				this.splitVariables.containsAll(other.splitVariables) &&
				other.splitVariables.containsAll(this.splitVariables);
	}

	public boolean commonBefore(SplitVariableReplacement other) {
		Set<String> interestion = new LinkedHashSet<String>(this.splitVariables);
		interestion.retainAll(other.splitVariables);
		return this.getBefore().equals(other.getBefore()) && interestion.size() == 0;
	}

	public boolean subsumes(SplitVariableReplacement other) {
		return this.getBefore().equals(other.getBefore()) &&
				this.splitVariables.containsAll(other.splitVariables) &&
				this.splitVariables.size() > other.splitVariables.size();
	}

	public SimpleEntry<VariableDeclaration, UMLOperation> getVariableDeclaration2(VariableReplacementAnalysis variableReplacementAnalysis, String variableName) {
		for(AbstractCodeMapping mapping : variableReplacementAnalysis.mappings) {
			if(mapping.getReplacements().contains(this)) {
				Set<String> foundSplitVariables = new LinkedHashSet<String>();
				for(Replacement r : mapping.getReplacements()) {
					if(getSplitVariables().contains(r.getAfter())) {
						foundSplitVariables.add(r.getAfter());
					}
				}
				if(mapping.getReplacements().contains(this) || foundSplitVariables.equals(getSplitVariables())) {
					VariableDeclaration vd = mapping.getFragment2().searchVariableDeclaration(variableName);
					if(vd != null) {
						return new SimpleEntry<VariableDeclaration, UMLOperation>(vd, mapping.getOperation2());
					}
				}
			}
		}
		for(UMLParameter parameter : variableReplacementAnalysis.operation2.getParameters()) {
			VariableDeclaration vd = parameter.getVariableDeclaration();
			if(vd != null && vd.getVariableName().equals(variableName)) {
				return new SimpleEntry<VariableDeclaration, UMLOperation>(vd, variableReplacementAnalysis.operation2);
			}
		}
		if(variableReplacementAnalysis.callSiteOperation != null) {
			for(UMLParameter parameter : variableReplacementAnalysis.callSiteOperation.getParameters()) {
				VariableDeclaration vd = parameter.getVariableDeclaration();
				if(vd != null && vd.getVariableName().equals(variableName)) {
					return new SimpleEntry<VariableDeclaration, UMLOperation>(vd, variableReplacementAnalysis.callSiteOperation);
				}
			}
		}
		return null;
	}
}
