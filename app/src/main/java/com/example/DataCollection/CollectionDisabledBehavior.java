package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

public class CollectionDisabledBehavior implements IBehavior, Serializable {

    @SerializedName("disabled_behavior_type")
    @Expose
    private final String DISABLE_BEHAVIOR_TYPE = "Collection Disabled";
    
    /**
     * Takes a Site reading array, and item,  and the Site ID and returns false
     * @param siteReadings The readings for the site
     * @param i The item to add
     * @param siteID The site id for reference
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
     * "Collection Disabled"
     */

    @Override
    public String toString(Site s) {
        return "Collection Disabled";
    }
    
    /**
     * @return
     * the disabled Behavior Type
     */

    @Override
    public String behaviorTypeToString() {
        return DISABLE_BEHAVIOR_TYPE;
    }
}
