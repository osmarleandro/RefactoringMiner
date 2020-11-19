package gr.uom.java.xmi;

import java.util.List;

public interface IUMLJavadoc {

	void addTag(UMLTagElement tag);

	List<UMLTagElement> getTags();

	boolean contains(String s);

	boolean containsIgnoreCase(String s);

}