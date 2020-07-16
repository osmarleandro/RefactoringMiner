package gr.uom.java.xmi;

import java.util.ArrayList;
import java.util.List;

import gr.uom.java.xmi.decomposition.AnonymousClassDeclarationObject;

public class UMLJavadoc {
	private List<UMLTagElement> tags;

	public UMLJavadoc() {
		this.tags = new ArrayList<UMLTagElement>();
	}
	
	public void addTag(UMLTagElement tag) {
		tags.add(tag);
	}

	public List<UMLTagElement> getTags() {
		return tags;
	}

	public boolean contains(String s) {
		for(UMLTagElement tag : tags) {
			if(tag.contains(s)) {
				return true;
			}
		}
		return false;
	}

	public boolean containsIgnoreCase(String s) {
		for(UMLTagElement tag : tags) {
			if(tag.containsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}

	public List<UMLOperation> getOperationsInsideAnonymousClass(UMLOperation umlOperation, List<UMLAnonymousClass> allAddedAnonymousClasses) {
		List<UMLOperation> operationsInsideAnonymousClass = new ArrayList<UMLOperation>();
		if(umlOperation.operationBody != null) {
			List<AnonymousClassDeclarationObject> anonymousClassDeclarations = umlOperation.operationBody.getAllAnonymousClassDeclarations();
			for(AnonymousClassDeclarationObject anonymousClassDeclaration : anonymousClassDeclarations) {
				for(UMLAnonymousClass anonymousClass : allAddedAnonymousClasses) {
					if(anonymousClass.getLocationInfo().equals(anonymousClassDeclaration.getLocationInfo())) {
						operationsInsideAnonymousClass.addAll(anonymousClass.getOperations());
					}
				}
			}
		}
		return operationsInsideAnonymousClass;
	}
}
