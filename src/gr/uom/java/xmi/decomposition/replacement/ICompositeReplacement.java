package gr.uom.java.xmi.decomposition.replacement;

import java.util.Set;

import gr.uom.java.xmi.decomposition.AbstractCodeFragment;

public interface ICompositeReplacement {

	Set<AbstractCodeFragment> getAdditionallyMatchedStatements1();

	Set<AbstractCodeFragment> getAdditionallyMatchedStatements2();

}