package gr.uom.java.xmi;

import java.util.List;

import gr.uom.java.xmi.diff.CodeRange;
import gr.uom.java.xmi.diff.RenamePattern;

public interface IUMLAbstractClass {

	LocationInfo getLocationInfo();

	void addOperation(UMLOperation operation);

	void addAttribute(UMLAttribute attribute);

	List<UMLOperation> getOperations();

	List<UMLAttribute> getAttributes();

	UMLOperation operationWithTheSameSignature(UMLOperation operation);

	boolean containsOperationWithTheSameSignature(UMLOperation operation);

	UMLOperation operationWithTheSameSignatureIgnoringChangedTypes(UMLOperation operation);

	boolean containsOperationWithTheSameSignatureIgnoringChangedTypes(UMLOperation operation);

	boolean containsOperationWithTheSameName(UMLOperation operation);

	boolean containsOperationWithTheSameRenamePattern(UMLOperation operation, RenamePattern pattern);

	UMLAttribute attributeWithTheSameNameIgnoringChangedType(UMLAttribute attribute);

	boolean containsAttributeWithTheSameNameIgnoringChangedType(UMLAttribute attribute);

	boolean containsAttributeWithTheSameName(UMLAttribute attribute);

	boolean containsAttributeWithTheSameRenamePattern(UMLAttribute attribute, RenamePattern pattern);

	boolean containsAttributeWithName(String attributeName);

	boolean hasAttributesAndOperationsWithCommonNames(UMLAbstractClass umlClass);

	boolean hasCommonAttributesAndOperations(UMLAbstractClass umlClass);

	boolean hasSameAttributesAndOperations(UMLAbstractClass umlClass);

	boolean isTestClass();

	List<UMLAttribute> attributesOfType(String targetClass);

	boolean isSingleAbstractMethodInterface();

	boolean isInterface();

	String getSourceFile();

	CodeRange codeRange();

}