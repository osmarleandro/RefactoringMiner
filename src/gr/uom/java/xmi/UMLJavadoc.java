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

	boolean isToString(UMLOperation umlOperation) {
		List<UMLType> parameterTypeList = umlOperation.getParameterTypeList();
		return umlOperation.getName().equals("toString") && umlOperation.getReturnParameter().getType().getClassType().equals("String") && parameterTypeList.size() == 0;
	}
}
