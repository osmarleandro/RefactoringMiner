package gr.uom.java.xmi;

import gr.uom.java.xmi.diff.CodeRange_RENAMED;

public interface LocationInfoProvider {
	public LocationInfo getLocationInfo();
	public CodeRange_RENAMED codeRange();
}
