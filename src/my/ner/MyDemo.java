package my.ner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ie.regexp.RegexNERSequenceClassifier;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.util.CoreMap;


public class MyDemo {
	
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
	
	public MyDemo() {
		this.regeClassifier();
	}
	
    public static void main(String[] args) throws IOException {
    	
    	
    	new MyDemo();
    	
    	String qry;
    	
    	long startTime = System.currentTimeMillis();

        String serializedClassifier = "my.entities.ner-model.ser.gz";
        //AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
        AbstractSequenceClassifier<CoreMap> classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
        //RegexNERSequenceClassifier<CoreMap> classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);  
        String s1 = "show Emma Japan me all China Emma companies in USA and sorted by continent before 11/18 not include China Japan or American Samoa Colombia and company not include Apple and Amazon not Apple juice or Emma or American Samoa";
//        String s1 = "The next day is Friday for Emma to eat KFC date is 12/25 or'' 11/18 <.";  
//        System.out.println(classifier.classifyToString(s1, "xml", true));
        String resultStr = classifier.classifyWithInlineXML(s1);
    	long endTime = System.currentTimeMillis();
    	System.out.println("run time used(ms): " + (endTime - startTime));
        System.out.println(resultStr);

        String s2 = "show me Emma all the company's that is founded after 11/18";  
//        System.out.println(classifier.classifyToString(s2, "xml", true));
        System.out.println(classifier.classifyWithInlineXML(s2));
        
        String s3 = "show me Jay all the company's that is founded after  12/25";  
//      System.out.println(classifier.classifyToString(s2, "xml", true));
      System.out.println(classifier.classifyWithInlineXML(s3));
      serializedClassifier = "my_test.entities.ner-model.ser.gz";
      //AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
      startTime = System.currentTimeMillis();
      classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);  
        //qry = "show Emma 12/25 and Japan me all China companies in USA and sorted by continent which not include China Japan or American Samoa Colombia";
      	qry = "Please backtest Pfizer!!!!";
        System.out.println(classifier.classifyWithInlineXML(qry));
    	endTime = System.currentTimeMillis();
    	System.out.println("run time used(ms): " + (endTime - startTime));
    	
        serializedClassifier = "my_test.entities.ner-model.ser.gz";
        //AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
        startTime = System.currentTimeMillis();
        classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);  
          qry = "Please predict the performance of Pfizer.";
          System.out.println(">>>>The results: " + classifier.classifyWithInlineXML(qry));
      	endTime = System.currentTimeMillis();
      	System.out.println("run time used(ms): " + (endTime - startTime));
    	
        serializedClassifier = "my_test.entities.ner-model.ser.gz";
        //AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
        startTime = System.currentTimeMillis();
        classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);  
          qry = "When was SomeCompany purchased by Pfizerpen?";
          System.out.println(classifier.classifyWithInlineXML(qry));
      	endTime = System.currentTimeMillis();
      	System.out.println("run time used(ms): " + (endTime - startTime));
      	
      	
    }
}
