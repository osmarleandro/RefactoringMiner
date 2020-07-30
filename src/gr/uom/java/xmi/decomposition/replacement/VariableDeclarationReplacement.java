package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.VariableDeclaration;

public class VariableDeclarationReplacement extends Replacement {

	VariableDeclaration v1;
	VariableDeclaration v2;
	private UMLOperation operation1;
	private UMLOperation operation2;
	
	public VariableDeclarationReplacement(VariableDeclaration v1, VariableDeclaration v2,
			UMLOperation operation1, UMLOperation operation2) {
		super(v1.toString() + " | " + v1.getScope(), v2.toString() + " | " + v2.getScope(), ReplacementType.VARIABLE_DECLARATION);
		this.v1 = v1;
		this.v2 = v2;
		this.operation1 = operation1;
		this.operation2 = operation2;
	}

	public VariableDeclaration getVariableDeclaration1() {
		return v1;
	}

	public VariableDeclaration getVariableDeclaration2() {
		return v2;
	}

	public UMLOperation getOperation1() {
		return operation1;
	}

	public UMLOperation getOperation2() {
		return operation2;
	}

	public Replacement getVariableNameReplacement() {
		return new Replacement(v1.getVariableName(), v2.getVariableName(), ReplacementType.VARIABLE_NAME);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
		result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
		return result;
	}
}
