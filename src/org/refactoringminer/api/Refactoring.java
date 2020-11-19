package org.refactoringminer.api;

import java.io.Serializable;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.core.util.BufferRecyclers;

public interface Refactoring extends Serializable, CodeRangeProvider, IRefactoring {

	default public String toJSON() {
		StringBuilder sb = new StringBuilder();
		JsonStringEncoder encoder = BufferRecyclers.getJsonStringEncoder();
		sb.append("{").append("\n");
		sb.append("\t").append("\"").append("type").append("\"").append(": ").append("\"").append(getName()).append("\"").append(",").append("\n");
		sb.append("\t").append("\"").append("description").append("\"").append(": ").append("\"");
		encoder.quoteAsString(toString().replace('\t', ' '), sb);
		sb.append("\"").append(",").append("\n");
		sb.append("\t").append("\"").append("leftSideLocations").append("\"").append(": ").append(leftSide()).append(",").append("\n");
		sb.append("\t").append("\"").append("rightSideLocations").append("\"").append(": ").append(rightSide()).append("\n");
		sb.append("}");
		return sb.toString();
	}
}