package gr.uom.java.xmi.decomposition.replacement;

import java.util.Set;

public interface ISplitVariableReplacement {

	Set<String> getSplitVariables();

	boolean equal(SplitVariableReplacement other);

	boolean commonBefore(SplitVariableReplacement other);

	boolean subsumes(SplitVariableReplacement other);

}