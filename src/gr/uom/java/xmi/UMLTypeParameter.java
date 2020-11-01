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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((typeBounds == null) ? 0 : typeBounds.hashCode());
		return result;
	}
}
