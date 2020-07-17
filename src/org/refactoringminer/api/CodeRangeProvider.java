package org.refactoringminer.api;

import java.util.List;

import gr.uom.java.xmi.diff.CodeRange;

public interface CodeRangeProvider {
	List<CodeRange> leftSide_RENAMED();
	List<CodeRange> rightSide();
}
