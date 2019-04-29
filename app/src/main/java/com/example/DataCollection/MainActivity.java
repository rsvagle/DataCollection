package com.example.DataCollection;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private static final String STATE_FILEPATH = "STATE_FILE.json";

    private IReaderFactory iReaderFactory;
    private JSONWriter jsonWriter = new JSONWriter();
    private JSONReader jsonReader = new JSONReader();

    private static Record record;
    private final Context myContext = this;

    Button dialogCancelButton;
    Button dialogConfirmButton;

    
	/**
	 * onCreate starts up the GUI for the android application, while 
	 * it also checks to see if a previous state has been saved to reopen. 
	 *
	 * void
	 */
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started.");

        /*
         * Try to load the record from internal storage. If it fails, create a blank record.
         */
        try {
            FileInputStream fileInputStream = myContext.openFileInput(STATE_FILEPATH);
            record = jsonReader.loadState(fileInputStream);
        } catch (Exception e) {
            record = Record.getInstance();
            e.printStackTrace();
        }


        /*
         * Initialize Buttons and Views
         */
        Button studyImportButton = findViewById(R.id.ImportDataButton);
        Button recordAddReadingsButton = findViewById(R.id.add_readings_btn);
        Button recordExportButton = findViewById(R.id.exportRecordBtn);
        Button addStudyButton = findViewById(R.id.add_study_btn);
        final ListView studyListView = findViewById(R.id.StudyListView);
        final StudyListAdapter adapter = new StudyListAdapter(this, R.layout.adapter_study_list, record.getStudies());
        studyListView.setAdapter(adapter);


        /*
         * Set up buttons
         */

        /*
         *  Import Study from file button
         */
        studyImportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(myContext);
                dialog.setContentView(R.layout.dialog_add_readings);
                dialog.setTitle("Import readings");

                /*
                 * set the custom dialog components - text, image and button
                 */

                final EditText getFileNameText = dialog.findViewById(R.id.dialog_get_file_name);

                dialogCancelButton = dialog.findViewById(R.id.dialog_cancel_btn);
                dialogConfirmButton = dialog.findViewById(R.id.dialog_confirm_btn);
                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Operation Cancelled", Toast.LENGTH_SHORT)
                                .show();
                        dialog.dismiss();
                    }
                });
                dialogConfirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filePath = getFileNameText.getText().toString();
                        try {
                            FileInputStream fileInputStream = myContext.openFileInput(filePath);
                            iReaderFactory = new IReaderFactory(filePath);
                            Study importedStudy = iReaderFactory.getIReader().getStudy(fileInputStream);
                            record.importStudy(importedStudy);
                            studyListView.setAdapter(adapter);
                            Toast.makeText(getApplicationContext(), "Study Successfully Imported!", Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "File Not Found!", Toast.LENGTH_SHORT).show();
                            getFileNameText.requestFocus();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Something went Wrong", Toast.LENGTH_LONG).show();
                        } finally {
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });

        /*
         *  Add Readings from file button
         */
        recordAddReadingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(myContext);
                dialog.setContentView(R.layout.dialog_add_readings);
                dialog.setTitle("Add readings");

                /*
                 * set the custom dialog components - text, image and button
                 */

                final EditText getFileNameText = dialog.findViewById(R.id.dialog_get_file_name);

                dialogCancelButton = dialog.findViewById(R.id.dialog_cancel_btn);
                dialogConfirmButton = dialog.findViewById(R.id.dialog_confirm_btn);
                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Operation Cancelled!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialogConfirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filePath = getFileNameText.getText().toString();
                        try {
                            FileInputStream fileInputStream = myContext.openFileInput(filePath);
                            IReaderFactory fac = new IReaderFactory(filePath);
                            Readings importedReadings = fac.getIReader().getReadings(fileInputStream);
                            record.addReadings(importedReadings);
                            studyListView.setAdapter(adapter);
                            Toast.makeText(getApplicationContext(), "Readings Added Successfully!", Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "File Not Found!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Something went Wrong", Toast.LENGTH_LONG).show();
                        } finally {
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });

        /*
         *  Manually add a study button
         */
        addStudyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(myContext);
                dialog.setContentView(R.layout.dialog_add_study);
                dialog.setTitle("Add a study");

                /*
                 * set the custom dialog components - text, image and button
                 */

                final EditText getIdText = dialog.findViewById(R.id.dialog_get_study_id);
                final EditText getNameText = dialog.findViewById(R.id.dialog_get_study_name);

                dialogCancelButton = dialog.findViewById(R.id.dialog_cancel_btn);
                dialogConfirmButton = dialog.findViewById(R.id.dialog_confirm_btn);

                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Operation Cancelled!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialogConfirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newStudyId = getIdText.getText().toString();
                        String newStudyName = getNameText.getText().toString();
                        if(!newStudyId.equals("") && !newStudyName.equals("")){
                            Study newStudy = new Study(newStudyId, newStudyName);
                            record.addStudy(newStudy);
                            studyListView.setAdapter(adapter);
                            Toast.makeText(getApplicationContext(), "Study Created Successfully!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Study is NOT CREATED!", Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        /*
         *  Export to file button
         */
        recordExportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(myContext);
                dialog.setContentView(R.layout.dialog_export_to_file);
                dialog.setTitle("Export File");

                /*
                 * set the custom dialog components - text, image and button
                 */

                final EditText getFileNameText = dialog.findViewById(R.id.dialog_get_file_name);

                dialogCancelButton = dialog.findViewById(R.id.dialog_cancel_btn);
                dialogConfirmButton = dialog.findViewById(R.id.dialog_confirm_btn);

                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Operation Cancelled", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                dialogConfirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filePath = getFileNameText.getText().toString();
                        try {
                            jsonWriter.writeToFileRecord(record, filePath, myContext);
                            Toast.makeText(getApplicationContext(), "File Exported Successfully!", Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "File Not Found!", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Something went Wrong", Toast.LENGTH_LONG).show();
                        } finally {
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });
    }
    
	/**
	 * onStop saved the record to internal storage.
	 *
	 * void
	 */

    public void onStop() {
        super.onStop();
        /*
            Try to write the record to internal storage
         */
        Log.d(TAG, "onStop: Started");
        try {
            Log.d(TAG, "onStop: WritingToFile");
            jsonWriter.writeToFileRecord(record, STATE_FILEPATH, myContext);
        } catch (Exception e) {
            Log.d(TAG, "onStop: caught exception!");
            e.printStackTrace();
        }
    }

}
