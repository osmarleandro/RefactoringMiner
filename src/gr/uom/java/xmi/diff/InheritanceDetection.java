package gr.uom.java.xmi.diff;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class InheritanceDetection {
	private Map<String, LinkedHashSet<String>> subclassMap;

	public InheritanceDetection(UMLModelDiff modelDiff) {
		this.subclassMap = new LinkedHashMap<String, LinkedHashSet<String>>();
		modelDiff.generateNewInheritanceHierarchies(this);
	}

	void addSubclassToSuperclass(String superclass, String subclass) {
		if(subclassMap.containsKey(superclass)) {
			LinkedHashSet<String> subclasses = subclassMap.get(superclass);
			subclasses.add(subclass);
		}
		else {
			LinkedHashSet<String> subclasses = new LinkedHashSet<String>();
			subclasses.add(subclass);
			subclassMap.put(superclass, subclasses);
		}
	}

	public Set<String> getRoots() {
		return subclassMap.keySet();
	}
}
