package gr.uom.java.xmi;

import gr.uom.java.xmi.diff.CodeRange;

public interface LocationInfoProvider {
	public ILocationInfo getLocationInfo();
	public CodeRange codeRange();
}
