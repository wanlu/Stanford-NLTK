package my.ner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ie.regexp.RegexNERSequenceClassifier;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.util.CoreMap;


public class QueryParser {
	
	public void regeClassifier() {
		BufferedReader reader = null;
		File file = new File("entities/countries_stanford_pattern/entities-detection.tsv");
		try {
			reader = new BufferedReader(new FileReader(file));
			// case sensitive
	    	long startTime = System.currentTimeMillis();
			RegexNERSequenceClassifier rc = new RegexNERSequenceClassifier(reader, false, true, null);
			String qry = "show THC STS Japan SCE^B me AMTG all china Emma companies in COOK Islands and Isle of Man and Christmas Island sorted by continent before 11/18 not include China Japan or CELESTONE SOLUSPAN or American Samoa Colombia and company not include Apple and Amazon not Apple juice or Emma or American Samoa";
	        String resultStr = rc.classifyWithInlineXML(qry);
	    	long endTime = System.currentTimeMillis();
	    	System.out.println("case DO sensitive run time used(ms): " + (endTime - startTime));
	        System.out.println(resultStr);
	        // case not sensitive
	        startTime = System.currentTimeMillis();
	        reader = new BufferedReader(new FileReader(file));
			rc = new RegexNERSequenceClassifier(reader, true, true, null);
	        resultStr = rc.classifyWithInlineXML(qry);
	    	endTime = System.currentTimeMillis();
	    	System.out.println("case NOT sensitive run time used(ms): " + (endTime - startTime));
	        System.out.println(resultStr);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	protected void showExtractEntities (File entity_file, String qry) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(entity_file));
			// case sensitive
	    	long startTime = System.currentTimeMillis();
			RegexNERSequenceClassifier rc = new RegexNERSequenceClassifier(reader, false, true, null);
	        String resultStr = rc.classifyWithInlineXML(qry);
	    	long endTime = System.currentTimeMillis();
	        System.out.println(">>>>The result: " + resultStr);
	    	System.out.println("run time used for entities detection with XML (case DO sensitive): " + (endTime - startTime) + "(ms)");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	protected List<Map<String, String>> extractEntities(File entity_file, String qry) {
		long startTime, endTime;
		ArrayList<Map<String,String>> hm_list = new ArrayList<Map<String,String>>();
		BufferedReader reader = null;
    	startTime = System.currentTimeMillis();
		try {
			reader = new BufferedReader(new FileReader(entity_file));
			// case sensitive
			RegexNERSequenceClassifier rc = new RegexNERSequenceClassifier(reader, false, true, null);
			List<List<CoreLabel>> out = rc.classify(qry);
			for (List<CoreLabel> sentence : out) {
				for (CoreLabel word :sentence) {
		        	if ( word.get(CoreAnnotations.AnswerAnnotation.class) != null ) {
		        		Map<String,Object> concept = new HashMap<String,Object>();
		        		List<Map<String,String>> conceptConstraints = new ArrayList<Map<String,String>>();
		        		Map<String, String> hm = new HashMap<String, String>();
		        		hm.put("key", word.get(CoreAnnotations.AnswerAnnotation.class));
		        		hm.put("rel", "=");
		        		hm.put("val", word.word());
		        		conceptConstraints.add(hm);
		        		concept.put("constraints", conceptConstraints);
		        		hm_list.add(hm);
		        	}
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		endTime = System.currentTimeMillis();
		System.out.println("run time used for entities detection: " + (endTime - startTime) + "(ms)");
		return hm_list;
	}
	
	protected void showDefineQueryType(String c, String qry) {
    	long startTime, endTime;
        startTime = System.currentTimeMillis();
        AbstractSequenceClassifier<CoreMap> classifier = CRFClassifier.getClassifierNoExceptions(c);
        System.out.println(">>>>The result: " + classifier.classifyWithInlineXML(qry));
      	endTime = System.currentTimeMillis();
      	System.out.println("run time used for query type defination with XML: " + (endTime - startTime) + "(ms)");
	}

	protected String defineQueryType(String serializedClassifier, String qry) {
    	long startTime, endTime;
        startTime = System.currentTimeMillis();
        AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
	      List<List<CoreLabel>> out = classifier.classify(qry);
	      for (List<CoreLabel> sentence : out) {
	        for (CoreLabel word : sentence) {
	        	if ( !word.get(CoreAnnotations.AnswerAnnotation.class).equalsIgnoreCase("O") ) {
	              	endTime = System.currentTimeMillis();
	              	System.out.println("run time used for query type defination: " + (endTime - startTime) + "(ms)");
	        		return word.get(CoreAnnotations.AnswerAnnotation.class);
	        	}
	        }
	      }
	      return null;
	}
	
	
	public QueryParser() {
		String qry;
		List<Map<String, String>> hm_list;
		// void loadClassifier(ObjectInputStream in, Properties props) throws IOException, ClassCastException, ClassNotFoundException;
        String serializedClassifier = "parser.type.ner-model.ser.gz";
//        qry = "Please predict the performance of PFIZERPEN and OGEN.";
        qry = "show me whatever the query is only need some concepts which is continent by/or/and/whatever another is country.";
        File entity_file = new File("entities/countries_stanford_pattern/entities-detection.tsv");

		// define type
      	showDefineQueryType(serializedClassifier, qry);
      	String query_type = defineQueryType(serializedClassifier, qry);
      	if(query_type != null) {
      		System.out.println("The query type is: " + query_type);
      	}

		// extract entities
      	showExtractEntities(entity_file, qry);
      	hm_list = extractEntities(entity_file, qry);
      	for (Map<String, String> hm : hm_list) {
      		System.out.println(">>>>>");
	      	for (String key : hm.keySet()) {
	      		System.out.println("Hashmap("+key+"): "+hm.get(key));
	      	}
      	}
	}
	
    public static void main(String[] args) throws IOException {
    	
    	new QueryParser();      	

    }
}