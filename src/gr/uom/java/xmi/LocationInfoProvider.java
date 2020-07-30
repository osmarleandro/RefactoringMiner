package gr.uom.java.xmi;

import org.eclipse.jdt.core.dom.Type;

import gr.uom.java.xmi.diff.CodeRange;

public interface LocationInfoProvider {
	public LocationInfo getLocationInfo();
	public CodeRange codeRange();
	Type extractType(org.eclipse.jdt.core.dom.VariableDeclaration variableDeclaration);
}
