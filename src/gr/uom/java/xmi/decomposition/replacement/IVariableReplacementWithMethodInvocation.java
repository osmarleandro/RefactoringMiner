package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.OperationInvocation;
import gr.uom.java.xmi.decomposition.replacement.VariableReplacementWithMethodInvocation.Direction;

public interface IVariableReplacementWithMethodInvocation {

	OperationInvocation getInvokedOperation();

	Direction getDirection();

}