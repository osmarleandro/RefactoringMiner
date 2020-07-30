package gr.uom.java.xmi.diff;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.refactoringminer.api.Refactoring;

import gr.uom.java.xmi.UMLAttribute;
import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.decomposition.OperationInvocation;
import gr.uom.java.xmi.decomposition.VariableDeclaration;

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

	private boolean multipleExtractedMethodInvocationsWithDifferentAttributesAsArguments(CandidateAttributeRefactoring candidate, List<Refactoring> refactorings) {
		for(Refactoring refactoring : refactorings) {
			if(refactoring instanceof ExtractOperationRefactoring) {
				ExtractOperationRefactoring extractRefactoring = (ExtractOperationRefactoring)refactoring;
				if(extractRefactoring.getExtractedOperation().equals(candidate.getOperationAfter())) {
					List<OperationInvocation> extractedInvocations = extractRefactoring.getExtractedOperationInvocations();
					if(extractedInvocations.size() > 1) {
						Set<VariableDeclaration> attributesMatchedWithArguments = new LinkedHashSet<VariableDeclaration>();
						Set<String> attributeNamesMatchedWithArguments = new LinkedHashSet<String>();
						for(OperationInvocation extractedInvocation : extractedInvocations) {
							for(String argument : extractedInvocation.getArguments()) {
								for(UMLAttribute attribute : originalClass.getAttributes()) {
									if(attribute.getName().equals(argument)) {
										attributesMatchedWithArguments.add(attribute.getVariableDeclaration());
										attributeNamesMatchedWithArguments.add(attribute.getName());
										break;
									}
								}
							}
						}
						if((attributeNamesMatchedWithArguments.contains(candidate.getOriginalVariableName()) ||
								attributeNamesMatchedWithArguments.contains(candidate.getRenamedVariableName())) &&
								attributesMatchedWithArguments.size() > 1) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
