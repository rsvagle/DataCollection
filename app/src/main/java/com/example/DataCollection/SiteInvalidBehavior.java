package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

public class SiteInvalidBehavior implements IBehavior, Serializable {

    @SerializedName("invalid_behavior_type")
    @Expose
    private final String INVALID_BEHAVIOR_TYPE = "Invalid";

    /**
     * Checks if site contains a certain reading.
     * @param siteReadings The site readings
     * @param i The item to be added
     * @param siteId The site Id for reference
     * @return
     * false
     */

    public boolean addItem(Map<String, Item> siteReadings, Item i, String siteId) {
        return false;
    }

    /**
     * Takes a Site and reading and returns whether or not
     * the readings were added
     * @param s The site
     * @param r The set of readings
     * @return
     * false
     */

    @Override
    public boolean addReadings(Site s, Readings r) {
        return false;
    }
    
    /**
     * Writes "Site has been marked invalid!" to string
     * @return
     * "Site has been marked invalid!"
     */

    @Override
    public String toString(Site s) {
        return "Site has been marked invalid!";
    }
    
    /**
     * @return invalidBehaviorType
     */

    @Override
    public String behaviorTypeToString() {
        return INVALID_BEHAVIOR_TYPE;
    }
}
