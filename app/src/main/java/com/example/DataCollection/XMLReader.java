package com.example.DataCollection;

import android.os.Environment;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
	
	/**
	 * Initializes the parser factory
	 */
	
	public XMLReader() {
		saxParserFactory = SAXParserFactory.newInstance();
	}
	
	/**
	 * parses XML file
	 * @param is
	 */
	
	public void readXML(InputStream is) {
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			XMLSAXParserHandler handler = new XMLSAXParserHandler();
			saxParser.parse(is, handler);
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
	 * @return
	 * readings list
	 */
	public Readings getReadings(InputStream is)  throws Exception{
		this.readXML(is);
		return readings;
	}
	
	/**
	 * This method returns the study imported from the input file
	 * @return
	 * myStudy
	 */
	public Study getStudy(InputStream is)  throws Exception{
		this.readXML(is);
		myStudy.setSitesForReading(readings);
		myStudy.addReadings(readings);
		return myStudy;
	}
}
