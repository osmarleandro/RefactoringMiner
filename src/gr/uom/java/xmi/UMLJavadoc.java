package gr.uom.java.xmi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gr.uom.java.xmi.decomposition.AbstractStatement;
import gr.uom.java.xmi.decomposition.OperationInvocation;
import gr.uom.java.xmi.decomposition.StatementObject;

public class UMLJavadoc {
	private List<UMLTagElement> tags;

	public UMLJavadoc() {
		this.tags = new ArrayList<UMLTagElement>();
	}
	
	public void addTag(UMLTagElement tag) {
		tags.add(tag);
	}

	public List<UMLTagElement> getTags() {
		return tags;
	}

	public boolean contains(String s) {
		for(UMLTagElement tag : tags) {
			if(tag.contains(s)) {
				return true;
			}
		}
		return false;
	}

	public boolean containsIgnoreCase(String s) {
		for(UMLTagElement tag : tags) {
			if(tag.containsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}

	public OperationInvocation isDelegate(UMLOperation umlOperation) {
		if(umlOperation.getBody() != null) {
			List<AbstractStatement> statements = umlOperation.getBody().getCompositeStatement().getStatements();
			if(statements.size() == 1 && statements.get(0) instanceof StatementObject) {
				StatementObject statement = (StatementObject)statements.get(0);
				Map<String, List<OperationInvocation>> operationInvocationMap = statement.getMethodInvocationMap();
				for(String key : operationInvocationMap.keySet()) {
					List<OperationInvocation> operationInvocations = operationInvocationMap.get(key);
					for(OperationInvocation operationInvocation : operationInvocations) {
						if(operationInvocation.matchesOperation(umlOperation, umlOperation.variableTypeMap(), null) || operationInvocation.getMethodName().equals(umlOperation.getName())) {
							return operationInvocation;
						}
					}
				}
			}
		}
		return null;
	}
}
