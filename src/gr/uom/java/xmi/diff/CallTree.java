package gr.uom.java.xmi.diff;

import gr.uom.java.xmi.UMLOperation;

public class CallTree {
	CallTreeNode root;
	
	public CallTree(CallTreeNode root) {
		this.root = root;
	}
	
	public boolean contains(UMLOperation invokedOperation) {
		for(CallTreeNode node : root.getNodesInBreadthFirstOrder()) {
			if(node.getInvokedOperation().equals(invokedOperation)) {
				return true;
			}
		}
		return false;
	}
}
