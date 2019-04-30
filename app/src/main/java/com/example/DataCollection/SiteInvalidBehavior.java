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
     * The Site Invalid Behavior does not add an item to
     * a site when the addItem method is called
     * @param siteReadings The readings for the site
     * @param i The item to add
     * @param siteId The site id for reference
     * @return
     * false
     */

    public boolean addItem(Map<String, Item> siteReadings, Item i, String siteId) {
        return false;
    }

    /**
     * Takes a Site and readings and adds the readings to the site.
     * @param s The site
     * @param r The set of readings
     * @return boolean indicating whether or not the readings were added
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
        return "Site has been marked invalid!";
    }

    /**
     * Returns the Invalid Site Behavior string for serialization
     * @return
     * the INVALID_BEHAVIOR_TYPE string
     */

    @Override
    public String behaviorTypeToString() {
        return INVALID_BEHAVIOR_TYPE;
    }
}
