package gr.uom.java.xmi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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

	public Map<String, Set<String>> aliasedAttributes(UMLOperation umlOperation) {
		if(umlOperation.operationBody != null && umlOperation.isConstructor) {
			List<String> parameterNames = umlOperation.getParameterNameList();
			Map<String, Set<String>> map = umlOperation.operationBody.aliasedAttributes();
			Set<String> keysToBeRemoved = new LinkedHashSet<String>();
			for(String key : map.keySet()) {
				if(!parameterNames.contains(key)) {
					keysToBeRemoved.add(key);
				}
			}
			for(String key : keysToBeRemoved) {
				map.remove(key);
			}
			return map;
		}
		return new LinkedHashMap<String, Set<String>>();
	}
}
