package gr.uom.java.xmi.decomposition;

import java.util.List;
import java.util.Map;
import java.util.Set;

import gr.uom.java.xmi.LocationInfo;
import gr.uom.java.xmi.decomposition.AbstractCall.StatementCoverageType;
import gr.uom.java.xmi.decomposition.replacement.Replacement;
import gr.uom.java.xmi.diff.CodeRange;

public interface IAbstractCall {

	String getExpression();

	List<String> getArguments();

	LocationInfo getLocationInfo();

	StatementCoverageType getCoverage();

	boolean identicalName(IAbstractCall call);

	String getName();

	double normalizedNameDistance(IAbstractCall call);

	IAbstractCall update(String oldExpression, String newExpression);

	String actualString();

	boolean expressionIsNullOrThis();

	boolean identicalExpression(IAbstractCall call, Set<Replacement> replacements);

	boolean identicalExpression(IAbstractCall call);

	boolean equalArguments(IAbstractCall call);

	boolean identicalOrReplacedArguments(IAbstractCall call, Set<Replacement> replacements);

	boolean identicalOrConcatenatedArguments(IAbstractCall call);

	boolean identicalOrWrappedArguments(IAbstractCall call);

	boolean allArgumentsReplaced(IAbstractCall call, Set<Replacement> replacements);

	boolean allArgumentsReplaced(IAbstractCall call, Set<Replacement> replacements,
			Map<String, String> parameterToArgumentMap);

	boolean renamedWithIdenticalExpressionAndArguments(IAbstractCall call, Set<Replacement> replacements,
			double distance);

	boolean renamedWithDifferentExpressionAndIdenticalArguments(IAbstractCall call);

	boolean renamedWithIdenticalArgumentsAndNoExpression(IAbstractCall call, double distance,
			List<UMLOperationBodyMapper> lambdaMappers);

	boolean renamedWithIdenticalExpressionAndDifferentNumberOfArguments(IAbstractCall call,
			Set<Replacement> replacements, double distance, List<UMLOperationBodyMapper> lambdaMappers);

	boolean identicalWithMergedArguments(AbstractCall call, Set<Replacement> replacements);

	boolean identicalWithDifferentNumberOfArguments(IAbstractCall call, Set<Replacement> replacements,
			Map<String, String> parameterToArgumentMap);

	boolean identical(IAbstractCall call, Set<Replacement> replacements);

	Set<String> argumentIntersection(IAbstractCall call);

	Replacement makeReplacementForReturnedArgument(String statement);

	Replacement makeReplacementForWrappedCall(String statement);

	Replacement makeReplacementForAssignedArgument(String statement);

	CodeRange codeRange();

}