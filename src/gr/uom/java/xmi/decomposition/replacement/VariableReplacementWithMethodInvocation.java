package gr.uom.java.xmi.decomposition.replacement;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import gr.uom.java.xmi.decomposition.AbstractCodeMapping;
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

	public void processVariableReplacementWithMethodInvocation(
			AbstractCodeMapping mapping, Map<String, Map<VariableReplacementWithMethodInvocation, Set<AbstractCodeMapping>>> variableInvocationExpressionMap, Direction direction) {
		String expression = getInvokedOperation().getExpression();
		if(expression != null && getDirection().equals(direction)) {
			if(variableInvocationExpressionMap.containsKey(expression)) {
				Map<VariableReplacementWithMethodInvocation, Set<AbstractCodeMapping>> map = variableInvocationExpressionMap.get(expression);
				if(map.containsKey(this)) {
					if(mapping != null) {
						map.get(this).add(mapping);
					}
				}
				else {
					Set<AbstractCodeMapping> mappings = new LinkedHashSet<AbstractCodeMapping>();
					if(mapping != null) {
						mappings.add(mapping);
					}
					map.put(this, mappings);
				}
			}
			else {
				Set<AbstractCodeMapping> mappings = new LinkedHashSet<AbstractCodeMapping>();
				if(mapping != null) {
					mappings.add(mapping);
				}
				Map<VariableReplacementWithMethodInvocation, Set<AbstractCodeMapping>> map = new LinkedHashMap<VariableReplacementWithMethodInvocation, Set<AbstractCodeMapping>>();
				map.put(this, mappings);
				variableInvocationExpressionMap.put(expression, map);
			}
		}
	}

	public enum Direction {
		VARIABLE_TO_INVOCATION, INVOCATION_TO_VARIABLE;
	}
}
