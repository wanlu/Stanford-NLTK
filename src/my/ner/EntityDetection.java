package my.ner;


import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class EntityDetection {
	
	public EntityDetection( String query ) throws ClassCastException, ClassNotFoundException, IOException {
	    String serializedClassifier = "classifiers/english.all.3class.distsim.crf.ser.gz";
	    AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
	    System.out.println("Before Query Parser: " + query);
	    System.out.println("After Query Parser: " + classifier.classifyWithInlineXML(query));
	}

	public List<Map<String,Object>> parse(String query) {
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
		return returnList;
	}
	
	public static void main(String[] argv) throws ClassCastException, ClassNotFoundException, IOException {
		String query = "show me all companies in United States and sorted by continent not include Asia and company not include Apple and Amazon not Apple juice or Natwest";
		new EntityDetection(query);
	}
	
}
