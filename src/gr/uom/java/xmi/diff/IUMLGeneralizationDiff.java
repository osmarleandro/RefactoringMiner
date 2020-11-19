package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLGeneralization;

public interface IUMLGeneralizationDiff {

	UMLGeneralization getRemovedGeneralization();

	UMLGeneralization getAddedGeneralization();

	String toString();

	int compareTo(IUMLGeneralizationDiff generalizationDiff);

}