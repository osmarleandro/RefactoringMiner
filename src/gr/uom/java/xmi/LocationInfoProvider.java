package gr.uom.java.xmi;

import java.util.List;
import java.util.Set;

import gr.uom.java.xmi.decomposition.AbstractCall;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper;
import gr.uom.java.xmi.decomposition.replacement.Replacement;
import gr.uom.java.xmi.diff.CodeRange;

public interface LocationInfoProvider {
	public LocationInfo getLocationInfo();
	public CodeRange codeRange();
	boolean renamedWithIdenticalExpressionAndDifferentNumberOfArguments(AbstractCall call, Set<Replacement> replacements, double distance,
			List<UMLOperationBodyMapper> lambdaMappers);
}
