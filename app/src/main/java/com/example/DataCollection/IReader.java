package com.example.DataCollection;

import java.io.InputStream;


/**
 * IReader defines the set of functions that readers must implement
 * to be considered an IReader
 */

public interface IReader {

	/**
	 * A reader must implement a method to return a set of readings.
	 * @param is
	 * @return
	 * @throws Exception
	 */
	Readings getReadings(InputStream is) throws Exception;

	/**
	 * A reader must implement a method to return a study.
	 * @param is
	 * @return
	 * @throws Exception
	 */
	Study getStudy(InputStream is) throws Exception;

}
