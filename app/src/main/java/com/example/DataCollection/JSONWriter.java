package com.example.DataCollection;

import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

public class JSONWriter {

    public JSONWriter(){

    }
    /**
     * WriteToFile method takes as a parameters a list of sites
     * and a file name. It write the sites in the list to a file on the disk
     */
    public void writeToFile(Record studyRecord, File outputFile) throws Exception{

        //path and construct of the output file
        File myOutputFile = outputFile;
        FileOutputStream fos = new FileOutputStream(myOutputFile);

        //Write JSON object in pretty format
        Gson myGson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        //new instance of JSON Object that will contains iterations of study
        JsonArray jObject = new JsonArray();
        Iterator<Study> it = studyRecord.iterator();
        while(it.hasNext()) {
            jObject.add(myGson.toJsonTree(it.next()));
        }

        String jsonString = myGson.toJson(jObject);
        fos.write(jsonString.getBytes());
        fos.close();
    }
}
