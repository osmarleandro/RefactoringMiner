package gr.uom.java.xmi.decomposition.replacement;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashSet;
import java.util.Set;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.UMLParameter;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;
import gr.uom.java.xmi.decomposition.VariableDeclaration;
import gr.uom.java.xmi.decomposition.VariableReplacementAnalysis;

public class MergeVariableReplacement extends Replacement {
	private Set<String> mergedVariables;
	
	public MergeVariableReplacement(Set<String> mergedVariables, String newVariable) {
		super(mergedVariables.toString(), newVariable, ReplacementType.MERGE_VARIABLES);
		this.mergedVariables = mergedVariables;
	}

	public Set<String> getMergedVariables() {
		return mergedVariables;
	}

	public boolean equal(MergeVariableReplacement other) {
		return this.getAfter().equals(other.getAfter()) &&
				this.mergedVariables.containsAll(other.mergedVariables) &&
				other.mergedVariables.containsAll(this.mergedVariables);
	}

	public boolean commonAfter(MergeVariableReplacement other) {
		Set<String> interestion = new LinkedHashSet<String>(this.mergedVariables);
		interestion.retainAll(other.mergedVariables);
		return this.getAfter().equals(other.getAfter()) && interestion.size() == 0;
	}

	public boolean subsumes(MergeVariableReplacement other) {
		return this.getAfter().equals(other.getAfter()) &&
				this.mergedVariables.containsAll(other.mergedVariables) &&
				this.mergedVariables.size() > other.mergedVariables.size();
	}

	public SimpleEntry<VariableDeclaration, UMLOperation> getVariableDeclaration2(VariableReplacementAnalysis variableReplacementAnalysis) {
		for(AbstractCodeMapping mapping : variableReplacementAnalysis.mappings) {
			Set<String> foundMergedVariables = new LinkedHashSet<String>();
			for(Replacement r : mapping.getReplacements()) {
				if(getMergedVariables().contains(r.getBefore())) {
					foundMergedVariables.add(r.getBefore());
				}
			}
			if(mapping.getReplacements().contains(this) || foundMergedVariables.equals(getMergedVariables())) {
				VariableDeclaration vd = mapping.getFragment2().searchVariableDeclaration(getAfter());
				if(vd != null) {
					return new SimpleEntry<VariableDeclaration, UMLOperation>(vd, mapping.getOperation2());
				}
			}
		}
		for(UMLParameter parameter : variableReplacementAnalysis.operation2.getParameters()) {
			VariableDeclaration vd = parameter.getVariableDeclaration();
			if(vd != null && vd.getVariableName().equals(getAfter())) {
				return new SimpleEntry<VariableDeclaration, UMLOperation>(vd, variableReplacementAnalysis.operation2);
			}
		}
		if(variableReplacementAnalysis.callSiteOperation != null) {
			for(UMLParameter parameter : variableReplacementAnalysis.callSiteOperation.getParameters()) {
				VariableDeclaration vd = parameter.getVariableDeclaration();
				if(vd != null && vd.getVariableName().equals(getAfter())) {
					return new SimpleEntry<VariableDeclaration, UMLOperation>(vd, variableReplacementAnalysis.callSiteOperation);
				}
			}
		}
		return null;
	}
}
