package gr.uom.java.xmi.decomposition;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import gr.uom.java.xmi.LocationInfo.CodeElementType;

public class VariableReferenceExtractor {

	public static Set<AbstractCodeMapping_RENAMED> findReferences(VariableDeclaration declaration1, VariableDeclaration declaration2, Set<AbstractCodeMapping_RENAMED> mappings) {
		Set<AbstractCodeMapping_RENAMED> references = new LinkedHashSet<AbstractCodeMapping_RENAMED>();
		VariableScope scope1 = declaration1.getScope();
		VariableScope scope2 = declaration2.getScope();
		for(AbstractCodeMapping_RENAMED mapping : mappings) {
			AbstractCodeFragment fragment1 = mapping.getFragment1();
			AbstractCodeFragment fragment2 = mapping.getFragment2();
			if(scope1.subsumes(fragment1.getLocationInfo()) && scope2.subsumes(fragment2.getLocationInfo()) &&
					usesVariable(fragment1, declaration1) && usesVariable(fragment2, declaration2)) {
				references.add(mapping);
			}
		}
		return references;
	}

	private static boolean usesVariable(AbstractCodeFragment fragment, VariableDeclaration declaration) {
		List<String> variables = fragment.getVariables();
		return variables.contains(declaration.getVariableName()) ||
				(declaration.isAttribute() && variables.contains("this." + declaration.getVariableName()));
	}

	public static Set<AbstractCodeMapping_RENAMED> findReturnReferences(Set<AbstractCodeMapping_RENAMED> mappings) {
		Set<AbstractCodeMapping_RENAMED> references = new LinkedHashSet<AbstractCodeMapping_RENAMED>();
		for(AbstractCodeMapping_RENAMED mapping : mappings) {
			if(mapping.getFragment1().getLocationInfo().getCodeElementType().equals(CodeElementType.RETURN_STATEMENT) &&
					mapping.getFragment2().getLocationInfo().getCodeElementType().equals(CodeElementType.RETURN_STATEMENT)) {
				references.add(mapping);
			}
		}
		return references;
	}

	public static Set<AbstractCodeMapping_RENAMED> findReferences(VariableDeclaration declaration1, VariableDeclaration declaration2, List<UMLOperationBodyMapper> operationBodyMapperList) {
		Set<AbstractCodeMapping_RENAMED> references = new LinkedHashSet<AbstractCodeMapping_RENAMED>();
		for(UMLOperationBodyMapper mapper : operationBodyMapperList) {
			references.addAll(findReferences(declaration1, declaration2, mapper.getMappings()));
		}
		return references;
	}
}