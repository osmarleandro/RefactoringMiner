package gr.uom.java.xmi;

import java.util.ArrayList;
import java.util.List;

public class UMLTypeParameter {
	private String name;
	private List<UMLType> typeBounds;
	private List<UMLAnnotation> annotations;

	public UMLTypeParameter(String name) {
		this.name = name;
		this.typeBounds = new ArrayList<UMLType>();
		this.annotations = new ArrayList<UMLAnnotation>();
	}

	public String getName() {
		return name;
	}

	public List<UMLType> getTypeBounds() {
		return typeBounds;
	}

	public void addTypeBound(UMLType type) {
		typeBounds.add(type);
	}

	public List<UMLAnnotation> getAnnotations() {
		return annotations;
	}

	public void addAnnotation(UMLAnnotation annotation) {
		annotations.add(annotation);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UMLTypeParameter other = (UMLTypeParameter) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (typeBounds == null) {
			if (other.typeBounds != null)
				return false;
		} else if (!typeBounds.equals(other.typeBounds))
			return false;
		return true;
	}
}
