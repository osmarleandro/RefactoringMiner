package gr.uom.java.xmi;

import gr.uom.java.xmi.diff.CodeRange;

public interface LocationInfoProvider {
	public LocationInfo_RENAMED getLocationInfo();
	public CodeRange codeRange();
}
