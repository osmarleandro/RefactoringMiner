package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.VariableDeclaration;

public interface IVariableDeclarationReplacement {

	VariableDeclaration getVariableDeclaration1();

	VariableDeclaration getVariableDeclaration2();

	UMLOperation getOperation1();

	UMLOperation getOperation2();

	Replacement getVariableNameReplacement();

	int hashCode();

	boolean equals(Object obj);

}