package gr.uom.java.xmi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

	public Map<String, UMLType> variableTypeMap(UMLOperation umlOperation) {
		Map<String, UMLType> variableTypeMap = new LinkedHashMap<String, UMLType>();
		for(UMLParameter parameter : umlOperation.parameters) {
			if(!parameter.getKind().equals("return"))
				variableTypeMap.put(parameter.getName(), parameter.getType());
		}
		for(VariableDeclaration declaration : umlOperation.getAllVariableDeclarations()) {
			variableTypeMap.put(declaration.getVariableName(), declaration.getType());
		}
		return variableTypeMap;
	}
}
