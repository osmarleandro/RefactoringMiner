package org.refactoringminer.api;

import java.util.List;

import gr.uom.java.xmi.diff.CodeRange_RENAMED;

public interface CodeRangeProvider {
	List<CodeRange_RENAMED> leftSide();
	List<CodeRange_RENAMED> rightSide();
}
