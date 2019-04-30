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
     * The Collection Disabled Behavior does not
     * add an item to a site when the addItem method is called
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
     * The Collection Disabled Site Behavior takes a Site
     * and Readings and does not add the readings to the site.
     * @param s The site
     * @param r The set of readings
     * @return boolean indicating the readings were not added
     */

    @Override
    public boolean addReadings(Site s, Readings r) {
        return false;
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
        return "Collection Disabled";
    }

    /**
     * Returns the Collection Disabled Site Behavior string for serialization
     * @return
     * the DISABLE_BEHAVIOR_TYPE string
     */

    @Override
    public String behaviorTypeToString() {
        return DISABLE_BEHAVIOR_TYPE;
    }
}
