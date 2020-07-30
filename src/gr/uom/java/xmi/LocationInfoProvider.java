package gr.uom.java.xmi;

import java.util.Map;
import java.util.Set;

import gr.uom.java.xmi.decomposition.AbstractCall;
import gr.uom.java.xmi.decomposition.replacement.Replacement;
import gr.uom.java.xmi.diff.CodeRange;

public interface LocationInfoProvider {
	public LocationInfo getLocationInfo();
	public CodeRange codeRange();
	boolean allArgumentsReplaced(AbstractCall call, Set<Replacement> replacements, Map<String, String> parameterToArgumentMap);
}
