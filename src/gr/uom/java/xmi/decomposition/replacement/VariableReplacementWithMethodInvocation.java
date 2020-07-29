package gr.uom.java.xmi.decomposition.replacement;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gr.uom.java.xmi.decomposition.AbstractCall;
import gr.uom.java.xmi.decomposition.OperationInvocation;
import gr.uom.java.xmi.decomposition.replacement.VariableReplacementWithMethodInvocation.Direction;

public class VariableReplacementWithMethodInvocation extends Replacement {
	private OperationInvocation invokedOperation;
	private Direction direction;
	
	public VariableReplacementWithMethodInvocation(String before, String after, OperationInvocation invocation, Direction direction) {
		super(before, after, ReplacementType.VARIABLE_REPLACED_WITH_METHOD_INVOCATION);
		this.invokedOperation = invocation;
		this.direction = direction;
	}

	public OperationInvocation getInvokedOperation() {
		return invokedOperation;
	}

	public Direction getDirection() {
		return direction;
	}

	public enum Direction {
		VARIABLE_TO_INVOCATION, INVOCATION_TO_VARIABLE;

		public Set<Replacement> replacementsWithinMethodInvocations(String s1, String s2, Set<String> set1, Set<String> set2, Map<String, List<? extends AbstractCall>> methodInvocationMap) {
			Set<Replacement> replacements = new LinkedHashSet<Replacement>();
			for(String element1 : set1) {
				if(s1.contains(element1) && !s1.equals(element1) && !s1.equals("this." + element1) && !s1.equals("_" + element1)) {
					int startIndex1 = s1.indexOf(element1);
					String substringBeforeIndex1 = s1.substring(0, startIndex1);
					String substringAfterIndex1 = s1.substring(startIndex1 + element1.length(), s1.length());
					for(String element2 : set2) {
						if(element2.endsWith(substringAfterIndex1) && substringAfterIndex1.length() > 1) {
							element2 = element2.substring(0, element2.indexOf(substringAfterIndex1));
						}
						if(s2.contains(element2) && !s2.equals(element2)) {
							int startIndex2 = s2.indexOf(element2);
							String substringBeforeIndex2 = s2.substring(0, startIndex2);
							String substringAfterIndex2 = s2.substring(startIndex2 + element2.length(), s2.length());
							List<? extends AbstractCall> methodInvocationList = null;
							if(equals(Direction.VARIABLE_TO_INVOCATION))
								methodInvocationList = methodInvocationMap.get(element2);
							else if(equals(Direction.INVOCATION_TO_VARIABLE))
								methodInvocationList = methodInvocationMap.get(element1);
							if(substringBeforeIndex1.equals(substringBeforeIndex2) && !substringAfterIndex1.isEmpty() && !substringAfterIndex2.isEmpty() && methodInvocationList != null) {
								Replacement r = new VariableReplacementWithMethodInvocation(element1, element2, (OperationInvocation)methodInvocationList.get(0), this);
								replacements.add(r);
							}
							else if(substringAfterIndex1.equals(substringAfterIndex2) && !substringBeforeIndex1.isEmpty() && !substringBeforeIndex2.isEmpty() && methodInvocationList != null) {
								Replacement r = new VariableReplacementWithMethodInvocation(element1, element2, (OperationInvocation)methodInvocationList.get(0), this);
								replacements.add(r);
							}
						}
					}
				}
			}
			return replacements;
		}
	}
}
