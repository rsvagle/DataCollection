package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

public class CompletedStudyBehavior implements IBehavior, Serializable {

    @SerializedName("completed_behavior_type")
    @Expose
    private final String COMPLETE_BEHAVIOR_TYPE = "Complete";

    /**
     * Checks if site contains a certain reading.
     * @param siteReadings The site readings
     * @param i The item to be added
     * @param siteID The site ID for reference
     * @return
     * false
     */
    
    @Override
    public boolean addItem(Map<String, Item> siteReadings, Item i, String siteID) {
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
     * Writes collection disabled to text
     * @param s The site
     * @return
     * "Completed Study"
     */

    @Override
    public String toString(Site s) {
        return "Completed Study\n" + s.toString();
    }
    
    /**
     * @return
     * the Completed Behavior Type
     */

    @Override
    public String behaviorTypeToString() {
        return COMPLETE_BEHAVIOR_TYPE;
    }
}
