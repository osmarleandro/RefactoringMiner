package gr.uom.java.xmi;

import java.util.List;

import gr.uom.java.xmi.diff.CodeRange;

public interface LocationInfoProvider {
	public LocationInfo getLocationInfo();
	public CodeRange codeRange();
	List<UMLParameter> getParametersWithoutReturnType();
}
