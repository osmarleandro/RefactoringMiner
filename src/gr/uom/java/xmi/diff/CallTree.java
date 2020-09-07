package gr.uom.java.xmi.diff;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import gr.uom.java.xmi.UMLOperation;

public class CallTree {
	private CallTreeNode_RENAMED root;
	
	public CallTree(CallTreeNode_RENAMED root) {
		this.root = root;
	}
	
	public List<CallTreeNode_RENAMED> getNodesInBreadthFirstOrder() {
		List<CallTreeNode_RENAMED> nodes = new ArrayList<CallTreeNode_RENAMED>();
		List<CallTreeNode_RENAMED> queue = new LinkedList<CallTreeNode_RENAMED>();
		nodes.add(root);
		queue.add(root);
		while(!queue.isEmpty()) {
			CallTreeNode_RENAMED node = queue.remove(0);
			nodes.addAll(node.getChildren());
			queue.addAll(node.getChildren());
		}
		return nodes;
	}
	
	public boolean contains(UMLOperation invokedOperation) {
		for(CallTreeNode_RENAMED node : getNodesInBreadthFirstOrder()) {
			if(node.getInvokedOperation().equals(invokedOperation)) {
				return true;
			}
		}
		return false;
	}
}
