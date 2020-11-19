package org.refactoringminer.api;

import java.util.List;

import gr.uom.java.xmi.diff.CodeRange;

public interface CodeRangeProvider extends ICodeRangeProvider {
	List<CodeRange> leftSide();
	List<CodeRange> rightSide();
}
