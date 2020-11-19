package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.replacement.Replacement.ReplacementType;

public interface IReplacement {

	String getBefore();

	String getAfter();

	ReplacementType getType();

	int hashCode();

	boolean equals(Object obj);

	String toString();

	double normalizedEditDistance();

	boolean involvesVariable();

}