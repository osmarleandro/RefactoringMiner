package gr.uom.java.xmi;

import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;

import gr.uom.java.xmi.diff.CodeRange;

public interface LocationInfoProvider {
	public LocationInfo getLocationInfo();
	public CodeRange codeRange();
	void setAstNode(AnonymousClassDeclaration node);
}
