package gr.uom.java.xmi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gr.uom.java.xmi.diff.StringDistance;

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

	public UMLOperation operationWithTheSameSignatureIgnoringChangedTypes(UMLOperation operation) {
		List<UMLOperation> matchingOperations = new ArrayList<UMLOperation>();
		for(UMLOperation originalOperation : operations) {
			boolean matchesOperation = isInterface() ?
				originalOperation.equalSignatureIgnoringChangedTypes(operation) :
				originalOperation.equalSignatureWithIdenticalNameIgnoringChangedTypes(operation);
			if(matchesOperation) {
				boolean originalOperationEmptyBody = originalOperation.getBody() == null || originalOperation.hasEmptyBody();
				boolean operationEmptyBody = operation.getBody() == null || operation.hasEmptyBody();
				if(originalOperationEmptyBody == operationEmptyBody)
					matchingOperations.add(originalOperation);
			}
		}
		if(matchingOperations.size() == 1) {
			return matchingOperations.get(0);
		}
		else if(matchingOperations.size() > 1) {
			int minDistance = StringDistance.editDistance(matchingOperations.get(0).toString(), operation.toString());
			UMLOperation matchingOperation = matchingOperations.get(0);
			for(int i=1; i<matchingOperations.size(); i++) {
				int distance = StringDistance.editDistance(matchingOperations.get(i).toString(), operation.toString());
				if(distance < minDistance) {
					minDistance = distance;
					matchingOperation = matchingOperations.get(i);
				}
			}
			return matchingOperation;
		}
		return null;
	}
}
