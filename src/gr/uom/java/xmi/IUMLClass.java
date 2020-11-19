package gr.uom.java.xmi;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IUMLClass {

	List<UMLTypeParameter> getTypeParameters();

	List<String> getTypeParameterNames();

	void addTypeParameter(UMLTypeParameter typeParameter);

	List<UMLAnnotation> getAnnotations();

	void addAnnotation(UMLAnnotation annotation);

	void addAnonymousClass(UMLAnonymousClass anonymousClass);

	String getPackageName();

	String getName();

	//returns true if the "innerClass" parameter is inner class of this
	boolean isInnerClass(UMLClass innerClass);

	boolean isTopLevel();

	void setTopLevel(boolean topLevel);

	String getVisibility();

	void setVisibility(String visibility);

	boolean isEnum();

	void setEnum(boolean isEnum);

	boolean isInterface();

	void setInterface(boolean isInterface);

	boolean isAbstract();

	void setAbstract(boolean isAbstract);

	UMLType getSuperclass();

	void setSuperclass(UMLType superclass);

	void addImplementedInterface(UMLType implementedInterface);

	List<UMLType> getImplementedInterfaces();

	List<String> getImportedTypes();

	List<UMLAnonymousClass> getAnonymousClassList();

	UMLJavadoc getJavadoc();

	void setJavadoc(UMLJavadoc javadoc);

	UMLAttribute containsAttribute(UMLAttribute otherAttribute);

	UMLAttribute matchAttribute(UMLAttribute otherAttribute);

	UMLOperation matchOperation(UMLOperation otherOperation);

	boolean hasSameNameAndKind(UMLClass umlClass);

	boolean hasSameKind(UMLClass umlClass);

	boolean equals(Object o);

	String toString();

	int compareTo(IUMLClass umlClass);

	double normalizedNameDistance(UMLClass c);

	double normalizedPackageNameDistance(UMLClass c);

	double normalizedSourceFolderDistance(UMLClass c);

	boolean implementsInterface(Set<UMLType> interfaces);

	boolean isSubTypeOf(IUMLClass umlClass);

	boolean importsType(String targetClass);

	boolean containsAnonymousWithSameAttributesAndOperations(UMLAnonymousClass anonymous);

	boolean isSingleAbstractMethodInterface();

	Map<String, Set<String>> aliasedAttributes();

}