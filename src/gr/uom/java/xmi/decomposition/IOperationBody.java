package gr.uom.java.xmi.decomposition;

import java.util.List;
import java.util.Map;
import java.util.Set;

import gr.uom.java.xmi.LocationInfo;

public interface IOperationBody {

	int statementCount();

	CompositeStatementObject getCompositeStatement();

	List<AnonymousClassDeclarationObject> getAllAnonymousClassDeclarations();

	List<OperationInvocation> getAllOperationInvocations();

	List<LambdaExpressionObject> getAllLambdas();

	List<String> getAllVariables();

	List<VariableDeclaration> getAllVariableDeclarations();

	List<VariableDeclaration> getVariableDeclarationsInScope(LocationInfo location);

	VariableDeclaration getVariableDeclaration(String variableName);

	Map<String, Set<String>> aliasedAttributes();

	CompositeStatementObject loopWithVariables(String currentElementName, String collectionName);

}