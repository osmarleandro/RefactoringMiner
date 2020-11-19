package gr.uom.java.xmi;

public interface IUMLAnonymousClass {

	boolean isDirectlyNested();

	String getCodePath();

	String getName();

	boolean equals(Object o);

	String toString();

	int compareTo(IUMLAnonymousClass umlClass);

	boolean isSingleAbstractMethodInterface();

	boolean isInterface();

}