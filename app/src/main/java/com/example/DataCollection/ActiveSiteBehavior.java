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
     * Checks if site contains a certain reading.
     * @param siteReadings The site readings
     * @param i The item to be added
     * @param siteId The site Id for reference
     * @return
     * true if site contains reading
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
     * Takes a Site and reading and returns whether or not
     * the readings were added
     * @param s The site
     * @param r The set of readings
     * @return boolean
     *
     */
    
    @Override
    public boolean addReadings(Site s, Readings r) {
        return s.addReadings(r);
    }

    /**
     * 
     * @param s The site
     * @return
     * The current site to text
     */
    
    @Override
    public String toString(Site s) {
        return s.toString();
    }
    
    /**
     * 
     * @return
     * the active Behavior Type
     */

    @Override
    public String behaviorTypeToString() {
        return ACTIVE_BEHAVIOR_TYPE;
    }
}
