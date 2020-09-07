package gr.uom.java.xmi;

import java.util.ArrayList;
import java.util.List;

public class UMLJavadoc {
	private List<UMLTagElement_RENAMED> tags;

	public UMLJavadoc() {
		this.tags = new ArrayList<UMLTagElement_RENAMED>();
	}
	
	public void addTag(UMLTagElement_RENAMED tag) {
		tags.add(tag);
	}

	public List<UMLTagElement_RENAMED> getTags() {
		return tags;
	}

	public boolean contains(String s) {
		for(UMLTagElement_RENAMED tag : tags) {
			if(tag.contains(s)) {
				return true;
			}
		}
		return false;
	}

	public boolean containsIgnoreCase(String s) {
		for(UMLTagElement_RENAMED tag : tags) {
			if(tag.containsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}
}
