package gr.uom.java.xmi;

import gr.uom.java.xmi.decomposition.AbstractCall;
import gr.uom.java.xmi.diff.CodeRange;

public interface LocationInfoProvider {
	public LocationInfo getLocationInfo();
	public CodeRange codeRange();
	double normalizedNameDistance(AbstractCall call);
}
