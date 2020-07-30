package gr.uom.java.xmi;

import java.util.Set;

import gr.uom.java.xmi.diff.CodeRange;

public interface LocationInfoProvider {
	public LocationInfo getLocationInfo();
	public CodeRange codeRange();
	boolean containsInitializerOfVariableDeclaration(Set<String> expressions);
}
