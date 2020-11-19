package gr.uom.java.xmi;

import java.util.List;

public interface IUMLTagElement {

	void addFragment(String fragment);

	String getTagName();

	List<String> getFragments();

	boolean contains(String s);

	boolean containsIgnoreCase(String s);

}