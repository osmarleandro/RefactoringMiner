package gr.uom.java.xmi;

import java.util.Map;

import gr.uom.java.xmi.decomposition.AbstractExpression;
import gr.uom.java.xmi.diff.CodeRange;

public interface IUMLAnnotation {

	String getTypeName();

	AbstractExpression getValue();

	Map<String, AbstractExpression> getMemberValuePairs();

	boolean isMarkerAnnotation();

	boolean isSingleMemberAnnotation();

	boolean isNormalAnnotation();

	String toString();

	LocationInfo getLocationInfo();

	CodeRange codeRange();

	int hashCode();

	boolean equals(Object obj);

}