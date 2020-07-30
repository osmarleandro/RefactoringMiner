package gr.uom.java.xmi;

import org.eclipse.jdt.core.dom.ASTNode;

import gr.uom.java.xmi.diff.CodeRange;

public interface LocationInfoProvider {
	public LocationInfo getLocationInfo();
	public CodeRange codeRange();
	ASTNode getScopeNode(org.eclipse.jdt.core.dom.VariableDeclaration variableDeclaration);
}
