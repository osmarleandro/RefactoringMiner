package gr.uom.java.xmi.diff;

import java.util.List;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.decomposition.OperationInvocation;

public interface ICallTreeNode {

	UMLOperation getOriginalOperation();

	UMLOperation getInvokedOperation();

	OperationInvocation getInvocation();

	void addChild(CallTreeNode node);

	List<CallTreeNode> getChildren();

	int hashCode();

	boolean equals(Object obj);

	String toString();

}