package my.ner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


public class DoSplit {

	public DoSplit () {


		BufferedReader reader = null;
		BufferedWriter bw = null;
		try {
			File file = new File("entities/countries_stanford_pattern/all-entities.tsv");
			reader = new BufferedReader(new FileReader(file));
			String line, concept, tag;
			String[] line_splits, concept_splits;
			while ((line = reader.readLine()) != null) {
				line_splits = line.split("	");
				tag = line_splits[1];
				// dealConcept(line_splits[0]);
				concept_splits = line_splits[0].split(" ");
				for (String token : concept_splits) {
					System.out.println(token + "	" + tag);
					bw = new BufferedWriter( new FileWriter("entities/my.countries.tsv", true) );
					bw.write(token + "	" + tag + "\n");
					bw.flush();
				}
			}
//
//			bw = new BufferedWriter( new FileWriter("civic_data.sql", true) );
//			bw.write("");
//			bw.newLine();
//			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
				try {
					bw.close();
				} catch (IOException ioe2) {
	    			  
				}
		}
	}

	public static void main(String[] argv) {
		new DoSplit();
	}
}
