package gr.uom.java.xmi;

import gr.uom.java.xmi.LocationInfo.CodeElementType;
import gr.uom.java.xmi.diff.CodeRange;

public interface ILocationInfo {

	String getFilePath();

	int getStartOffset();

	int getEndOffset();

	int getLength();

	int getStartLine();

	int getStartColumn();

	int getEndLine();

	int getEndColumn();

	CodeElementType getCodeElementType();

	CodeRange codeRange();

	boolean subsumes(LocationInfo other);

	int hashCode();

	boolean equals(Object obj);

}