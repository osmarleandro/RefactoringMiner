package gr.uom.java.xmi.diff;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringType;

import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.UMLType;
import gr.uom.java.xmi.decomposition.AbstractCodeMapping;

public class ChangeReturnTypeRefactoring implements Refactoring {
	private UMLType originalType;
	private UMLType changedType;
	private UMLOperation operationBefore;
	private UMLOperation operationAfter;
	private Set<AbstractCodeMapping> returnReferences;

	public ChangeReturnTypeRefactoring(UMLType originalType, UMLType changedType,
			UMLOperation operationBefore, UMLOperation operationAfter, Set<AbstractCodeMapping> returnReferences) {
		this.originalType = originalType;
		this.changedType = changedType;
		this.operationBefore = operationBefore;
		this.operationAfter = operationAfter;
		this.returnReferences = returnReferences;
	}

	public RefactoringType getRefactoringType() {
		return RefactoringType.CHANGE_RETURN_TYPE;
	}

	public String getName() {
		return getRefactoringType().getDisplayName();
	}

	public UMLType getOriginalType() {
		return originalType;
	}

	public UMLType getChangedType() {
		return changedType;
	}

	public UMLOperation getOperationBefore() {
		return operationBefore;
	}

	public UMLOperation getOperationAfter() {
		return operationAfter;
	}

	public Set<AbstractCodeMapping> getReturnReferences() {
		return returnReferences;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean qualified = originalType.equals(changedType) && !originalType.equalsQualified(changedType);
		sb.append(getName()).append("\t");
		sb.append(qualified ? originalType.toQualifiedString() : originalType.toString());
		sb.append(" to ");
		sb.append(qualified ? changedType.toQualifiedString() : changedType.toString());
		sb.append(" in method ");
		sb.append(qualified ? operationAfter.toQualifiedString() : operationAfter.toString());
		sb.append(" in class ").append(operationAfter.getClassName());
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((changedType == null) ? 0 : changedType.hashCode());
		result = prime * result + ((operationAfter == null) ? 0 : operationAfter.hashCode());
		result = prime * result + ((operationBefore == null) ? 0 : operationBefore.hashCode());
		result = prime * result + ((originalType == null) ? 0 : originalType.hashCode());
		return result;
	}

	public Set<ImmutablePair<String, String>> getInvolvedClassesBeforeRefactoring() {
		Set<ImmutablePair<String, String>> pairs = new LinkedHashSet<ImmutablePair<String, String>>();
		pairs.add(new ImmutablePair<String, String>(getOperationBefore().getLocationInfo().getFilePath(), getOperationBefore().getClassName()));
		return pairs;
	}

	public Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring() {
		Set<ImmutablePair<String, String>> pairs = new LinkedHashSet<ImmutablePair<String, String>>();
		pairs.add(new ImmutablePair<String, String>(getOperationAfter().getLocationInfo().getFilePath(), getOperationAfter().getClassName()));
		return pairs;
	}


	@Override
	public List<CodeRange> leftSide() {
		List<CodeRange> ranges = new ArrayList<CodeRange>();
		ranges.add(originalType.codeRange()
				.setDescription("original return type")
				.setCodeElement(originalType.toString()));
		return ranges;
	}

	@Override
	public List<CodeRange> rightSide() {
		List<CodeRange> ranges = new ArrayList<CodeRange>();
		ranges.add(changedType.codeRange()
				.setDescription("changed return type")
				.setCodeElement(changedType.toString()));
		return ranges;
	}
}
