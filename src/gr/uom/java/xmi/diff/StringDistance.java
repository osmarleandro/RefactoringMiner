package gr.uom.java.xmi.diff;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class StringDistance {
	
	public static int editDistance(String a, String b) {
		return new LevenshteinDistance().apply(a, b);
	}
}
