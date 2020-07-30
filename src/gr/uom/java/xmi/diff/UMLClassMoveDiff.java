package gr.uom.java.xmi.diff;

import java.util.LinkedHashSet;
import java.util.Set;

import org.refactoringminer.api.Refactoring;

import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper;

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

	private void checkForInconsistentVariableRenames(UMLOperationBodyMapper mapper) {
		if(mapper.getChildMappers().size() > 1) {
			Set<Refactoring> refactoringsToBeRemoved = new LinkedHashSet<Refactoring>();
			for(Refactoring r : refactorings) {
				if(r instanceof RenameVariableRefactoring) {
					RenameVariableRefactoring rename = (RenameVariableRefactoring)r;
					Set<AbstractCodeMapping> references = rename.getVariableReferences();
					for(AbstractCodeMapping reference : references) {
						if(reference.getFragment1().getVariableDeclarations().size() > 0 && !reference.isExact()) {
							Set<AbstractCodeMapping> allMappingsForReference = new LinkedHashSet<AbstractCodeMapping>();
							for(UMLOperationBodyMapper childMapper : mapper.getChildMappers()) {
								for(AbstractCodeMapping mapping : childMapper.getMappings()) {
									if(mapping.getFragment1().equals(reference.getFragment1())) {
										allMappingsForReference.add(mapping);
										break;
									}
								}
							}
							if(allMappingsForReference.size() > 1) {
								for(AbstractCodeMapping mapping : allMappingsForReference) {
									if(!mapping.equals(reference) && mapping.isExact()) {
										refactoringsToBeRemoved.add(rename);
										break;
									}
								}
							}
						}
					}
				}
			}
			refactorings.removeAll(refactoringsToBeRemoved);
		}
	}
}
