package gr.uom.java.xmi;

import java.util.List;
import java.util.Map;

import gr.uom.java.xmi.decomposition.OperationInvocation;
import gr.uom.java.xmi.diff.CodeRange;

public interface LocationInfoProvider {
	public LocationInfo getLocationInfo();
	public CodeRange codeRange();
	Map<String, List<OperationInvocation>> getMethodInvocationMap();
}
