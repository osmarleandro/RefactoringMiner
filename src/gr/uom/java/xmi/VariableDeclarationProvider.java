package gr.uom.java.xmi;

import java.util.List;

import gr.uom.java.xmi.decomposition.VariableDeclaration;

public interface VariableDeclarationProvider {
	public VariableDeclaration getVariableDeclaration();

	List<UMLAnnotation> getAnnotations();
}
