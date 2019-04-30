package com.example.DataCollection;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * A class to parse XML document.
 * This class extends DefaultHandler which provides default implementation
 * of ContentHandler that contains callback methods that receive notification
 * when an even occurs such as StartDocument, EndDocument etc.
 */


public class XMLSAXParserHandler extends DefaultHandler {

	//List to hold Item object
	private ArrayList<Item> itemList=null;
	private Item item=null;
	private Study study=null;
	private StringBuilder stringBuilder=null;
	
	
	/**
	 * getter method for itemList
	 * @return itemList
	 */
	public ArrayList<Item> getItemList(){
		return itemList;
	}
	
	/**
	 * getter method for study
	 * @return study object
	 */
	public Study getStudy() {
		return study;
	}
	
	//boolean variables to set Item and Study variables
	boolean bStudyID=false;
	boolean bStudyName=false;
	boolean bReadingType=false;
	boolean bReadingID=false;
	boolean bReadingValue=false;
	boolean bSiteID=false;
	boolean bReadingUnit=false;
	
	/**
	 * Method called at the start of a document element
	 * @param uri
	 * @param localName
	 * @param qName
	 * @param attributes
	 */
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{		
		if(qName.equalsIgnoreCase("Study")) {
			String id=attributes.getValue("id");			
			//initialize Study object and set ID attribute
			study=new Study();
			study.setStudyID(id);
			bStudyName=true;			
		}
		else if(qName.equalsIgnoreCase("Reading")) {
			String type=attributes.getValue("type");
			String id=attributes.getValue("id");
			
			//initialize item object and set ID and Type attributes
			item = new Item();
			item.setReadingID(id);
			item.setReadingType(type);
			
			//initialize list
			if(itemList==null) {
				itemList=new ArrayList<>();
			}			
		}
		else if (qName.equalsIgnoreCase("Value")) {
			//Set boolean values for fields, will be used in setting Item variables
			String unit=attributes.getValue("unit");
			item.setUnit(unit);
			bReadingValue=true;
		}
		else if(qName.equalsIgnoreCase("Site")) {
			bSiteID=true;
		}
		
		//create a data container
		stringBuilder=new StringBuilder();
	}
	
	
	/**
	 * Method called at the end of a document element
	 * @param uri
	 * @param localName
	 * @param qName
	 */	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException{
		if(bStudyName) {
			study.setStudyName(stringBuilder.toString());
			bStudyName=false;
		}
		else if(bReadingValue) {
			item.setReadingValue(Double.parseDouble(stringBuilder.toString()));
			bReadingValue=false;
		}
		else if (bSiteID) {
			item.setSiteID(stringBuilder.toString());
			bSiteID=false;
		}
		
		if(qName.equalsIgnoreCase("Reading")) {
			itemList.add(item);
		}
	}
	
	
	/**
	 * Method called with the text contents in between the start and end 
	 * tags of an XML document element.
	 * @param ch
	 * @param start
	 * @param length
	 */
	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		stringBuilder.append(new String(ch, start, length));
	}
	
}
