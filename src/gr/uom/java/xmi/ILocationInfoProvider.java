package gr.uom.java.xmi;

import gr.uom.java.xmi.diff.CodeRange;

public interface ILocationInfoProvider {

	LocationInfo getLocationInfo();

	CodeRange codeRange();

}