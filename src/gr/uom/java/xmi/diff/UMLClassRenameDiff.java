package gr.uom.java.xmi.diff;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.refactoringminer.api.Refactoring;

import gr.uom.java.xmi.UMLAttribute;
import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.decomposition.OperationInvocation;
import gr.uom.java.xmi.decomposition.VariableDeclaration;

public class UMLClassRenameDiff extends UMLClassBaseDiff {
	
	public UMLClassRenameDiff(UMLClass originalClass, UMLClass renamedClass, UMLModelDiff modelDiff) {
		super(originalClass, renamedClass, modelDiff);
	}

	public UMLClass getRenamedClass() {
		return nextClass;
	}

	public boolean samePackage() {
		return originalClass.getPackageName().equals(nextClass.getPackageName());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ");
		sb.append(originalClass.getName());
		sb.append(" was renamed to ");
		sb.append(nextClass.getName());
		sb.append("\n");
		return sb.toString();
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
