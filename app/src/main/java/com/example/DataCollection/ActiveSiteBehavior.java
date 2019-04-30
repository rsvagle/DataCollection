package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

public class ActiveSiteBehavior implements IBehavior, Serializable {

    @SerializedName("active_behavior_type")
    @Expose
    private final String ACTIVE_BEHAVIOR_TYPE = "Active";

    /**
     * The Active Site Behavior adds a reading item to a given site's readings
     * when the addItem method is called.
     * @param siteReadings The site readings
     * @param i The item to be added
     * @param siteId The site Id for reference
     * @return
     * true if the reading was added
     */
    
    @Override
    public boolean addItem(Map<String, Item> siteReadings, Item i, String siteId) {
        if(i.getSiteID() == null){
            return false;
        }
        else {
            if (i.getSiteID().equals(siteId)) {
                siteReadings.putIfAbsent(i.getReadingID(), i);
            }
            return siteReadings.containsValue(i);
        }
    }

    /**
     * The Active Site Behavior takes a Site and Readings and adds the readings to the site.
     * @param s The site
     * @param r The set of readings
     * @return boolean indicating whether or not the readings were added
     */
    
    @Override
    public boolean addReadings(Site s, Readings r) {
        return s.addReadings(r);
    }

    /**
     * Formats the site's readings for output in the view
     * based on the specific behavior
     * @param s The site
     * @return
     * The current site to text
     */
    
    @Override
    public String toString(Site s) {
        return s.toString();
    }
    
    /**
     * Returns the Active Site Behavior string for serialization
     * @return
     * the ACTIVE_BEHAVIOR_TYPE string
     */

    @Override
    public String behaviorTypeToString() {
        return ACTIVE_BEHAVIOR_TYPE;
    }
}
