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

	public String toString(UMLOperation umlOperation) {
		StringBuilder sb = new StringBuilder();
		sb.append(umlOperation.visibility);
		sb.append(" ");
		if(umlOperation.isAbstract) {
			sb.append("abstract");
			sb.append(" ");
		}
		sb.append(umlOperation.name);
		UMLParameter returnParameter = umlOperation.getReturnParameter();
		List<UMLParameter> parameters = new ArrayList<UMLParameter>(umlOperation.parameters);
		parameters.remove(returnParameter);
		sb.append("(");
		for(int i=0; i<parameters.size(); i++) {
			UMLParameter parameter = parameters.get(i);
			if(parameter.getKind().equals("in")) {
				sb.append(parameter);
				if(i < parameters.size()-1)
					sb.append(", ");
			}
		}
		sb.append(")");
		if(returnParameter != null) {
			sb.append(" : ");
			sb.append(returnParameter.toString());
		}
		return sb.toString();
	}
}
