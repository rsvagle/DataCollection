package com.example.DataCollection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * JSONReader reads in .json files to be used in the data
 * collection application.
 */
public class JSONReader implements IReader{

	private static Study myStudy;
	private static Readings myReadings = new Readings();
	
	public JSONReader() {
		
	}

	/**
	 * The readFile method takes a JSON file and reads it
	 * in as a set of readings.
	 * @param is The input stream
	 * @return
	 * void
	 */

	public void readFile(InputStream is) throws IOException {
		InputStreamReader isr = new InputStreamReader(is);
		Gson myGson = new Gson();
		myReadings = myGson.fromJson(isr, Readings.class);
		isr.close();
		
		// Validate each item
		for(Item i : myReadings.getReadings()) {
			i.validateDate();
			i.ValidateUnit();
		}
	}
	
	/**
	 * Returns a Readings object that contains the collection of deserialized items
	 * @param is The input stream
	 * @return
	 * A readings object containing the items in the file
	 */	
	
	public Readings getReadings(InputStream is) throws Exception{
		this.readFile(is);
		return myReadings;
	}
	
	/**
	 * The getStudy method takes the input stream and parses out a study.
	 * @param is the input stream
	 * @return
	 * the study out of the file and adds it to the reading. 
	 */
	
	
	public Study getStudy(InputStream is) throws Exception{
		this.readFile(is);
		myStudy = new Study("xxx","UnknownStudy");
		myStudy.setSitesForReading(myReadings);
		myStudy.addReadings(myReadings);
		return myStudy;
	}
	
	/**
	 * loadSate loads previous state of the program. 
	 * @param is The input stream
	 * @return
	 * A record that encapsulates the previous state.
	 */
	
	public Record loadState(InputStream is) throws Exception {

		Record myRecord = Record.getInstance();
		int size = is.available();
		byte[] buffer = new byte[size];
		is.read(buffer);
		is.close();
		String jsonString = new String(buffer, "UTF-8");
		Study[] importedStudies;
		Gson myGson = new GsonBuilder().setLenient().create();
		importedStudies = myGson.fromJson(jsonString, Study[].class);
		for(Study s : importedStudies) {
			myRecord.importStudy(s);
		}
		return myRecord;
	}
}
