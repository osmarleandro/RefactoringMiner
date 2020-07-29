package gr.uom.java.xmi.diff;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class RenamePattern {
	private String before;
	private String after;
	
	public RenamePattern(String originalPath, String movedPath) {
		this.before = originalPath;
		this.after = movedPath;
	}

	public String getBefore() {
		return before;
	}

	public String getAfter() {
		return after;
	}

	public String toString() {
		return before + "\t->\t" + after;
	}
	
	public boolean equals(Object o) {
		if(this == o) {
    		return true;
    	}
    	if(o instanceof RenamePattern) {
    		RenamePattern pattern = (RenamePattern)o;
    		return this.before.equals(pattern.before) && this.after.equals(pattern.after);
    	}
    	return false;
	}
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((after == null) ? 0 : after.hashCode());
		result = prime * result + ((before == null) ? 0 : before.hashCode());
		return result;
	}
	
	public RenamePattern reverse() {
		return new RenamePattern(after, before);
	}

	public Set<ImmutablePair<String, String>> getInvolvedClassesAfterRefactoring(RenamePackageRefactoring renamePackageRefactoring) {
		Set<ImmutablePair<String, String>> pairs = new LinkedHashSet<ImmutablePair<String, String>>();
		for(MoveClassRefactoring ref : renamePackageRefactoring.moveClassRefactorings) {
			pairs.add(new ImmutablePair<String, String>(ref.getMovedClass().getLocationInfo().getFilePath(), ref.getMovedClassName()));
		}
		return pairs;
	}
}
