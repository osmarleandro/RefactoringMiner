package gr.uom.java.xmi.decomposition.replacement;

import gr.uom.java.xmi.decomposition.ObjectCreation;
import gr.uom.java.xmi.decomposition.OperationInvocation;

public interface IMethodInvocationWithClassInstanceCreationReplacement {

	OperationInvocation getInvokedOperationBefore();

	ObjectCreation getObjectCreationAfter();

}