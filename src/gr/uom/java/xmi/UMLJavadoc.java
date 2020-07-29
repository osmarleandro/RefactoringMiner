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

	public boolean equals(UMLAttribute umlAttribute2, Object o) {
		if(umlAttribute2 == o) {
			return true;
		}
		
		if(o instanceof UMLAttribute) {
			UMLAttribute umlAttribute = (UMLAttribute)o;
			return umlAttribute2.name.equals(umlAttribute.name) &&
			umlAttribute2.visibility.equals(umlAttribute.visibility) &&
			umlAttribute2.type.equals(umlAttribute.type);
		}
		return false;
	}
}
