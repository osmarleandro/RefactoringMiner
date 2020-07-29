package gr.uom.java.xmi.decomposition;

import java.util.LinkedHashSet;
import java.util.Set;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.LocationInfo.CodeElementType;
import gr.uom.java.xmi.diff.StringDistance;

public class LeafMapping extends AbstractCodeMapping implements Comparable<LeafMapping> {

	public LeafMapping(AbstractCodeFragment statement1, AbstractCodeFragment statement2,
			UMLOperation operation1, UMLOperation operation2) {
		super(statement1, statement2, operation1, operation2);
	}

	private double parentEditDistance() {
		CompositeStatementObject parent1 = getFragment1().getParent();
		while(parent1 != null && parent1.getLocationInfo().getCodeElementType().equals(CodeElementType.BLOCK)) {
			parent1 = parent1.getParent();
		}
		CompositeStatementObject parent2 = getFragment2().getParent();
		while(parent2 != null && parent2.getLocationInfo().getCodeElementType().equals(CodeElementType.BLOCK)) {
			parent2 = parent2.getParent();
		}
		if(parent1 == null && parent2 == null) {
			//method signature is the parent
			return 0;
		}
		else if(parent1 == null && parent2 != null) {
			String s2 = parent2.getString();
			int distance = StringDistance.editDistance("{", s2);
			double normalized = (double)distance/(double)Math.max(1, s2.length());
			return normalized;
		}
		else if(parent1 != null && parent2 == null) {
			String s1 = parent1.getString();
			int distance = StringDistance.editDistance(s1, "{");
			double normalized = (double)distance/(double)Math.max(s1.length(), 1);
			return normalized;
		}
		String s1 = parent1.getString();
		String s2 = parent2.getString();
		int distance = StringDistance.editDistance(s1, s2);
		double normalized = (double)distance/(double)Math.max(s1.length(), s2.length());
		return normalized;
	}

	public Set<String> callChainIntersection() {
		OperationInvocation invocation1 = this.getFragment1().invocationCoveringEntireFragment();
		OperationInvocation invocation2 = this.getFragment2().invocationCoveringEntireFragment();
		if(invocation1 != null && invocation2 != null) {
			return invocation1.callChainIntersection(invocation2);
		}
		return new LinkedHashSet<String>();
	}

	public int compareTo(LeafMapping leafMapping) {
		double distance1;
		double distance2;
		if(leafMapping.getFragment1().getString().equals(leafMapping.getFragment2().getString())) {
			distance1 = 0;
		}
		else {
			String s1 = leafMapping.getFragment1().getString().toLowerCase();
			String s2 = leafMapping.getFragment2().getString().toLowerCase();
			int distance = StringDistance.editDistance(s1, s2);
			distance1 = (double)distance/(double)Math.max(s1.length(), s2.length());
		}
		
		if(getFragment1().getString().equals(getFragment2().getString())) {
			distance2 = 0;
		}
		else {
			String s1 = getFragment1().getString().toLowerCase();
			String s2 = getFragment2().getString().toLowerCase();
			int distance = StringDistance.editDistance(s1, s2);
			distance2 = (double)distance/(double)Math.max(s1.length(), s2.length());
		}
		
		if(distance1 != distance2) {
			if(leafMapping.isIdenticalWithExtractedVariable() && !isIdenticalWithExtractedVariable()) {
				return -1;
			}
			else if(!leafMapping.isIdenticalWithExtractedVariable() && isIdenticalWithExtractedVariable()) {
				return 1;
			}
			if(leafMapping.isIdenticalWithInlinedVariable() && !isIdenticalWithInlinedVariable()) {
				return -1;
			}
			else if(!leafMapping.isIdenticalWithInlinedVariable() && isIdenticalWithInlinedVariable()) {
				return 1;
			}
			return Double.compare(distance1, distance2);
		}
		else {
			int depthDiff1 = Math.abs(leafMapping.getFragment1().getDepth() - leafMapping.getFragment2().getDepth());
			int depthDiff2 = Math.abs(getFragment1().getDepth() - getFragment2().getDepth());
	
			if(depthDiff1 != depthDiff2) {
				return Integer.valueOf(depthDiff1).compareTo(Integer.valueOf(depthDiff2));
			}
			else {
				int indexDiff1 = Math.abs(leafMapping.getFragment1().getIndex() - leafMapping.getFragment2().getIndex());
				int indexDiff2 = Math.abs(getFragment1().getIndex() - getFragment2().getIndex());
				if(indexDiff1 != indexDiff2) {
					return Integer.valueOf(indexDiff1).compareTo(Integer.valueOf(indexDiff2));
				}
				else {
					double parentEditDistance1 = leafMapping.parentEditDistance();
					double parentEditDistance2 = parentEditDistance();
					return Double.compare(parentEditDistance1, parentEditDistance2);
				}
			}
		}
	}
}
