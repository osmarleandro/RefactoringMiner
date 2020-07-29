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

	public boolean isGetter(UMLOperation umlOperation) {
		if(umlOperation.getBody() != null) {
			List<AbstractStatement> statements = umlOperation.getBody().getCompositeStatement().getStatements();
			List<UMLParameter> parameters = umlOperation.getParametersWithoutReturnType();
			if(statements.size() == 1 && statements.get(0) instanceof StatementObject) {
				StatementObject statement = (StatementObject)statements.get(0);
				if(statement.getString().startsWith("return ")) {
					for(String variable : statement.getVariables()) {
						if(statement.getString().equals("return " + variable + ";\n") && parameters.size() == 0) {
							return true;
						}
						else if(statement.getString().equals("return " + variable + ".keySet()" + ";\n") && parameters.size() == 0) {
							return true;
						}
						else if(statement.getString().equals("return " + variable + ".values()" + ";\n") && parameters.size() == 0) {
							return true;
						}
					}
					UMLParameter returnParameter = umlOperation.getReturnParameter();
					if((umlOperation.name.startsWith("is") || umlOperation.name.startsWith("has")) && parameters.size() == 0 &&
							returnParameter != null && returnParameter.getType().getClassType().equals("boolean")) {
						return true;
					}
					if(statement.getString().equals("return null;\n")) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
