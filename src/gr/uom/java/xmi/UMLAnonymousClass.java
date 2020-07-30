package gr.uom.java.xmi;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.refactoringminer.util.PrefixSuffixUtils;

import gr.uom.java.xmi.diff.RenamePattern;

public class UMLAnonymousClass extends UMLAbstractClass implements Comparable<UMLAnonymousClass>, Serializable, LocationInfoProvider {
	private String codePath;
	
	public UMLAnonymousClass(String packageName, String name, String codePath, LocationInfo locationInfo) {
    	super();
		this.packageName = packageName;
        this.name = name;
        this.locationInfo = locationInfo;
        this.codePath = codePath;
    }

	public boolean isDirectlyNested() {
		return !name.contains(".");
	}

	public String getCodePath() {
		if(packageName.equals(""))
    		return codePath;
    	else
    		return packageName + "." + codePath;
	}

    public String getName() {
    	if(packageName.equals(""))
    		return name;
    	else
    		return packageName + "." + name;
    }

    public boolean equals(Object o) {
    	if(this == o) {
    		return true;
    	}
    	
    	if(o instanceof UMLAnonymousClass) {
    		UMLAnonymousClass umlClass = (UMLAnonymousClass)o;
    		return this.packageName.equals(umlClass.packageName) && this.attributes.equals(umlClass.attributes) &&
    				this.operations.equals(umlClass.operations) && this.getSourceFile().equals(umlClass.getSourceFile());
    	}
    	return false;
    }

    public String toString() {
    	return getName();
    }

	public int compareTo(UMLAnonymousClass umlClass) {
		return this.toString().compareTo(umlClass.toString());
	}

	public boolean isSingleAbstractMethodInterface() {
		return false;
	}

	public boolean isInterface() {
		return false;
	}

	public boolean hasCommonAttributesAndOperations(UMLAbstractClass umlClass) {
		String commonPrefix = PrefixSuffixUtils.longestCommonPrefix(this.name, umlClass.name);
		String commonSuffix = PrefixSuffixUtils.longestCommonSuffix(this.name, umlClass.name);
		RenamePattern pattern = null;
		if(!commonPrefix.isEmpty() && !commonSuffix.isEmpty()) {
			int beginIndexS1 = this.name.indexOf(commonPrefix) + commonPrefix.length();
			int endIndexS1 = this.name.lastIndexOf(commonSuffix);
			String diff1 = beginIndexS1 > endIndexS1 ? "" :	this.name.substring(beginIndexS1, endIndexS1);
			int beginIndexS2 = umlClass.name.indexOf(commonPrefix) + commonPrefix.length();
			int endIndexS2 = umlClass.name.lastIndexOf(commonSuffix);
			String diff2 = beginIndexS2 > endIndexS2 ? "" :	umlClass.name.substring(beginIndexS2, endIndexS2);
			pattern = new RenamePattern(diff1, diff2);
		}
		Set<UMLOperation> commonOperations = new LinkedHashSet<UMLOperation>();
		int totalOperations = 0;
		for(UMLOperation operation : operations) {
			if(!operation.isConstructor() && !operation.overridesObject()) {
				totalOperations++;
	    		if(umlClass.containsOperationWithTheSameSignatureIgnoringChangedTypes(operation) ||
	    				(pattern != null && umlClass.containsOperationWithTheSameRenamePattern(operation, pattern.reverse()))) {
	    			commonOperations.add(operation);
	    		}
			}
		}
		for(UMLOperation operation : umlClass.operations) {
			if(!operation.isConstructor() && !operation.overridesObject()) {
				totalOperations++;
	    		if(this.containsOperationWithTheSameSignatureIgnoringChangedTypes(operation) ||
	    				(pattern != null && this.containsOperationWithTheSameRenamePattern(operation, pattern))) {
	    			commonOperations.add(operation);
	    		}
			}
		}
		Set<UMLAttribute> commonAttributes = new LinkedHashSet<UMLAttribute>();
		int totalAttributes = 0;
		for(UMLAttribute attribute : attributes) {
			totalAttributes++;
			if(umlClass.containsAttributeWithTheSameNameIgnoringChangedType(attribute) ||
					(pattern != null && umlClass.containsAttributeWithTheSameRenamePattern(attribute, pattern.reverse()))) {
				commonAttributes.add(attribute);
			}
		}
		for(UMLAttribute attribute : umlClass.attributes) {
			totalAttributes++;
			if(this.containsAttributeWithTheSameNameIgnoringChangedType(attribute) ||
					(pattern != null && this.containsAttributeWithTheSameRenamePattern(attribute, pattern))) {
				commonAttributes.add(attribute);
			}
		}
		if(this.isTestClass() && umlClass.isTestClass()) {
			return commonOperations.size() > Math.floor(totalOperations/2.0) || commonOperations.containsAll(this.operations);
		}
		if(this.isSingleAbstractMethodInterface() && umlClass.isSingleAbstractMethodInterface()) {
			return commonOperations.size() == totalOperations;
		}
		return (commonOperations.size() > Math.floor(totalOperations/2.0) && (commonAttributes.size() > 2 || totalAttributes == 0)) ||
				(commonOperations.size() > Math.floor(totalOperations/3.0*2.0) && (commonAttributes.size() >= 2 || totalAttributes == 0)) ||
				(commonAttributes.size() > Math.floor(totalAttributes/2.0) && (commonOperations.size() > 2 || totalOperations == 0)) ||
				(commonOperations.size() == totalOperations && commonOperations.size() > 2 && this.attributes.size() == umlClass.attributes.size()) ||
				(commonOperations.size() == totalOperations && commonOperations.size() > 2 && totalAttributes == 1);
	}
}
