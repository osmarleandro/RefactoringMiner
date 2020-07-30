package gr.uom.java.xmi;

import gr.uom.java.xmi.LocationInfo.CodeElementType;
import gr.uom.java.xmi.diff.CodeRange;

public interface LocationInfoProvider {
	public LocationInfo getLocationInfo();
	public CodeRange codeRange();
	CodeElementType extractVariableDeclarationType(org.eclipse.jdt.core.dom.VariableDeclaration variableDeclaration);
}
