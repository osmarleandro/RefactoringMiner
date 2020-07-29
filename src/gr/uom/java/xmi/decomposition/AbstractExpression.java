package gr.uom.java.xmi.decomposition;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.util.PrefixSuffixUtils;

import gr.uom.java.xmi.LocationInfo;
import gr.uom.java.xmi.LocationInfo.CodeElementType;
import gr.uom.java.xmi.diff.CodeRange;
import gr.uom.java.xmi.diff.ExtractVariableRefactoring;

public class AbstractExpression extends AbstractCodeFragment {
	
	private String expression;
	private LocationInfo locationInfo;
	private CompositeStatementObject owner;
	private List<String> variables;
	private List<String> types;
	private List<VariableDeclaration> variableDeclarations;
	private Map<String, List<OperationInvocation>> methodInvocationMap;
	private List<AnonymousClassDeclarationObject> anonymousClassDeclarations;
	private List<String> stringLiterals;
	private List<String> numberLiterals;
	private List<String> nullLiterals;
	private List<String> booleanLiterals;
	private List<String> typeLiterals;
	private Map<String, List<ObjectCreation>> creationMap;
	private List<String> infixOperators;
	private List<String> arrayAccesses;
	private List<String> prefixExpressions;
	private List<String> postfixExpressions;
	private List<String> arguments;
	private List<TernaryOperatorExpression> ternaryOperatorExpressions;
	private List<LambdaExpressionObject> lambdas;
    
    public AbstractExpression(CompilationUnit cu, String filePath, Expression expression, CodeElementType codeElementType) {
    	this.locationInfo = new LocationInfo(cu, filePath, expression, codeElementType);
    	Visitor visitor = new Visitor(cu, filePath);
    	expression.accept(visitor);
		this.variables = visitor.getVariables();
		this.types = visitor.getTypes();
		this.variableDeclarations = visitor.getVariableDeclarations();
		this.methodInvocationMap = visitor.getMethodInvocationMap();
		this.anonymousClassDeclarations = visitor.getAnonymousClassDeclarations();
		this.stringLiterals = visitor.getStringLiterals();
		this.numberLiterals = visitor.getNumberLiterals();
		this.nullLiterals = visitor.getNullLiterals();
		this.booleanLiterals = visitor.getBooleanLiterals();
		this.typeLiterals = visitor.getTypeLiterals();
		this.creationMap = visitor.getCreationMap();
		this.infixOperators = visitor.getInfixOperators();
		this.arrayAccesses = visitor.getArrayAccesses();
		this.prefixExpressions = visitor.getPrefixExpressions();
		this.postfixExpressions = visitor.getPostfixExpressions();
		this.arguments = visitor.getArguments();
		this.ternaryOperatorExpressions = visitor.getTernaryOperatorExpressions();
		this.lambdas = visitor.getLambdas();
    	this.expression = expression.toString();
    	this.owner = null;
    }

    public void setOwner(CompositeStatementObject owner) {
    	this.owner = owner;
    }

    public CompositeStatementObject getOwner() {
    	return this.owner;
    }

	@Override
	public CompositeStatementObject getParent() {
		return getOwner();
	}

    public String getExpression() {
    	return expression;
    }

	public String getString() {
    	return toString();
    }
  
	public String toString() {
		return getExpression().toString();
	}

	@Override
	public List<String> getVariables() {
		return variables;
	}

	@Override
	public List<String> getTypes() {
		return types;
	}

	@Override
	public List<VariableDeclaration> getVariableDeclarations() {
		return variableDeclarations;
	}

	@Override
	public Map<String, List<OperationInvocation>> getMethodInvocationMap() {
		return methodInvocationMap;
	}

	@Override
	public List<AnonymousClassDeclarationObject> getAnonymousClassDeclarations() {
		return anonymousClassDeclarations;
	}

	@Override
	public List<String> getStringLiterals() {
		return stringLiterals;
	}

	@Override
	public List<String> getNumberLiterals() {
		return numberLiterals;
	}

	@Override
	public List<String> getNullLiterals() {
		return nullLiterals;
	}

	@Override
	public List<String> getBooleanLiterals() {
		return booleanLiterals;
	}

	@Override
	public List<String> getTypeLiterals() {
		return typeLiterals;
	}

	@Override
	public Map<String, List<ObjectCreation>> getCreationMap() {
		return creationMap;
	}

	@Override
	public List<String> getInfixOperators() {
		return infixOperators;
	}

	@Override
	public List<String> getArrayAccesses() {
		return arrayAccesses;
	}

	@Override
	public List<String> getPrefixExpressions() {
		return prefixExpressions;
	}

	@Override
	public List<String> getPostfixExpressions() {
		return postfixExpressions;
	}

	@Override
	public List<String> getArguments() {
		return arguments;
	}

	@Override
	public List<TernaryOperatorExpression> getTernaryOperatorExpressions() {
		return ternaryOperatorExpressions;
	}

	@Override
	public List<LambdaExpressionObject> getLambdas() {
		return lambdas;
	}

	public LocationInfo getLocationInfo() {
		return locationInfo;
	}

	public VariableDeclaration searchVariableDeclaration(String variableName) {
		VariableDeclaration variableDeclaration = this.getVariableDeclaration(variableName);
		if(variableDeclaration != null) {
			return variableDeclaration;
		}
		else if(owner != null) {
			return owner.searchVariableDeclaration(variableName);
		}
		return null;
	}

	public VariableDeclaration getVariableDeclaration(String variableName) {
		List<VariableDeclaration> variableDeclarations = getVariableDeclarations();
		for(VariableDeclaration declaration : variableDeclarations) {
			if(declaration.getVariableName().equals(variableName)) {
				return declaration;
			}
		}
		return null;
	}

	public CodeRange codeRange() {
		return locationInfo.codeRange();
	}

	boolean overlappingExtractVariable(String input, List<? extends AbstractCodeFragment> nonMappedLeavesT2, Set<Refactoring> refactorings) {
		String output = input;
		for(Refactoring ref : refactorings) {
			if(ref instanceof ExtractVariableRefactoring) {
				ExtractVariableRefactoring extractVariable = (ExtractVariableRefactoring)ref;
				VariableDeclaration declaration = extractVariable.getVariableDeclaration();
				if(declaration.getInitializer() != null && input.contains(declaration.getInitializer().toString())) {
					output = output.replace(declaration.getInitializer().toString(), declaration.getVariableName());
				}
			}
		}
		if(toString().equals(output)) {
			return true;
		}
		String longestCommonSuffix = PrefixSuffixUtils.longestCommonSuffix(toString(), input);
		if(!longestCommonSuffix.isEmpty() && longestCommonSuffix.startsWith(".")) {
			String prefix1 = toString().substring(0, toString().indexOf(longestCommonSuffix));
			String prefix2 = input.substring(0, input.indexOf(longestCommonSuffix));
			//skip static variable prefixes
			if(prefix1.equals(prefix2) || (!prefix1.toUpperCase().equals(prefix1) && !prefix2.toUpperCase().equals(prefix2))) {
				return true;
			}
		}
		String longestCommonPrefix = PrefixSuffixUtils.longestCommonPrefix(toString(), input);
		if(!longestCommonSuffix.isEmpty() && !longestCommonPrefix.isEmpty() &&
				!longestCommonPrefix.equals(toString()) && !longestCommonPrefix.equals(input) &&
				!longestCommonSuffix.equals(toString()) && !longestCommonSuffix.equals(input) &&
				longestCommonPrefix.length() + longestCommonSuffix.length() < input.length() &&
				longestCommonPrefix.length() + longestCommonSuffix.length() < toString().length()) {
			String s1 = input.substring(longestCommonPrefix.length(), input.lastIndexOf(longestCommonSuffix));
			String s2 = toString().substring(longestCommonPrefix.length(), toString().lastIndexOf(longestCommonSuffix));
			for(AbstractCodeFragment statement : nonMappedLeavesT2) {
				VariableDeclaration variable = statement.getVariableDeclaration(s2);
				if(variable != null) {
					if(variable.getInitializer() != null && variable.getInitializer().toString().equals(s1)) {
						return true;
					}
					List<TernaryOperatorExpression> ternaryOperators = statement.getTernaryOperatorExpressions();
					for(TernaryOperatorExpression ternaryOperator : ternaryOperators) {
						if(ternaryOperator.getThenExpression().toString().equals(s1) ||
								ternaryOperator.getElseExpression().toString().equals(s1)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
