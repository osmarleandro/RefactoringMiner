package gr.uom.java.xmi;

import java.util.ArrayList;
import java.util.List;

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

	public boolean equals(UMLOperation umlOperation, Object o) {
		if(umlOperation == o) {
	        return true;
	    }
		
		if(o instanceof UMLOperation) {
			UMLOperation operation = (UMLOperation)o;
			boolean thisEmptyBody = umlOperation.getBody() == null || umlOperation.hasEmptyBody();
			boolean otherEmptyBody = operation.getBody() == null || operation.hasEmptyBody();
			return umlOperation.className.equals(operation.className) &&
				umlOperation.name.equals(operation.name) &&
				umlOperation.visibility.equals(operation.visibility) &&
				umlOperation.isAbstract == operation.isAbstract &&
				thisEmptyBody == otherEmptyBody &&
				umlOperation.getParameterTypeList().equals(operation.getParameterTypeList()) &&
				umlOperation.equalTypeParameters(operation);
		}
		return false;
	}
}
