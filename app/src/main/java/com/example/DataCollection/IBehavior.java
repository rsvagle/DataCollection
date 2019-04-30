package com.example.DataCollection;

import java.util.Map;

/**
 * IBehavior defines a family of methods the must be implemented by
 * site behavior patterns
 */
public interface IBehavior {

    /**
     * Adds a reading Item to a given site's readings
     * based on the specific behavior when the addItem method is called.
     * @param siteReadings The site readings
     * @param i The item to be added
     * @param siteID The site Id for reference
     * @return
     * true if the reading was added
     */
    boolean addItem(Map<String,Item> siteReadings, Item i, String siteID);

    /**
     * Adds a set of Readings to a given site's readings
     * based on the specific behavior when the addReadings method is called.
     * @param s The site
     * @param r The readings to be added
     * @return
     * true if the reading was added
     */
    boolean addReadings(Site s, Readings r);

    /**
     * Formats the site's readings for output in the view
     * based on the specific behavior
     * @param s The site
     * @return
     * The current site to text
     */
    String toString(Site s);

    /**
     * Returns the behavior type string for serialization
     * @return
     * the _BEHAVIOR_TYPE string
     */
    String behaviorTypeToString();

}
