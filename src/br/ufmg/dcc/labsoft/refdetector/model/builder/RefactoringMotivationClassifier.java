package br.ufmg.dcc.labsoft.refdetector.model.builder;

import java.util.EnumSet;
import java.util.Set;

import br.ufmg.dcc.labsoft.refdetector.model.EntitySet;
import br.ufmg.dcc.labsoft.refdetector.model.EntitySet.Filter;
import br.ufmg.dcc.labsoft.refdetector.model.SDContainerEntity;
import br.ufmg.dcc.labsoft.refdetector.model.SDMethod;
import br.ufmg.dcc.labsoft.refdetector.model.SDModel;
import br.ufmg.dcc.labsoft.refdetector.model.SDType;

public class RefactoringMotivationClassifier {

	SDModel sd;
	
	public RefactoringMotivationClassifier(SDModel sdModel) {
		this.sd = sdModel;
	}

	public enum Motivation {
		EM_REUSE,
		EM_INTRODUCE_ALTERNATIVE_SIGNATURE,
		EM_REMOVE_DUPLICATION,
		EM_PRESERVE_BACKWARD_COMPATIBILITY,
		EM_IMPROVE_TESTABILITY,
		EM_ENABLE_OVERRIDING,
		EM_ENABLE_RECURSION,
		
		MC_RENAME_PACKAGE,
		MC_INTRODUCE_SUBPACKAGE,
		MC_CONVERT_TO_TOP_LEVEL,
		MC_CONVERT_TO_INNER,
		MC_REMOVE_FROM_DEPRECATED_CONTAINER;
	}

	public Set<Motivation> classifyExtractMethod(SDMethod extractedMethod) {
		Set<Motivation> tags = EnumSet.noneOf(Motivation.class);
		EntitySet<SDMethod> from = extractedMethod.origins();
		
		if (from.size() == 1) {
			SDMethod fromMethodBefore = from.getFirst();
			SDMethod fromMethod = sd.after(fromMethodBefore);
			if (fromMethod.delegatesTo(extractedMethod)) {
				if (fromMethod.isDeprecated()) {
					tags.add(Motivation.EM_PRESERVE_BACKWARD_COMPATIBILITY);
				} else {
					tags.add(Motivation.EM_INTRODUCE_ALTERNATIVE_SIGNATURE);
				}
			}
		}

		if (from.size() > 1) {
			tags.add(Motivation.EM_REMOVE_DUPLICATION);
		}
		
		if (!tags.contains(Motivation.EM_PRESERVE_BACKWARD_COMPATIBILITY) && !tags.contains(Motivation.EM_INTRODUCE_ALTERNATIVE_SIGNATURE)) {
			final boolean isTest = extractedMethod.isTestCode();
			if (extractedMethod.callers().suchThat(testCodeEquals(isTest)).minus(from).minus(extractedMethod).size() > 0) {
				tags.add(Motivation.EM_REUSE);
			}
		}

		if (!tags.contains(Motivation.EM_PRESERVE_BACKWARD_COMPATIBILITY)) {
			if (!extractedMethod.isTestCode() && extractedMethod.callers().suchThat(testCodeEquals(true)).minus(from).size() > 0) {
				tags.add(Motivation.EM_IMPROVE_TESTABILITY);
			}
		}

		if (extractedMethod.isOverriden()) {
			tags.add(Motivation.EM_ENABLE_OVERRIDING);
		}

		if (extractedMethod.isRecursive()) {
			tags.add(Motivation.EM_ENABLE_RECURSION);
		}
		
		return tags;
	}

	public Set<Motivation> classifyMoveClass(SDType movedType) {
		Set<Motivation> tags = EnumSet.noneOf(Motivation.class);
		
		SDContainerEntity newContainer = movedType.container();
		SDContainerEntity oldContainer = sd.before(movedType).container();
		
		if (newContainer.isPackage() && oldContainer.isPackage()) {
            if (!sd.before().exists(newContainer.fullName()) && newContainer.isNestedIn(oldContainer)) {
            	tags.add(Motivation.MC_INTRODUCE_SUBPACKAGE);
			}
			if (!sd.after().exists(oldContainer.fullName()) && !sd.before().exists(newContainer.fullName()) && !newContainer.isNestedIn(oldContainer)) {
				tags.add(Motivation.MC_RENAME_PACKAGE);
			}
		}
		
		if (newContainer.isPackage() && !oldContainer.isPackage()) {
			tags.add(Motivation.MC_CONVERT_TO_TOP_LEVEL);
		}
		if (!newContainer.isPackage() && oldContainer.isPackage()) {
			tags.add(Motivation.MC_CONVERT_TO_INNER);
		}
		
		if (!oldContainer.isPackage() && (!sd.after().exists(oldContainer) || ((SDType) sd.after(oldContainer)).isDeprecated())) {
			tags.add(Motivation.MC_REMOVE_FROM_DEPRECATED_CONTAINER);
		}
		
		return tags;
	}
	
	private Filter<SDMethod> testCodeEquals(final boolean value) {
		return new Filter<SDMethod>() {
			@Override
			public boolean accept(SDMethod method) {
				return method.isTestCode() == value;
			}
		};
	}
}
