package gr.uom.java.xmi.diff;

public interface IRenamePattern {

	String getBefore();

	String getAfter();

	String toString();

	boolean equals(Object o);

	int hashCode();

	IRenamePattern reverse();

}