package gr.uom.java.xmi.decomposition;

import java.util.Set;

public interface ILeafMapping {

	int compareTo(ILeafMapping o);

	Set<String> callChainIntersection();

}