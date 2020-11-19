package gr.uom.java.xmi.diff;

import java.util.List;

import gr.uom.java.xmi.UMLOperation;

public interface ICallTree {

	List<CallTreeNode> getNodesInBreadthFirstOrder();

	boolean contains(UMLOperation invokedOperation);

}