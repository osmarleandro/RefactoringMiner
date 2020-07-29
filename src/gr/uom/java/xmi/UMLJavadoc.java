package gr.uom.java.xmi;

import java.util.ArrayList;
import java.util.List;

import gr.uom.java.xmi.decomposition.AbstractStatement;
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

	public boolean isSetter(UMLOperation umlOperation) {
		List<String> parameterNames = umlOperation.getParameterNameList();
		if(umlOperation.getBody() != null && parameterNames.size() == 1) {
			List<AbstractStatement> statements = umlOperation.getBody().getCompositeStatement().getStatements();
			if(statements.size() == 1 && statements.get(0) instanceof StatementObject) {
				StatementObject statement = (StatementObject)statements.get(0);
				for(String variable : statement.getVariables()) {
					if(statement.getString().equals(variable + "=" + parameterNames.get(0) + ";\n")) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
