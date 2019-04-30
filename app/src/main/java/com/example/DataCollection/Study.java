package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Study implements Serializable {
	
	@SerializedName("study_name")
	@Expose
	private String studyName;	
	@SerializedName("study_id")
	@Expose
	private String studyID;	
	@SerializedName("sites")
	@Expose
	private Map<String,Site> associatedSites = new HashMap<String, Site>();
	
	/**
	 * initializes the study
	 */
	
	public Study() {
		this.studyID = "";
		this.studyName = "";
	}
	
	/**
	 * sets study name and ID
	 * @param studyID
	 * @param studyName
	 */
  
	public Study(String studyID, String studyName) {
		this.studyID = studyID;
		this.studyName = studyName;		
	}
	
	/**
	 * Gets the study name
	 * @return
	 * studyName
	 */

	public String getStudyName() {
		return studyName;
	}
	
	/**
	 * Set the study Name
	 * @param studyName
	 */

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}
	
	/**
	 * Get the studyID
	 * @return
	 * studyID
	 */

	public String getStudyID() {
		return studyID;
	}
	
	/**
	 * Set the study ID
	 * @param studyId
	 */

	public void setStudyID(String studyId) {
		this.studyID = studyId;
	}
	
	/**
	 * The addSite method takes a Site object and adds it to the study.
	 * @param site
	 */
	public void addSite(Site site) {
		site.setStudyID(this.getStudyID());
		associatedSites.put(site.getSiteID(), site);
	}
	
	/**
	 * Set all sites in the study to start collecting
	 */
	public void startAllSiteCollection() {
		for(Site s : associatedSites.values()) {
			s.setRecording(true);
		}
	}
	
	/**
	 * End collection for all sites in the study
	 */
	public void endAllSiteCollection() {
		for(Site s: associatedSites.values()) {
			s.setRecording(false);
		}
	}
	
	/**
	 * The addItem method takes a Item object and adds it to the appropriate
	 * site in the study. If the site doesn't exist within the study, create it
	 * @param i
	 * @return
	 * A boolean value is returned indicating whether or not the item is in the site
	 */
	public boolean addItem(Item i) {
		if(associatedSites.containsKey(i.getSiteID())) {
			associatedSites.get(i.getSiteID()).addItem(i);
		}
		else {
			Site newSite = new Site(i.getSiteID());
			associatedSites.putIfAbsent(i.getSiteID(), newSite);
			associatedSites.get(i.getSiteID()).addItem(i);
		}
		return associatedSites.get(i.getSiteID()).getItems().contains(i);
	}
	
	/**
	 * The addReadings method takes a Readings object and adds the items to the appropriate
	 * site in the study.
	 * @param r
	 * @return
	 * void
	 */
	public void addReadings(Readings r) {
		for(Item i : r.getReadings()) {
			if(associatedSites.containsKey(i.getSiteID())) {
				associatedSites.get(i.getSiteID()).addItem(i);
			}
		}
	}
	
	/**
	 * setSitesForReading method takes a Readings object and sets up a list
	 * of sites associated with each reading object under a study
	 * @param reading
	 * @return
	 * void
	 */
	public void setSitesForReading(Readings reading) {
		Iterator<Item> itemIterator = reading.getReadings().iterator();
		//Iterate over readings to create site and associate the items to it
		while (itemIterator.hasNext()) {
			Item currentItem = itemIterator.next();
			if(associatedSites.containsKey(currentItem.getSiteID())) {
				associatedSites.get(currentItem.getSiteID()).setRecording(true);
			}
			else if (currentItem.getSiteID() != null) {
				Site currentSite = new Site(currentItem.getSiteID());
				currentSite.setRecording(true);
				currentSite.setStudyID(this.getStudyID());
				this.addSite(currentSite);
			}
		}
	}
	
	/**
	 * This method takes an ID string as parameter and return 
	 * a site from study that match the input string ID
	 * @param siteId
	 * @return
	 * a Site is return to the caller
	 */
	public Site getSiteByID(String siteId) {
		return associatedSites.get(siteId);
	}
	
	/**
	 * Return a list of sites in study
	 * @return
	 *  all sites
	 */
	public ArrayList<Site> getAllSites() {
		ArrayList<Site> mySites = new ArrayList<Site>(associatedSites.values());
		return mySites;
	}
	
	/**
	 * Checks another object for equality
	 */
	@Override
	public boolean equals(Object o) {
		Study nstudy = null;
		if(o instanceof Study) {
			nstudy = (Study)o;
		}
		return this.studyID.equals(nstudy.getStudyID()) && this.studyName.equals(nstudy.getStudyName());
	}
	
	/**
	 * Returns a string representation of the study
	 */
	@Override
	public String toString() {
		String text = "";
		for(Site s : associatedSites.values()) {
			text += s.toString() + "\n";
		}
		return "\nStudy_ID: " + this.studyID +"\nStudy_Name: "+ this.studyName +"\n" + text;
	}

}