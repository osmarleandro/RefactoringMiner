package gr.uom.java.xmi.decomposition.replacement;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ConsistentReplacementDetector {

	public static <T extends Replacement> void updateRenames(
			Set<T> allConsistentRenames,
			Set<T> allInconsistentRenames,
			Set<T> renames) {
		for(T newRename : renames) {
			Set<T> inconsistentRenames1 = new LinkedHashSet<T>();
			for(T rename : allConsistentRenames) {
				if(rename.getBefore().equals(newRename.getBefore()) && !rename.getAfter().equals(newRename.getAfter())) {
					inconsistentRenames1.add(rename);
				}
				else if(!rename.getBefore().equals(newRename.getBefore()) && rename.getAfter().equals(newRename.getAfter())) {
					inconsistentRenames1.add(rename);
				}
			}
			Set<T> inconsistentRenames = inconsistentRenames1;
			if(inconsistentRenames.isEmpty()) {
				allConsistentRenames.add(newRename);
			}
			else {
				allInconsistentRenames.addAll(inconsistentRenames);
				allInconsistentRenames.add(newRename);
			}
		}
	}


	public static <T extends Replacement> void updateRenames(
			Set<T> allConsistentRenames,
			Set<T> allInconsistentRenames,
			Set<T> renames,
			Map<String, Set<String>> aliasedAttributesInOriginalClass,
			Map<String, Set<String>> aliasedAttributesInNextClass) {
		for(T newRename : renames) {
			Set<T> inconsistentRenames1 = new LinkedHashSet<T>();
			for(T rename : allConsistentRenames) {
				if(rename.getBefore().equals(newRename.getBefore()) && !rename.getAfter().equals(newRename.getAfter())) {
					inconsistentRenames1.add(rename);
				}
				else if(!rename.getBefore().equals(newRename.getBefore()) && rename.getAfter().equals(newRename.getAfter())) {
					inconsistentRenames1.add(rename);
				}
			}
			Set<T> inconsistentRenames = inconsistentRenames1;
			filter(inconsistentRenames, aliasedAttributesInOriginalClass, aliasedAttributesInNextClass);
			if(inconsistentRenames.isEmpty()) {
				allConsistentRenames.add(newRename);
			}
			else {
				allInconsistentRenames.addAll(inconsistentRenames);
				allInconsistentRenames.add(newRename);
			}
		}
	}
	
	private static <T extends Replacement> Set<T> filter(Set<T> inconsistentRenames,
			Map<String, Set<String>> aliasedAttributesInOriginalClass,
			Map<String, Set<String>> aliasedAttributesInNextClass) {
		Set<T> renamesToBeRemoved = new LinkedHashSet<T>();
		for(String key : aliasedAttributesInOriginalClass.keySet()) {
			Set<String> aliasedAttributes = aliasedAttributesInOriginalClass.get(key);
			for(T r : inconsistentRenames) {
				if(aliasedAttributes.contains(r.getBefore())) {
					renamesToBeRemoved.add(r);
				}
			}
		}
		for(String key : aliasedAttributesInNextClass.keySet()) {
			Set<String> aliasedAttributes = aliasedAttributesInNextClass.get(key);
			for(T r : inconsistentRenames) {
				if(aliasedAttributes.contains(r.getAfter())) {
					renamesToBeRemoved.add(r);
				}
			}
		}
		inconsistentRenames.removeAll(renamesToBeRemoved);
		return inconsistentRenames;
	}
}
