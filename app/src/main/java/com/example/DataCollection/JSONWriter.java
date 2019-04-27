package com.example.DataCollection;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.FileOutputStream;
import java.util.Iterator;

public class JSONWriter {

    public static final String TAG = "JSONWriter";

    public JSONWriter(){

    }
    /**
     * WriteToFileObject method takes as parameters an object to write,
     * a file name, and a context. It writes the object to a file on the storage
     * 
     * @param o, outputFileName, context
     * 
     * @return
     * void
     * 
     * 
     */
    public void writeToFileObject(Object o, String outputFileName, Context context) throws Exception{
        String myJson;
        Gson myGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        myJson = myGson.toJson(o);

        FileOutputStream fos;
        fos = context.openFileOutput(outputFileName, Context.MODE_PRIVATE);
        fos.write(myJson.getBytes());
        fos.close();
    }
    
    
    /**
     * WriteToFileRecord method takes as parameters an object to write,
     * a file name, and a context. It writes the record to a file for storage
     * 
     * @param studyRecord, outputFileName, context
     * 
     * @return
     * void
     */

    public void writeToFileRecord(Record studyRecord, String outputFileName, Context context) throws Exception{
        String myJson;
        Gson myGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        JsonArray myObj = new JsonArray();
        Iterator<Study> it = studyRecord.iterator();
        while(it.hasNext()) {
            myObj.add(myGson.toJsonTree(it.next()));
        }
        myJson = myGson.toJson(myObj);

        FileOutputStream fos;
        fos = context.openFileOutput(outputFileName, Context.MODE_PRIVATE);
        fos.write(myJson.getBytes());
        fos.close();
    }
}
