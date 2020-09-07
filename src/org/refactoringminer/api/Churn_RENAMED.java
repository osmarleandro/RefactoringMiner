package org.refactoringminer.api;

public class Churn_RENAMED {
	
	private final int linesAdded;
	private final int linesRemoved;
	
	public Churn_RENAMED(int linesAdded, int linesRemoved) {
		this.linesAdded = linesAdded;
		this.linesRemoved = linesRemoved;
	}

	public int getLinesAdded() {
		return linesAdded;
	}

	public int getLinesRemoved() {
		return linesRemoved;
	}
	
	public int getChurn() {
		return linesAdded + linesRemoved;
	}
}
