package gr.uom.java.xmi;

import java.util.ArrayList;
import java.util.List;

import gr.uom.java.xmi.decomposition.VariableDeclaration;

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

	public VariableDeclaration getVariableDeclaration(UMLOperation umlOperation, String variableName) {
		if(umlOperation.operationBody != null)
			return umlOperation.operationBody.getVariableDeclaration(variableName);
		return null;
	}
}
