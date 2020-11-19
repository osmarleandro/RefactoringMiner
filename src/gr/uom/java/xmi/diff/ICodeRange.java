package gr.uom.java.xmi.diff;

import java.util.List;

import gr.uom.java.xmi.LocationInfo.CodeElementType;
import gr.uom.java.xmi.decomposition.AbstractCodeFragment;

public interface ICodeRange {

	String getFilePath();

	int getStartLine();

	int getEndLine();

	int getStartColumn();

	int getEndColumn();

	CodeElementType getCodeElementType();

	String getDescription();

	ICodeRange setDescription(String description);

	String getCodeElement();

	ICodeRange setCodeElement(String codeElement);

	boolean subsumes(CodeRange other);

	boolean subsumes(List<? extends AbstractCodeFragment> statements);

	String toString();

}