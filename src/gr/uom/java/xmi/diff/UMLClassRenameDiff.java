package gr.uom.java.xmi.diff;

import java.util.List;
import java.util.Map;
import java.util.Set;

import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper;
import gr.uom.java.xmi.decomposition.replacement.Replacement;

public class UMLClassRenameDiff extends UMLClassBaseDiff {
	
	public UMLClassRenameDiff(UMLClass originalClass, UMLClass renamedClass, UMLModelDiff modelDiff) {
		super(originalClass, renamedClass, modelDiff);
	}

	public UMLClass getRenamedClass() {
		return nextClass;
	}

	public boolean samePackage() {
		return originalClass.getPackageName().equals(nextClass.getPackageName());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ");
		sb.append(originalClass.getName());
		sb.append(" was renamed to ");
		sb.append(nextClass.getName());
		sb.append("\n");
		return sb.toString();
	}

	private boolean inconsistentAttributeRename(Replacement pattern, Map<String, Set<String>> aliasedAttributesInOriginalClass, Map<String, Set<String>> aliasedAttributesInNextClass) {
		for(String key : aliasedAttributesInOriginalClass.keySet()) {
			if(aliasedAttributesInOriginalClass.get(key).contains(pattern.getBefore())) {
				return false;
			}
		}
		for(String key : aliasedAttributesInNextClass.keySet()) {
			if(aliasedAttributesInNextClass.get(key).contains(pattern.getAfter())) {
				return false;
			}
		}
		int counter = 0;
		int allCases = 0;
		for(UMLOperationBodyMapper mapper : this.operationBodyMapperList) {
			List<String> allVariables1 = mapper.getOperation1().getAllVariables();
			List<String> allVariables2 = mapper.getOperation2().getAllVariables();
			for(UMLOperationBodyMapper nestedMapper : mapper.getChildMappers()) {
				allVariables1.addAll(nestedMapper.getOperation1().getAllVariables());
				allVariables2.addAll(nestedMapper.getOperation2().getAllVariables());
			}
			boolean variables1contains = (allVariables1.contains(pattern.getBefore()) &&
					!mapper.getOperation1().getParameterNameList().contains(pattern.getBefore())) ||
					allVariables1.contains("this."+pattern.getBefore());
			boolean variables2Contains = (allVariables2.contains(pattern.getAfter()) &&
					!mapper.getOperation2().getParameterNameList().contains(pattern.getAfter())) ||
					allVariables2.contains("this."+pattern.getAfter());
			if(variables1contains && !variables2Contains) {	
				counter++;
			}
			if(variables2Contains && !variables1contains) {
				counter++;
			}
			if(variables1contains || variables2Contains) {
				allCases++;
			}
		}
		double percentage = (double)counter/(double)allCases;
		if(percentage > 0.5)
			return true;
		return false;
	}
}
