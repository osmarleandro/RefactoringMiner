package gr.uom.java.xmi;

public interface IUMLClassMatcher {

	boolean match(UMLClass removedClass, UMLClass addedClass, String renamedFile);

}