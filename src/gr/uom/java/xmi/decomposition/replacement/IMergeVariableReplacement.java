package gr.uom.java.xmi.decomposition.replacement;

import java.util.Set;

public interface IMergeVariableReplacement {

	Set<String> getMergedVariables();

	boolean equal(MergeVariableReplacement other);

	boolean commonAfter(MergeVariableReplacement other);

	boolean subsumes(MergeVariableReplacement other);

}