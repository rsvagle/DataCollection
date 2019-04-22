package com.example.DataCollection;

import android.os.Environment;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 
 * This class responsibility is to read an XML file
 * It has a default constructor
 *
 */

public class XMLReader implements IReader{
	private SAXParserFactory saxParserFactory = null;
	private Study myStudy;
	Readings readings = new Readings();
	
	public XMLReader() {
		saxParserFactory = SAXParserFactory.newInstance();
	}
	
	public void readXML(String fileName) {
		try {
			File stateFile = new File(Environment.getExternalStorageDirectory() + fileName);
			FileInputStream fis = new FileInputStream(stateFile);
			SAXParser saxParser = saxParserFactory.newSAXParser();
			XMLSAXParserHandler handler = new XMLSAXParserHandler();
			saxParser.parse(fis, handler);
			//Get Item List
			myStudy = handler.getStudy();
			readings.setReadings(handler.getItemList());
			for(Item i: readings.getReadings()) {
				//correction to date and unit in the imported readings
				i.validateDate();
				i.ValidateUnit();
			}
		}
		catch (ParserConfigurationException | SAXException | IOException e) {
		    e.printStackTrace();
		}
	}
	
	/**
	 * Read the XML file
	 */
	public Readings getReadings(String fileName)  throws Exception{
		this.readXML(fileName);
		return readings;
	}
	
	/**
	 * This method returns the study imported from the input file
	 * @return
	 */
	public Study getStudy(String fileName)  throws Exception{
		this.readXML(fileName);
		myStudy.setSiteForReading(readings);
		myStudy.addReadings(readings);
		return myStudy;
	}
}
