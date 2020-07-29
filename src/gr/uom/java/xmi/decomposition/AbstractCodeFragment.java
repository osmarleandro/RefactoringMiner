package gr.uom.java.xmi.decomposition;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gr.uom.java.xmi.LocationInfo.CodeElementType;
import gr.uom.java.xmi.LocationInfoProvider;
import gr.uom.java.xmi.UMLType;
import gr.uom.java.xmi.decomposition.AbstractCall.StatementCoverageType;
import gr.uom.java.xmi.decomposition.UMLOperationBodyMapper.ReplacementInfo;
import gr.uom.java.xmi.decomposition.replacement.Replacement;
import gr.uom.java.xmi.decomposition.replacement.Replacement.ReplacementType;

public abstract class AbstractCodeFragment implements LocationInfoProvider {
	private int depth;
	private int index;
	private String codeFragmentAfterReplacingParametersWithArguments;

	public String getArgumentizedString() {
		return codeFragmentAfterReplacingParametersWithArguments != null ? codeFragmentAfterReplacingParametersWithArguments : getString();
	}

    public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public abstract CompositeStatementObject getParent();
	public abstract String getString();
	public abstract List<String> getVariables();
	public abstract List<String> getTypes();
	public abstract List<VariableDeclaration> getVariableDeclarations();
	public abstract Map<String, List<OperationInvocation>> getMethodInvocationMap();
	public abstract List<AnonymousClassDeclarationObject> getAnonymousClassDeclarations();
	public abstract List<String> getStringLiterals();
	public abstract List<String> getNumberLiterals();
	public abstract List<String> getNullLiterals();
	public abstract List<String> getBooleanLiterals();
	public abstract List<String> getTypeLiterals();
	public abstract Map<String, List<ObjectCreation>> getCreationMap();
	public abstract List<String> getInfixOperators();
	public abstract List<String> getArrayAccesses();
	public abstract List<String> getPrefixExpressions();
	public abstract List<String> getPostfixExpressions();
	public abstract List<String> getArguments();
	public abstract List<TernaryOperatorExpression> getTernaryOperatorExpressions();
	public abstract List<LambdaExpressionObject> getLambdas();
	public abstract VariableDeclaration searchVariableDeclaration(String variableName);
	public abstract VariableDeclaration getVariableDeclaration(String variableName);
	
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

	private static boolean quoteBefore(String beforeMatch) {
		if(beforeMatch.contains("\"")) {
			if(beforeMatch.contains("+")) {
				int indexOfQuote = beforeMatch.lastIndexOf("\"");
				int indexOfPlus = beforeMatch.lastIndexOf("+");
				if(indexOfPlus > indexOfQuote) {
					return false;
				}
				else {
					return true;
				}
			}
			else {
				return true;
			}
		}
		return false;
	}

	private static boolean quoteAfter(String afterMatch) {
		if(afterMatch.contains("\"")) {
			if(afterMatch.contains("+")) {
				int indexOfQuote = afterMatch.indexOf("\"");
				int indexOfPlus = afterMatch.indexOf("+");
				if(indexOfPlus < indexOfQuote) {
					return false;
				}
				else {
					return true;
				}
			}
			else {
				return true;
			}
		}
		return false;
	}

	public boolean equalFragment(AbstractCodeFragment other) {
		if(this.getString().equals(other.getString())) {
			return true;
		}
		else if(this.getString().contains(other.getString())) {
			return true;
		}
		else if(other.getString().contains(this.getString())) {
			return true;
		}
		else if(this.codeFragmentAfterReplacingParametersWithArguments != null) {
			return this.codeFragmentAfterReplacingParametersWithArguments.equals(other.getString());
		}
		else if(other.codeFragmentAfterReplacingParametersWithArguments != null) {
			return other.codeFragmentAfterReplacingParametersWithArguments.equals(this.getString());
		}
		return false;
	}

	public void resetArgumentization() {
		this.codeFragmentAfterReplacingParametersWithArguments = getString();
	}

	public ObjectCreation creationCoveringEntireFragment() {
		Map<String, List<ObjectCreation>> creationMap = getCreationMap();
		String statement = getString();
		for(String objectCreation : creationMap.keySet()) {
			List<ObjectCreation> creations = creationMap.get(objectCreation);
			for(ObjectCreation creation : creations) {
				if((objectCreation + ";\n").equals(statement) || objectCreation.equals(statement)) {
					creation.coverage = StatementCoverageType.ONLY_CALL;
					return creation;
				}
				else if(("return " + objectCreation + ";\n").equals(statement)) {
					creation.coverage = StatementCoverageType.RETURN_CALL;
					return creation;
				}
				else if(("throw " + objectCreation + ";\n").equals(statement)) {
					creation.coverage = StatementCoverageType.THROW_CALL;
					return creation;
				}
				else if(expressionIsTheInitializerOfVariableDeclaration(objectCreation)) {
					creation.coverage = StatementCoverageType.VARIABLE_DECLARATION_INITIALIZER_CALL;
					return creation;
				}
			}
		}
		return null;
	}

	public OperationInvocation invocationCoveringEntireFragment() {
		Map<String, List<OperationInvocation>> methodInvocationMap = getMethodInvocationMap();
		String statement = getString();
		for(String methodInvocation : methodInvocationMap.keySet()) {
			List<OperationInvocation> invocations = methodInvocationMap.get(methodInvocation);
			for(OperationInvocation invocation : invocations) {
				if((methodInvocation + ";\n").equals(statement) || methodInvocation.equals(statement)) {
					invocation.coverage = StatementCoverageType.ONLY_CALL;
					return invocation;
				}
				else if(("return " + methodInvocation + ";\n").equals(statement)) {
					invocation.coverage = StatementCoverageType.RETURN_CALL;
					return invocation;
				}
				else if(isCastExpressionCoveringEntireFragment(methodInvocation)) {
					invocation.coverage = StatementCoverageType.CAST_CALL;
					return invocation;
				}
				else if(expressionIsTheInitializerOfVariableDeclaration(methodInvocation)) {
					invocation.coverage = StatementCoverageType.VARIABLE_DECLARATION_INITIALIZER_CALL;
					return invocation;
				}
				else if(invocation.getLocationInfo().getCodeElementType().equals(CodeElementType.SUPER_CONSTRUCTOR_INVOCATION) ||
						invocation.getLocationInfo().getCodeElementType().equals(CodeElementType.CONSTRUCTOR_INVOCATION)) {
					invocation.coverage = StatementCoverageType.ONLY_CALL;
					return invocation;
				}
			}
		}
		return null;
	}

	public OperationInvocation assignmentInvocationCoveringEntireStatement() {
		Map<String, List<OperationInvocation>> methodInvocationMap = getMethodInvocationMap();
		for(String methodInvocation : methodInvocationMap.keySet()) {
			List<OperationInvocation> invocations = methodInvocationMap.get(methodInvocation);
			for(OperationInvocation invocation : invocations) {
				if(expressionIsTheRightHandSideOfAssignment(methodInvocation)) {
					return invocation;
				}
			}
		}
		return null;
	}

	private boolean isCastExpressionCoveringEntireFragment(String expression) {
		String statement = getString();
		int index = statement.indexOf(expression + ";\n");
		if(index != -1) {
			String prefix = statement.substring(0, index);
			if(prefix.contains("(") && prefix.contains(")")) {
				String casting = prefix.substring(prefix.indexOf("("), prefix.indexOf(")")+1);
				if(("return " + casting + expression + ";\n").equals(statement)) {
					return true;
				}
			}
		}
		return false;
	}

	protected boolean containsInitializerOfVariableDeclaration(Set<String> expressions) {
		List<VariableDeclaration> variableDeclarations = getVariableDeclarations();
		if(variableDeclarations.size() == 1 && variableDeclarations.get(0).getInitializer() != null) {
			String initializer = variableDeclarations.get(0).getInitializer().toString();
			if(expressions.contains(initializer)) {
				return true;
			}
		}
		return false;
	}

	private boolean expressionIsTheInitializerOfVariableDeclaration(String expression) {
		List<VariableDeclaration> variableDeclarations = getVariableDeclarations();
		if(variableDeclarations.size() == 1 && variableDeclarations.get(0).getInitializer() != null) {
			String initializer = variableDeclarations.get(0).getInitializer().toString();
			if(initializer.equals(expression))
				return true;
			if(initializer.startsWith("(")) {
				//ignore casting
				String initializerWithoutCasting = initializer.substring(initializer.indexOf(")")+1,initializer.length());
				if(initializerWithoutCasting.equals(expression))
					return true;
			}
		}
		return false;
	}

	private boolean expressionIsTheRightHandSideOfAssignment(String expression) {
		String statement = getString();
		if(statement.contains("=")) {
			List<String> variables = getVariables();
			if(variables.size() > 0) {
				String s = variables.get(0) + "=" + expression + ";\n";
				if(statement.equals(s)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean throwsNewException() {
		return getString().startsWith("throw new ");
	}

	public boolean countableStatement() {
		String statement = getString();
		//covers the cases of lambda expressions having an expression as their body
		if(this instanceof AbstractExpression) {
			return true;
		}
		//covers the cases of methods with only one statement in their body
		if(this instanceof AbstractStatement && ((AbstractStatement)this).getParent() != null &&
				((AbstractStatement)this).getParent().statementCount() == 1 && ((AbstractStatement)this).getParent().getParent() == null) {
			return true;
		}
		return !statement.equals("{") && !statement.startsWith("catch(") && !statement.startsWith("case ") && !statement.startsWith("default :") &&
				!statement.startsWith("return true;") && !statement.startsWith("return false;") && !statement.startsWith("return this;") && !statement.startsWith("return null;") && !statement.startsWith("return;");
	}

	boolean variableAssignmentWithEverythingReplaced(AbstractCodeFragment statement2, ReplacementInfo replacementInfo) {
		String string1 = getString();
		String string2 = statement2.getString();
		if(UMLOperationBodyMapper.containsMethodSignatureOfAnonymousClass(string1)) {
			string1 = string1.substring(0, string1.indexOf("\n"));
		}
		if(UMLOperationBodyMapper.containsMethodSignatureOfAnonymousClass(string2)) {
			string2 = string2.substring(0, string2.indexOf("\n"));
		}
		if(string1.contains("=") && string1.endsWith(";\n") && string2.contains("=") && string2.endsWith(";\n")) {
			boolean typeReplacement = false, compatibleTypes = false, variableRename = false, classInstanceCreationReplacement = false;
			String variableName1 = string1.substring(0, string1.indexOf("="));
			String variableName2 = string2.substring(0, string2.indexOf("="));
			String assignment1 = string1.substring(string1.indexOf("=")+1, string1.lastIndexOf(";\n"));
			String assignment2 = string2.substring(string2.indexOf("=")+1, string2.lastIndexOf(";\n"));
			UMLType type1 = null, type2 = null;
			Map<String, List<ObjectCreation>> creationMap1 = getCreationMap();
			for(String creation1 : creationMap1.keySet()) {
				if(creation1.equals(assignment1)) {
					type1 = creationMap1.get(creation1).get(0).getType();
				}
			}
			Map<String, List<ObjectCreation>> creationMap2 = statement2.getCreationMap();
			for(String creation2 : creationMap2.keySet()) {
				if(creation2.equals(assignment2)) {
					type2 = creationMap2.get(creation2).get(0).getType();
				}
			}
			if(type1 != null && type2 != null) {
				compatibleTypes = type1.compatibleTypes(type2);
			}
			OperationInvocation inv1 = null, inv2 = null;
			Map<String, List<OperationInvocation>> methodInvocationMap1 = getMethodInvocationMap();
			for(String invocation1 : methodInvocationMap1.keySet()) {
				if(invocation1.equals(assignment1)) {
					inv1 = methodInvocationMap1.get(invocation1).get(0);
				}
			}
			Map<String, List<OperationInvocation>> methodInvocationMap2 = statement2.getMethodInvocationMap();
			for(String invocation2 : methodInvocationMap2.keySet()) {
				if(invocation2.equals(assignment2)) {
					inv2 = methodInvocationMap2.get(invocation2).get(0);
				}
			}
			for(Replacement replacement : replacementInfo.getReplacements()) {
				if(replacement.getType().equals(ReplacementType.TYPE)) {
					typeReplacement = true;
					if(string1.contains("new " + replacement.getBefore() + "(") && string2.contains("new " + replacement.getAfter() + "("))
						classInstanceCreationReplacement = true;
				}
				else if(replacement.getType().equals(ReplacementType.VARIABLE_NAME) &&
						(variableName1.equals(replacement.getBefore()) || variableName1.endsWith(" " + replacement.getBefore())) &&
						(variableName2.equals(replacement.getAfter()) || variableName2.endsWith(" " + replacement.getAfter())))
					variableRename = true;
				else if(replacement.getType().equals(ReplacementType.CLASS_INSTANCE_CREATION) &&
						assignment1.equals(replacement.getBefore()) &&
						assignment2.equals(replacement.getAfter()))
					classInstanceCreationReplacement = true;
			}
			if(typeReplacement && !compatibleTypes && variableRename && classInstanceCreationReplacement) {
				return true;
			}
			if(variableRename && inv1 != null && inv2 != null && inv1.differentExpressionNameAndArguments(inv2)) {
				if(inv1.getArguments().size() > inv2.getArguments().size()) {
					for(String argument : inv1.getArguments()) {
						List<OperationInvocation> argumentInvocations = methodInvocationMap1.get(argument);
						if(argumentInvocations != null) {
							for(OperationInvocation argumentInvocation : argumentInvocations) {
								if(!argumentInvocation.differentExpressionNameAndArguments(inv2)) {
									return false;
								}
							}
						}
					}
				}
				else if(inv1.getArguments().size() < inv2.getArguments().size()) {
					for(String argument : inv2.getArguments()) {
						List<OperationInvocation> argumentInvocations = methodInvocationMap2.get(argument);
						if(argumentInvocations != null) {
							for(OperationInvocation argumentInvocation : argumentInvocations) {
								if(!inv1.differentExpressionNameAndArguments(argumentInvocation)) {
									return false;
								}
							}
						}
					}
				}
				return true;
			}
		}
		return false;
	}
}
