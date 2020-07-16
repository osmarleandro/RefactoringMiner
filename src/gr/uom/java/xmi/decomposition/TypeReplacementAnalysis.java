package gr.uom.java.xmi.decomposition;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import gr.uom.java.xmi.LocationInfo.CodeElementType;
import gr.uom.java.xmi.decomposition.replacement.Replacement;
import gr.uom.java.xmi.decomposition.replacement.Replacement.ReplacementType;
import gr.uom.java.xmi.diff.ChangeVariableTypeRefactoring;

public class TypeReplacementAnalysis {
	private Set<AbstractCodeMapping> mappings;
	private Set<ChangeVariableTypeRefactoring> changedTypes = new LinkedHashSet<ChangeVariableTypeRefactoring>();

	public TypeReplacementAnalysis(Set<AbstractCodeMapping> mappings) {
		this.mappings = mappings;
		findTypeChanges();
	}

	public Set<ChangeVariableTypeRefactoring> getChangedTypes() {
		return changedTypes;
	}

	private void findTypeChanges() {
		for(AbstractCodeMapping mapping : mappings) {
			AbstractCodeFragment fragment1 = mapping.getFragment1();
			AbstractCodeFragment fragment2 = mapping.getFragment2();
			for(Replacement replacement : mapping.getReplacements()) {
				if(replacement.getType().equals(ReplacementType.TYPE)) {
					List<VariableDeclaration> declarations1 = fragment1.getVariableDeclarations();
					List<VariableDeclaration> declarations2 = fragment2.getVariableDeclarations();
					for(VariableDeclaration declaration1 : declarations1) {
						for(VariableDeclaration declaration2 : declarations2) {
							if(declaration1.getVariableName().equals(declaration2.getVariableName()) &&
									(!declaration1.getInitializer().getType(this).equals(declaration2.getInitializer().getType(this)) || !declaration1.getInitializer().getType(this).equalsQualified(declaration2.getInitializer().getType(this))) &&
									!containsVariableDeclarationWithSameNameAndType(declaration1, declarations2)) {
								ChangeVariableTypeRefactoring ref = new ChangeVariableTypeRefactoring(declaration1, declaration2, mapping.getOperation1(), mapping.getOperation2(),
										VariableReferenceExtractor.findReferences(declaration1, declaration2, mappings));
								changedTypes.add(ref);
								break;
							}
						}
					}
				}
			}
			if(fragment1.getLocationInfo().getCodeElementType().equals(CodeElementType.ENHANCED_FOR_STATEMENT) &&
					fragment2.getLocationInfo().getCodeElementType().equals(CodeElementType.ENHANCED_FOR_STATEMENT)) {
				List<VariableDeclaration> declarations1 = fragment1.getVariableDeclarations();
				List<VariableDeclaration> declarations2 = fragment2.getVariableDeclarations();
				for(VariableDeclaration declaration1 : declarations1) {
					for(VariableDeclaration declaration2 : declarations2) {
						if(declaration1.getVariableName().equals(declaration2.getVariableName()) &&
								(!declaration1.getInitializer().getType(this).equals(declaration2.getInitializer().getType(this)) || !declaration1.getInitializer().getType(this).equalsQualified(declaration2.getInitializer().getType(this))) &&
								!containsVariableDeclarationWithSameNameAndType(declaration1, declarations2)) {
							ChangeVariableTypeRefactoring ref = new ChangeVariableTypeRefactoring(declaration1, declaration2, mapping.getOperation1(), mapping.getOperation2(),
									VariableReferenceExtractor.findReferences(declaration1, declaration2, mappings));
							changedTypes.add(ref);
							break;
						}
					}
				}
			}
		}
	}

	private boolean containsVariableDeclarationWithSameNameAndType(VariableDeclaration declaration, List<VariableDeclaration> declarations) {
		for(VariableDeclaration d : declarations) {
			if(d.getVariableName().equals(declaration.getVariableName()) && d.getInitializer().getType(this).equals(declaration.getInitializer().getType(this)) && d.getInitializer().getType(this).equalsQualified(declaration.getInitializer().getType(this))) {
				return true;
			}
		}
		return false;
	}
}
