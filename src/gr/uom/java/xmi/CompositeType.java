package gr.uom.java.xmi;

public class CompositeType extends UMLType {
	UMLType leftType;
	LeafType rightType;

	public CompositeType(UMLType leftType, LeafType rightType) {
		this.leftType = leftType;
		this.rightType = rightType;
	}

	public UMLType getLeftType() {
		return leftType;
	}

	public LeafType getRightType() {
		return rightType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((leftType == null) ? 0 : leftType.hashCode());
		result = prime * result + ((rightType == null) ? 0 : rightType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return leftType.equals(this, obj);
	}

	@Override
	public String toString() {
		return leftType.toString() + "." + rightType.toString();
	}

	@Override
	public String toQualifiedString() {
		return leftType.toQualifiedString() + "." + rightType.toQualifiedString();
	}

	@Override
	public String getClassType() {
		return rightType.getClassType();
	}
}
