package gr.uom.java.xmi.decomposition;

import gr.uom.java.xmi.UMLOperation;

public class CompositeStatementObjectMapping extends AbstractCodeMapping implements Comparable<CompositeStatementObjectMapping> {

	double compositeChildMatchingScore;
	
	public CompositeStatementObjectMapping(CompositeStatementObject statement1, CompositeStatementObject statement2,
			UMLOperation operation1, UMLOperation operation2, double score) {
		super(statement1, statement2, operation1, operation2);
		this.compositeChildMatchingScore = score;
	}

}
