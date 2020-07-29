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

	public int hashCode(UMLOperation umlOperation) {
		final int prime = 31;
		int result = 1;
		boolean thisEmptyBody = umlOperation.getBody() == null || umlOperation.hasEmptyBody();
		result = prime * result + ((umlOperation.className == null) ? 0 : umlOperation.className.hashCode());
		result = prime * result + (umlOperation.isAbstract ? 1231 : 1237);
		result = prime * result + (thisEmptyBody ? 1231 : 1237);
		result = prime * result + ((umlOperation.name == null) ? 0 : umlOperation.name.hashCode());
		result = prime * result + ((umlOperation.getParameterTypeList() == null) ? 0 : umlOperation.getParameterTypeList().hashCode());
		result = prime * result + ((umlOperation.visibility == null) ? 0 : umlOperation.visibility.hashCode());
		result = prime * result + ((umlOperation.typeParameters == null) ? 0 : umlOperation.typeParameters.hashCode());
		return result;
	}
}
