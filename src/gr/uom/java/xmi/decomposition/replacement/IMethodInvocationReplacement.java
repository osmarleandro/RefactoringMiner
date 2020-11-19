package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.OperationInvocation;

public interface IMethodInvocationReplacement {

	OperationInvocation getInvokedOperationBefore();

	OperationInvocation getInvokedOperationAfter();

	boolean differentExpressionNameAndArguments();

}