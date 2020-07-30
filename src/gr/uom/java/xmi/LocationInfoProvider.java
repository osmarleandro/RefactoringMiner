package gr.uom.java.xmi;

import gr.uom.java.xmi.decomposition.AbstractExpression;
import gr.uom.java.xmi.diff.CodeRange;

public interface LocationInfoProvider {
	public LocationInfo getLocationInfo();
	public CodeRange codeRange();
	AbstractExpression getExpression();
}
