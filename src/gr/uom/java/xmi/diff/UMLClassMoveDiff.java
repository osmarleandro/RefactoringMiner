package gr.uom.java.xmi.diff;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.refactoringminer.api.Refactoring;
import org.refactoringminer.util.PrefixSuffixUtils;

import gr.uom.java.xmi.UMLAttribute;
import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.decomposition.StatementObject;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper;
import gr.uom.java.xmi.decomposition.VariableDeclaration;
import gr.uom.java.xmi.decomposition.replacement.Replacement;

public class UMLClassMoveDiff extends UMLClassBaseDiff {
	
	public UMLClassMoveDiff(UMLClass originalClass, UMLClass movedClass, UMLModelDiff modelDiff) {
		super(originalClass, movedClass, modelDiff);
	}

	public UMLClass getMovedClass() {
		return nextClass;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ");
		sb.append(originalClass.getName());
		sb.append(" was moved to ");
		sb.append(nextClass.getName());
		sb.append("\n");
		return sb.toString();
	}

	public boolean equals(Object o) {
		if(this == o) {
    		return true;
    	}
		
		if(o instanceof UMLClassMoveDiff) {
			UMLClassMoveDiff classMoveDiff = (UMLClassMoveDiff)o;
			return this.originalClass.equals(classMoveDiff.originalClass) && this.nextClass.equals(classMoveDiff.nextClass);
		}
		return false;
	}

	private Set<Refactoring> inferAttributeMergesAndSplits(Map<Replacement, Set<CandidateAttributeRefactoring>> map, List<Refactoring> refactorings) {
		Set<Refactoring> newRefactorings = new LinkedHashSet<Refactoring>();
		for(Replacement replacement : map.keySet()) {
			Set<CandidateAttributeRefactoring> candidates = map.get(replacement);
			for(CandidateAttributeRefactoring candidate : candidates) {
				String originalAttributeName = PrefixSuffixUtils.normalize(candidate.getOriginalVariableName());
				String renamedAttributeName = PrefixSuffixUtils.normalize(candidate.getRenamedVariableName());
				UMLOperationBodyMapper candidateMapper = null;
				for(UMLOperationBodyMapper mapper : operationBodyMapperList) {
					if(mapper.getMappings().containsAll(candidate.getAttributeReferences())) {
						candidateMapper = mapper;
						break;
					}
					for(UMLOperationBodyMapper nestedMapper : mapper.getChildMappers()) {
						if(nestedMapper.getMappings().containsAll(candidate.getAttributeReferences())) {
							candidateMapper = nestedMapper;
							break;
						}
					}
				}
				for(Refactoring refactoring : refactorings) {
					if(refactoring instanceof MergeVariableRefactoring) {
						MergeVariableRefactoring merge = (MergeVariableRefactoring)refactoring;
						Set<String> nonMatchingVariableNames = new LinkedHashSet<String>();
						String matchingVariableName = null;
						for(VariableDeclaration variableDeclaration : merge.getMergedVariables()) {
							if(originalAttributeName.equals(variableDeclaration.getVariableName())) {
								matchingVariableName = variableDeclaration.getVariableName();
							}
							else {
								for(StatementObject statement : candidateMapper.getNonMappedLeavesT1()) {
									if(statement.getString().startsWith(variableDeclaration.getVariableName() + "=") ||
											statement.getString().startsWith("this." + variableDeclaration.getVariableName() + "=")) {
										nonMatchingVariableNames.add(variableDeclaration.getVariableName());
										break;
									}
								}
							}
						}
						if(matchingVariableName != null && renamedAttributeName.equals(merge.getNewVariable().getVariableName()) && nonMatchingVariableNames.size() > 0) {
							Set<UMLAttribute> mergedAttributes = new LinkedHashSet<UMLAttribute>();
							Set<VariableDeclaration> mergedVariables = new LinkedHashSet<VariableDeclaration>();
							Set<String> allMatchingVariables = new LinkedHashSet<String>();
							if(merge.getMergedVariables().iterator().next().getVariableName().equals(matchingVariableName)) {
								allMatchingVariables.add(matchingVariableName);
								allMatchingVariables.addAll(nonMatchingVariableNames);
							}
							else {
								allMatchingVariables.addAll(nonMatchingVariableNames);
								allMatchingVariables.add(matchingVariableName);
							}
							for(String mergedVariable : allMatchingVariables) {
								UMLAttribute a1 = findAttributeInOriginalClass(mergedVariable);
								if(a1 != null) {
									mergedAttributes.add(a1);
									mergedVariables.add(a1.getVariableDeclaration());
								}
							}
							UMLAttribute a2 = findAttributeInNextClass(renamedAttributeName);
							if(mergedVariables.size() > 1 && mergedVariables.size() == merge.getMergedVariables().size() && a2 != null) {
								MergeAttributeRefactoring ref = new MergeAttributeRefactoring(mergedVariables, a2.getVariableDeclaration(), getOriginalClassName(), getNextClassName(), new LinkedHashSet<CandidateMergeVariableRefactoring>());
								if(!refactorings.contains(ref)) {
									newRefactorings.add(ref);
								}
							}
						}
					}
					else if(refactoring instanceof SplitVariableRefactoring) {
						SplitVariableRefactoring split = (SplitVariableRefactoring)refactoring;
						Set<String> nonMatchingVariableNames = new LinkedHashSet<String>();
						String matchingVariableName = null;
						for(VariableDeclaration variableDeclaration : split.getSplitVariables()) {
							if(renamedAttributeName.equals(variableDeclaration.getVariableName())) {
								matchingVariableName = variableDeclaration.getVariableName();
							}
							else {
								for(StatementObject statement : candidateMapper.getNonMappedLeavesT2()) {
									if(statement.getString().startsWith(variableDeclaration.getVariableName() + "=") ||
											statement.getString().startsWith("this." + variableDeclaration.getVariableName() + "=")) {
										nonMatchingVariableNames.add(variableDeclaration.getVariableName());
										break;
									}
								}
							}
						}
						if(matchingVariableName != null && originalAttributeName.equals(split.getOldVariable().getVariableName()) && nonMatchingVariableNames.size() > 0) {
							Set<UMLAttribute> splitAttributes = new LinkedHashSet<UMLAttribute>();
							Set<VariableDeclaration> splitVariables = new LinkedHashSet<VariableDeclaration>();
							Set<String> allMatchingVariables = new LinkedHashSet<String>();
							if(split.getSplitVariables().iterator().next().getVariableName().equals(matchingVariableName)) {
								allMatchingVariables.add(matchingVariableName);
								allMatchingVariables.addAll(nonMatchingVariableNames);
							}
							else {
								allMatchingVariables.addAll(nonMatchingVariableNames);
								allMatchingVariables.add(matchingVariableName);
							}
							for(String splitVariable : allMatchingVariables) {
								UMLAttribute a2 = findAttributeInNextClass(splitVariable);
								if(a2 != null) {
									splitAttributes.add(a2);
									splitVariables.add(a2.getVariableDeclaration());
								}
							}
							UMLAttribute a1 = findAttributeInOriginalClass(originalAttributeName);
							if(splitVariables.size() > 1 && splitVariables.size() == split.getSplitVariables().size() && a1 != null) {
								SplitAttributeRefactoring ref = new SplitAttributeRefactoring(a1.getVariableDeclaration(), splitVariables, getOriginalClassName(), getNextClassName(), new LinkedHashSet<CandidateSplitVariableRefactoring>());
								if(!refactorings.contains(ref)) {
									newRefactorings.add(ref);
								}
							}
						}
					}
				}
			}
		}
		return newRefactorings;
	}
}
