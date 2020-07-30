package gr.uom.java.xmi;

import java.util.Map;

import gr.uom.java.xmi.decomposition.AbstractCall;
import gr.uom.java.xmi.diff.CodeRange;

public interface LocationInfoProvider {
	public LocationInfo getLocationInfo();
	public CodeRange codeRange();
	int argumentIntersectionSize(AbstractCall call, Map<String, String> parameterToArgumentMap);
}
