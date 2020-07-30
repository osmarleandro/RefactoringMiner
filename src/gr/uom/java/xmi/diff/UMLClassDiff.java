package gr.uom.java.xmi.diff;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringMinerTimedOutException;

import gr.uom.java.xmi.UMLAnonymousClass;
import gr.uom.java.xmi.UMLAttribute;
import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.UMLType;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper;
import gr.uom.java.xmi.decomposition.VariableDeclaration;
import gr.uom.java.xmi.decomposition.VariableReferenceExtractor;
import gr.uom.java.xmi.decomposition.replacement.ConsistentReplacementDetector;
import gr.uom.java.xmi.decomposition.replacement.MergeVariableReplacement;
import gr.uom.java.xmi.decomposition.replacement.Replacement;
import gr.uom.java.xmi.decomposition.replacement.SplitVariableReplacement;

public class UMLClassDiff extends UMLClassBaseDiff {
	
	private String className;
	public UMLClassDiff(UMLClass originalClass, UMLClass nextClass, UMLModelDiff modelDiff) {
		super(originalClass, nextClass, modelDiff);
		this.className = originalClass.getName();
	}

	private void reportAddedOperation(UMLOperation umlOperation) {
		this.addedOperations.add(umlOperation);
	}

	private void reportRemovedOperation(UMLOperation umlOperation) {
		this.removedOperations.add(umlOperation);
	}

	private void reportAddedAttribute(UMLAttribute umlAttribute) {
		this.addedAttributes.add(umlAttribute);
	}

	private void reportRemovedAttribute(UMLAttribute umlAttribute) {
		this.removedAttributes.add(umlAttribute);
	}

	protected void processAttributes() {
		for(UMLAttribute attribute : originalClass.getAttributes()) {
			UMLAttribute matchingAttribute = nextClass.containsAttribute(attribute);
    		if(matchingAttribute == null) {
    			this.reportRemovedAttribute(attribute);
    		}
    		else {
    			UMLAttributeDiff attributeDiff = new UMLAttributeDiff(attribute, matchingAttribute, getOperationBodyMapperList());
    			if(!attributeDiff.isEmpty()) {
	    			refactorings.addAll(attributeDiff.getRefactorings());
	    			this.attributeDiffList.add(attributeDiff);
    			}
    		}
    	}
    	for(UMLAttribute attribute : nextClass.getAttributes()) {
    		UMLAttribute matchingAttribute = originalClass.containsAttribute(attribute);
    		if(matchingAttribute == null) {
    			this.reportAddedAttribute(attribute);
    		}
    		else {
    			UMLAttributeDiff attributeDiff = new UMLAttributeDiff(matchingAttribute, attribute, getOperationBodyMapperList());
    			if(!attributeDiff.isEmpty()) {
	    			refactorings.addAll(attributeDiff.getRefactorings());
					this.attributeDiffList.add(attributeDiff);
    			}
    		}
    	}
	}

	protected void processOperations() {
		for(UMLOperation operation : originalClass.getOperations()) {
    		if(!nextClass.getOperations().contains(operation))
    			this.reportRemovedOperation(operation);
    	}
    	for(UMLOperation operation : nextClass.getOperations()) {
    		if(!originalClass.getOperations().contains(operation))
    			this.reportAddedOperation(operation);
    	}
	}

	protected void processAnonymousClasses() {
		for(UMLAnonymousClass umlAnonymousClass : originalClass.getAnonymousClassList()) {
    		if(!nextClass.getAnonymousClassList().contains(umlAnonymousClass))
    			this.reportRemovedAnonymousClass(umlAnonymousClass);
    	}
    	for(UMLAnonymousClass umlAnonymousClass : nextClass.getAnonymousClassList()) {
    		if(!originalClass.getAnonymousClassList().contains(umlAnonymousClass))
    			this.reportAddedAnonymousClass(umlAnonymousClass);
    	}
	}

	protected void createBodyMappers() throws RefactoringMinerTimedOutException {
		for(UMLOperation originalOperation : originalClass.getOperations()) {
			for(UMLOperation nextOperation : nextClass.getOperations()) {
				if(originalOperation.equalsQualified(nextOperation)) {
					if(getModelDiff() != null) {
						List<UMLOperationBodyMapper> mappers = getModelDiff().findMappersWithMatchingSignature2(nextOperation);
						if(mappers.size() > 0) {
							UMLOperation operation1 = mappers.get(0).getOperation1();
							if(!operation1.equalSignature(originalOperation) &&
									getModelDiff().commonlyImplementedOperations(operation1, nextOperation, this)) {
								if(!removedOperations.contains(originalOperation)) {
									removedOperations.add(originalOperation);
								}
								break;
							}
						}
					}
	    			UMLOperationBodyMapper operationBodyMapper = new UMLOperationBodyMapper(originalOperation, nextOperation, this);
	    			UMLOperationDiff operationSignatureDiff = new UMLOperationDiff(originalOperation, nextOperation, operationBodyMapper.getMappings());
					refactorings.addAll(operationSignatureDiff.getRefactorings());
	    			this.addOperationBodyMapper(operationBodyMapper);
				}
			}
		}
		for(UMLOperation operation : originalClass.getOperations()) {
			if(!containsMapperForOperation(operation) && nextClass.getOperations().contains(operation) && !removedOperations.contains(operation)) {
    			int index = nextClass.getOperations().indexOf(operation);
    			int lastIndex = nextClass.getOperations().lastIndexOf(operation);
    			int finalIndex = index;
    			if(index != lastIndex) {
    				double d1 = operation.getReturnParameter().getType().normalizedNameDistance(nextClass.getOperations().get(index).getReturnParameter().getType());
    				double d2 = operation.getReturnParameter().getType().normalizedNameDistance(nextClass.getOperations().get(lastIndex).getReturnParameter().getType());
    				if(d2 < d1) {
    					finalIndex = lastIndex;
    				}
    			}
    			UMLOperationBodyMapper operationBodyMapper = new UMLOperationBodyMapper(operation, nextClass.getOperations().get(finalIndex), this);
    			UMLOperationDiff operationSignatureDiff = new UMLOperationDiff(operation, nextClass.getOperations().get(finalIndex), operationBodyMapper.getMappings());
    			refactorings.addAll(operationSignatureDiff.getRefactorings());
    			this.addOperationBodyMapper(operationBodyMapper);
    		}
    	}
		List<UMLOperation> removedOperationsToBeRemoved = new ArrayList<UMLOperation>();
		List<UMLOperation> addedOperationsToBeRemoved = new ArrayList<UMLOperation>();
		for(UMLOperation removedOperation : removedOperations) {
			for(UMLOperation addedOperation : addedOperations) {
				if(removedOperation.equalsIgnoringVisibility(addedOperation)) {
					UMLOperationBodyMapper operationBodyMapper = new UMLOperationBodyMapper(removedOperation, addedOperation, this);
					UMLOperationDiff operationSignatureDiff = new UMLOperationDiff(removedOperation, addedOperation, operationBodyMapper.getMappings());
					refactorings.addAll(operationSignatureDiff.getRefactorings());
					this.addOperationBodyMapper(operationBodyMapper);
					removedOperationsToBeRemoved.add(removedOperation);
					addedOperationsToBeRemoved.add(addedOperation);
				}
				else if(removedOperation.equalsIgnoringNameCase(addedOperation)) {
					UMLOperationBodyMapper operationBodyMapper = new UMLOperationBodyMapper(removedOperation, addedOperation, this);
					UMLOperationDiff operationSignatureDiff = new UMLOperationDiff(removedOperation, addedOperation, operationBodyMapper.getMappings());
					refactorings.addAll(operationSignatureDiff.getRefactorings());
					if(!removedOperation.getName().equals(addedOperation.getName()) &&
							!(removedOperation.isConstructor() && addedOperation.isConstructor())) {
						RenameOperationRefactoring rename = new RenameOperationRefactoring(operationBodyMapper);
						refactorings.add(rename);
					}
					this.addOperationBodyMapper(operationBodyMapper);
					removedOperationsToBeRemoved.add(removedOperation);
					addedOperationsToBeRemoved.add(addedOperation);
				}
			}
		}
		removedOperations.removeAll(removedOperationsToBeRemoved);
		addedOperations.removeAll(addedOperationsToBeRemoved);
	}

	protected void checkForAttributeChanges() {
		for(Iterator<UMLAttribute> removedAttributeIterator = removedAttributes.iterator(); removedAttributeIterator.hasNext();) {
			UMLAttribute removedAttribute = removedAttributeIterator.next();
			for(Iterator<UMLAttribute> addedAttributeIterator = addedAttributes.iterator(); addedAttributeIterator.hasNext();) {
				UMLAttribute addedAttribute = addedAttributeIterator.next();
				if(removedAttribute.getName().equals(addedAttribute.getName())) {
					UMLAttributeDiff attributeDiff = new UMLAttributeDiff(removedAttribute, addedAttribute, getOperationBodyMapperList());
					refactorings.addAll(attributeDiff.getRefactorings());
					addedAttributeIterator.remove();
					removedAttributeIterator.remove();
					attributeDiffList.add(attributeDiff);
					break;
				}
			}
		}
	}

	private boolean containsMapperForOperation(UMLOperation operation) {
		for(UMLOperationBodyMapper mapper : getOperationBodyMapperList()) {
			if(mapper.getOperation1().equalsQualified(operation)) {
				return true;
			}
		}
		return false;
	}

	public boolean matches(String className) {
		return this.className.equals(className);
	}

	public boolean matches(UMLType type) {
		return this.className.endsWith("." + type.getClassType());
	}

	public List<Refactoring> getRefactorings() {
		List<Refactoring> refactorings = new ArrayList<Refactoring>(this.refactorings);
		for(UMLOperationBodyMapper mapper : operationBodyMapperList) {
			UMLOperationDiff operationSignatureDiff = new UMLOperationDiff(mapper.getOperation1(), mapper.getOperation2(), mapper.getMappings());
			refactorings.addAll(operationSignatureDiff.getRefactorings());
			processMapperRefactorings(mapper, refactorings);
		}
		refactorings.addAll(inferAttributeMergesAndSplits(renameMap, refactorings));
		for(MergeVariableReplacement merge : mergeMap.keySet()) {
			Set<UMLAttribute> mergedAttributes = new LinkedHashSet<UMLAttribute>();
			Set<VariableDeclaration> mergedVariables = new LinkedHashSet<VariableDeclaration>();
			for(String mergedVariable : merge.getMergedVariables()) {
				UMLAttribute a1 = findAttributeInOriginalClass(mergedVariable);
				if(a1 != null) {
					mergedAttributes.add(a1);
					mergedVariables.add(a1.getVariableDeclaration());
				}
			}
			UMLAttribute a2 = findAttributeInNextClass(merge.getAfter());
			Set<CandidateMergeVariableRefactoring> set = mergeMap.get(merge);
			for(CandidateMergeVariableRefactoring candidate : set) {
				if(mergedVariables.size() > 1 && mergedVariables.size() == merge.getMergedVariables().size() && a2 != null) {
					MergeAttributeRefactoring ref = new MergeAttributeRefactoring(mergedVariables, a2.getVariableDeclaration(), getOriginalClassName(), getNextClassName(), set);
					if(!refactorings.contains(ref)) {
						refactorings.add(ref);
						break;//it's not necessary to repeat the same process for all candidates in the set
					}
				}
				else {
					candidate.setMergedAttributes(mergedAttributes);
					candidate.setNewAttribute(a2);
					candidateAttributeMerges.add(candidate);
				}
			}
		}
		for(SplitVariableReplacement split : splitMap.keySet()) {
			Set<UMLAttribute> splitAttributes = new LinkedHashSet<UMLAttribute>();
			Set<VariableDeclaration> splitVariables = new LinkedHashSet<VariableDeclaration>();
			for(String splitVariable : split.getSplitVariables()) {
				UMLAttribute a2 = findAttributeInNextClass(splitVariable);
				if(a2 != null) {
					splitAttributes.add(a2);
					splitVariables.add(a2.getVariableDeclaration());
				}
			}
			UMLAttribute a1 = findAttributeInOriginalClass(split.getBefore());
			Set<CandidateSplitVariableRefactoring> set = splitMap.get(split);
			for(CandidateSplitVariableRefactoring candidate : set) {
				if(splitVariables.size() > 1 && splitVariables.size() == split.getSplitVariables().size() && a1 != null) {
					SplitAttributeRefactoring ref = new SplitAttributeRefactoring(a1.getVariableDeclaration(), splitVariables, getOriginalClassName(), getNextClassName(), set);
					if(!refactorings.contains(ref)) {
						refactorings.add(ref);
						break;//it's not necessary to repeat the same process for all candidates in the set
					}
				}
				else {
					candidate.setSplitAttributes(splitAttributes);
					candidate.setOldAttribute(a1);
					candidateAttributeSplits.add(candidate);
				}
			}
		}
		Set<Replacement> renames = renameMap.keySet();
		Set<Replacement> allConsistentRenames = new LinkedHashSet<Replacement>();
		Set<Replacement> allInconsistentRenames = new LinkedHashSet<Replacement>();
		Map<String, Set<String>> aliasedAttributesInOriginalClass = originalClass.aliasedAttributes();
		Map<String, Set<String>> aliasedAttributesInNextClass = nextClass.aliasedAttributes();
		ConsistentReplacementDetector.updateRenames(allConsistentRenames, allInconsistentRenames, renames,
				aliasedAttributesInOriginalClass, aliasedAttributesInNextClass);
		allConsistentRenames.removeAll(allInconsistentRenames);
		for(Replacement pattern : allConsistentRenames) {
			UMLAttribute a1 = findAttributeInOriginalClass(pattern.getBefore());
			UMLAttribute a2 = findAttributeInNextClass(pattern.getAfter());
			Set<CandidateAttributeRefactoring> set = renameMap.get(pattern);
			for(CandidateAttributeRefactoring candidate : set) {
				if(candidate.getOriginalVariableDeclaration() == null && candidate.getRenamedVariableDeclaration() == null) {
					if(a1 != null && a2 != null) {
						if((!originalClass.containsAttributeWithName(pattern.getAfter()) || cyclicRename(renameMap, pattern)) &&
								(!nextClass.containsAttributeWithName(pattern.getBefore()) || cyclicRename(renameMap, pattern)) &&
								!inconsistentAttributeRename(pattern, aliasedAttributesInOriginalClass, aliasedAttributesInNextClass) &&
								!attributeMerged(a1, a2, refactorings) && !attributeSplit(a1, a2, refactorings)) {
							UMLAttributeDiff attributeDiff = new UMLAttributeDiff(a1, a2, operationBodyMapperList);
							Set<Refactoring> attributeDiffRefactorings = attributeDiff.getRefactorings(set);
							if(!refactorings.containsAll(attributeDiffRefactorings)) {
								refactorings.addAll(attributeDiffRefactorings);
								break;//it's not necessary to repeat the same process for all candidates in the set
							}
						}
					}
					else {
						candidate.setOriginalAttribute(a1);
						candidate.setRenamedAttribute(a2);
						if(a1 != null)
							candidate.setOriginalVariableDeclaration(a1.getVariableDeclaration());
						if(a2 != null)
							candidate.setRenamedVariableDeclaration(a2.getVariableDeclaration());
						candidateAttributeRenames.add(candidate);
					}
				}
				else if(candidate.getOriginalVariableDeclaration() != null) {
					if(a2 != null) {
						RenameVariableRefactoring ref = new RenameVariableRefactoring(
								candidate.getOriginalVariableDeclaration(), a2.getVariableDeclaration(),
								candidate.getOperationBefore(), candidate.getOperationAfter(), candidate.getAttributeReferences());
						if(!refactorings.contains(ref)) {
							refactorings.add(ref);
							if(!candidate.getOriginalVariableDeclaration().getType().equals(a2.getVariableDeclaration().getType()) ||
									!candidate.getOriginalVariableDeclaration().getType().equalsQualified(a2.getVariableDeclaration().getType())) {
								ChangeVariableTypeRefactoring refactoring = new ChangeVariableTypeRefactoring(candidate.getOriginalVariableDeclaration(), a2.getVariableDeclaration(),
										candidate.getOperationBefore(), candidate.getOperationAfter(), candidate.getAttributeReferences());
								refactoring.addRelatedRefactoring(ref);
								refactorings.add(refactoring);
							}
						}
					}
					else {
						//field is declared in a superclass or outer class
						candidateAttributeRenames.add(candidate);
					}
				}
				else if(candidate.getRenamedVariableDeclaration() != null) {
					//inline field
				}
			}
		}
		return refactorings;
	}
}
