package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Site  implements Serializable {

	private IBehavior myBehavior;

	@SerializedName("behavior")
	@Expose
	private String serializedBehavior;
	@SerializedName("recording")
	@Expose
	private boolean recording;
	@SerializedName("study_id")
	@Expose
	private String studyID;
	@SerializedName("active")
	@Expose
	private boolean active;
	@SerializedName("site_ID")
	@Expose
	private String siteID;
	@SerializedName("readings")
	@Expose
	private Map<String,Item> siteReadings = new HashMap<String, Item>();
	
	/**
	 * sets site functions and initialized ID. 
	 */
	
	public Site() {
		recording = false;
		active = true;
		studyID = "xxx";
	}
	
	/**
	 * Sets ID number to siteID
	 * @param id
	 */
	
	public Site (String id) {
		this();
		siteID = id;
	}
		
	/**
	 * Gets site ID number. 
	 * @return
	 * Site ID number
	 */
	public String getSiteID() {
		return siteID;
	}

	/**
	 * 
	 * @param siteID
	 */
	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}

	/**
	 * Creates and array list of items 
	 * @return
	 */
	public ArrayList<Item> getItems() {
		ArrayList<Item> myItems = new ArrayList<Item>(siteReadings.values());
		return myItems;
	}
	
	/**
	 * sets items into array list. 
	 * @param items
	 */
	public void setItems(ArrayList<Item> items) {
		for(Item i : items) {
			if (i.getSiteID() == this.siteID) {
				siteReadings.put(i.getReadingID(), i);
			}
		}
	}
	
	/**
	 * Return the status of a site either start collecting
	 * or end collecting
	 * @return
	 * return true if start collecting and false if collection ended
	 */
	public boolean isRecording() {
		return this.recording;
	}
	
	/**
	 * Set recording. 
	 * @param bool
	 */
	public void setRecording(boolean bool) {
		this.recording = bool;
	}
	
	/**
	 * Gets the Behavior
	 * @return
	 * Behavior
	 */

	public IBehavior getMyBehavior(){
		myBehavior = getSerializedBehavior();
		return this.myBehavior;
	}
	
	/**
	 * set the Bheavior
	 * @param behavior
	 */

	public void setMyBehavior(IBehavior behavior){
		this.myBehavior = behavior;
		serializedBehavior = myBehavior.behaviorTypeToString();
	}
	
	/**
	 * Get the saved Behavior
	 * @return
	 * what ever behavior it is.
	 */

	public IBehavior getSerializedBehavior(){
		if(!(serializedBehavior == null)){
			if(serializedBehavior.equals("Active")){
				return new ActiveSiteBehavior();
			}
			else if(serializedBehavior.equals("Collection Disabled")){
				return new CollectionDisabledBehavior();
			}
			else if(serializedBehavior.equals("Invalid")){
				return new SiteInvalidBehavior();
			}
			else if(serializedBehavior.equals("Complete")){
				return new CompletedStudyBehavior();
			}
			else {
				return new ActiveSiteBehavior();
			}
		}
		else{
			return new ActiveSiteBehavior();
		}
	}

	/**
	 * get study ID
	 * @return
	 * study ID
	 */
	public String getStudyID() {
		return this.studyID;
	}
	/**
	 * set study ID to study
	 * @param newStudyID
	 */
	public void setStudyID(String newStudyID) {
		this.studyID = newStudyID;
	}
	
	/**
	 * addItem: takes a single Item as a parameter, adds it to 
	 * the instance of site's list of items
	 * @param
	 * @return
	 * return true if the new item is added to site
	 */
	public boolean addItem(Item i) {
		myBehavior = getSerializedBehavior();
		return myBehavior.addItem(siteReadings, i, siteID);
	}
	
	/**
	 * addReadings: takes a Readings as a parameter, add readings to 
	 * the instance of site list of items
	 * @param
	 * @return
	 * return true if the new item is added to site
	 */
	public boolean addReadings(Readings readings) {
		boolean result = false;
		if(this.recording) {
			for(Item item : readings.getReadings()) {
				this.addItem(item);
				result = siteReadings.containsKey(item.getReadingID());
			}
		}
		return result;
	}
	
	/**
	 * Invalidates site
	 */
	public void invalidateSite() {
		this.active = false;
		this.recording = false;
		siteReadings.clear();
	}
	
	/**
	 * Determines a site to be empty or not
	 * @param
	 * @return
	 * Returns a boolean indicating the presence of items in a site's readings
	 */
	public boolean isEmpty() {
		return siteReadings.isEmpty();
	}
	
	/**
	 * checks if objects are equal
	 * @param
	 * @return
	 * return true if equal
	 */
	@Override
	public boolean equals(Object object) {
		Site nsite = null;
		if(object instanceof Site) {
			nsite = (Site)object;
		}
		//true if both siteID field are equal
		return this.siteID.equals(nsite.getSiteID());
	}
	
	/**
	 * write site readings to text
	 */
	@Override
	public String toString() {
		String text = "";
		for(Item i: siteReadings.values()) {
			text += i.toString() + "\n";
		}
		return text;
	}
}