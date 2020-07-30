package gr.uom.java.xmi.decomposition;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractStatement extends AbstractCodeFragment {
	private CompositeStatementObject parent;
	
	public void setParent(CompositeStatementObject parent) {
    	this.parent = parent;
    }

    public CompositeStatementObject getParent() {
    	return this.parent;
    }

	public String getString() {
    	return toString();
    }

    public VariableDeclaration searchVariableDeclaration(String variableName) {
    	VariableDeclaration variableDeclaration = this.getVariableDeclaration(variableName);
    	if(variableDeclaration != null) {
    		return variableDeclaration;
    	}
    	else if(parent != null) {
    		return parent.searchVariableDeclaration(variableName);
    	}
    	return null;
    }

    public abstract List<StatementObject> getLeaves();
    public abstract int statementCount();

	public void replaceParametersWithArguments(Map<String, String> parameterToArgumentMap) {
		String afterReplacements = getString();
		for(String parameter : parameterToArgumentMap.keySet()) {
			String argument = parameterToArgumentMap.get(parameter);
			if(!parameter.equals(argument)) {
				StringBuffer sb = new StringBuffer();
				Pattern p = Pattern.compile(Pattern.quote(parameter));
				Matcher m = p.matcher(afterReplacements);
				while(m.find()) {
					//check if the matched string is an argument
					//previous character should be "(" or "," or " " or there is no previous character
					int start = m.start();
					boolean isArgument = false;
					boolean isInsideStringLiteral = false;
					if(start >= 1) {
						String previousChar = afterReplacements.substring(start-1, start);
						if(previousChar.equals("(") || previousChar.equals(",") || previousChar.equals(" ") || previousChar.equals("=")) {
							isArgument = true;
						}
						String beforeMatch = afterReplacements.substring(0, start);
						String afterMatch = afterReplacements.substring(start+parameter.length(), afterReplacements.length());
						if(quoteBefore(beforeMatch) && quoteAfter(afterMatch)) {
							isInsideStringLiteral = true;
						}
					}
					else if(start == 0 && !afterReplacements.startsWith("return ")) {
						isArgument = true;
					}
					if(isArgument && !isInsideStringLiteral) {
						m.appendReplacement(sb, Matcher.quoteReplacement(argument));
					}
				}
				m.appendTail(sb);
				afterReplacements = sb.toString();
			}
		}
		this.codeFragmentAfterReplacingParametersWithArguments = afterReplacements;
	}
}
