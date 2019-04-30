package com.example.DataCollection;

public class IReaderFactory {
	private String fileType ;
	private String myfileName ;
	IReader myReader;

	/**
	 * IReaderFactory constructor
	 * @param fileName
	 */
	
	public IReaderFactory(String fileName) {
		fileType = getFileExtension(fileName);
		myfileName = fileName;
	}
	
	/**
	 * Gets the file extension from the file name.
	 * @param fileName
	 * @return
	 * The File extension
	 */

	public String getFileExtension(String fileName) {
	    int dotIndex = fileName.lastIndexOf('.');
	    return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}
	
	/**
	 * Determines which reader to use to read a given file.
	 * The method will return a .json reader if it cannot decide which reader is appropriate.
	 * @return
	 * Which reader to use
	 */

	public IReader getIReader(){
		if(fileType.equals("json")) {
			myReader = new JSONReader();
		}
		else if(fileType.equals("xml")) {
			myReader = new XMLReader();
		}
		else {
            myReader = new JSONReader();
		}
		return myReader;
	}
}
