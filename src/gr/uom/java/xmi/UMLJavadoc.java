package gr.uom.java.xmi;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

	public boolean implementsInterface(UMLClass umlClass, Set<UMLType> interfaces) {
		for(UMLType type : interfaces) {
			if(umlClass.implementedInterfaces.contains(type))
				return true;
		}
		return false;
	}
}
