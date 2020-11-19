package gr.uom.java.xmi.decomposition;

import gr.uom.java.xmi.LocationInfo;

public interface IVariableScope {

	int hashCode();

	boolean equals(Object obj);

	String toString();

	boolean subsumes(LocationInfo other);

}