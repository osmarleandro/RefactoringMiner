package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLGeneralization;
import gr.uom.java.xmi.UMLRealization;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InheritanceDetection {
	private Map<String, LinkedHashSet<String>> subclassMap;

	public InheritanceDetection(UMLModelDiff modelDiff) {
		this.subclassMap = new LinkedHashMap<String, LinkedHashSet<String>>();
		generateNewInheritanceHierarchies(modelDiff);
	}

	private void generateNewInheritanceHierarchies(UMLModelDiff modelDiff) {
		List<UMLGeneralization> addedGeneralizations = modelDiff.getAddedGeneralizations();
		for(UMLGeneralization generalization : addedGeneralizations) {
			String superclass = generalization.getParent();
			String subclass = generalization.getChild().getName();
			if(modelDiff.getAddedClass(superclass) != null && modelDiff.getAddedClass(subclass) != null)
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
		List<UMLRealization> addedRealizations = modelDiff.getAddedRealizations();
		for(UMLRealization realization : addedRealizations) {
			String supplier = realization.getSupplier();
			String client = realization.getClient().getName();
			if(modelDiff.getAddedClass(supplier) != null && modelDiff.getAddedClass(client) != null)
				if(subclassMap.containsKey(supplier)) {
					LinkedHashSet<String> subclasses = subclassMap.get(supplier);
					subclasses.add(client);
				}
				else {
					LinkedHashSet<String> subclasses = new LinkedHashSet<String>();
					subclasses.add(client);
					subclassMap.put(supplier, subclasses);
				}
		}
	}

	public Set<String> getRoots() {
		return subclassMap.keySet();
	}
}
