
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
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ViewStudyActivity extends AppCompatActivity {

    public static final String TAG = "ViewStudyActivity";
    private static final String STATE_FILEPATH = "STATE_FILE.json";

    private IReaderFactory iReaderFactory;
    private JSONWriter jsonWriter = new JSONWriter();
    private Study currentStudy;

    private final Record theRecord = Record.getInstance();
    private final Context myContext = this;

    private ListView siteListView;
    private SiteListAdapter adapter;
    private Button dialogCancelButton;
    private Button dialogConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_study);
        Log.d(TAG, "onCreate: Started.");

        String id = getIntent().getStringExtra("studyID");
        currentStudy = theRecord.getStudyByID(id);

        TextView studyName = findViewById(R.id.study_name_textView);
        studyName.setText(currentStudy.getStudyName());

        TextView studyId = findViewById(R.id.study_id_tv);
        String studyID = String.format("Study ID: %s", currentStudy.getStudyID());
        studyId.setText(studyID);

        Button studyAddReadingsButton = findViewById(R.id.add_readings_btn);
        Button exportStudyButton = findViewById(R.id.export_study_btn);
        Button addSiteButton = findViewById(R.id.add_site_btn);
        Button studyAddSitesFromFileButton = findViewById(R.id.add_sites_from_file_btn);

        siteListView = findViewById(R.id.site_list_view);
        adapter = new SiteListAdapter(myContext, R.layout.adapter_site_list, currentStudy.getAllSites());
        siteListView.setAdapter(adapter);

        /*
         * Manually add site button
         */
        addSiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(myContext);
                dialog.setContentView(R.layout.dialog_add_site);
                dialog.setTitle("Add a site");

                /*
                 * set the custom dialog components - text, image and button
                 */
                final EditText getIdText = dialog.findViewById(R.id.dialog_get_site_id);
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
                        String newSiteId = getIdText.getText().toString();
                        if (!newSiteId.equals("")) {
                            Site newSite = new Site(newSiteId);
                            newSite.setRecording(true);
                            theRecord.getStudyByID(currentStudy.getStudyID()).addSite(newSite);
                            SiteListAdapter newAdapter = new SiteListAdapter(myContext, R.layout.adapter_site_list, theRecord.getStudyByID(currentStudy.getStudyID()).getAllSites());
                            siteListView.setAdapter(newAdapter);
                            Toast.makeText(getApplicationContext(), "Site "+ newSiteId +" Created!",Toast.LENGTH_LONG ).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Site is NOT CREATED!",Toast.LENGTH_LONG ).show();
                        }
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        /*
         * Add Sites from file button
         */
        studyAddReadingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(myContext);
                dialog.setContentView(R.layout.dialog_add_readings);
                dialog.setTitle("Add readings");

                /*
                 * set the custom dialog components - text, image and button
                 */
                final EditText getFileNameText =  dialog.findViewById(R.id.dialog_get_file_name);
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
                            iReaderFactory = new IReaderFactory(filePath);
                            Readings importedReadings = iReaderFactory.getIReader().getReadings(fileInputStream);
                            theRecord.getStudyByID(currentStudy.getStudyID()).addReadings(importedReadings);
                            Toast.makeText(getApplicationContext(), "Readings Added successfully!", Toast.LENGTH_SHORT).show();
                            SiteListAdapter newAdapter = new SiteListAdapter(myContext, R.layout.adapter_site_list, currentStudy.getAllSites());
                            siteListView.setAdapter(newAdapter);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "File Not Found!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                        }
                        finally {
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });

        /*
         * Add Sites from file button
         */
        studyAddSitesFromFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(myContext);
                dialog.setContentView(R.layout.dialog_add_readings);
                dialog.setTitle("Add readings");

                /*
                 * set the custom dialog components - text, image and button
                 */
                final EditText getFileNameText =  dialog.findViewById(R.id.dialog_get_file_name);
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
                            iReaderFactory = new IReaderFactory(filePath);
                            Readings importedReadings = iReaderFactory.getIReader().getReadings(fileInputStream);
                            theRecord.getStudyByID(currentStudy.getStudyID()).setSitesForReading(importedReadings);
                            theRecord.getStudyByID(currentStudy.getStudyID()).addReadings(importedReadings);
                            Toast.makeText(getApplicationContext(), "Readings Added successfully!", Toast.LENGTH_SHORT).show();
                            SiteListAdapter newAdapter = new SiteListAdapter(myContext, R.layout.adapter_site_list, currentStudy.getAllSites());
                            siteListView.setAdapter(newAdapter);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "File Not Found!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                        }
                        finally {
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });

        /*
         * Export Study button
         */
        exportStudyButton.setOnClickListener(new View.OnClickListener() {
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
                        Toast.makeText(getApplicationContext(), "Operation Cancelled!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                dialogConfirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filePath = getFileNameText.getText().toString();
                        try {
                            jsonWriter.writeToFileObject(theRecord.getStudyByID(currentStudy.getStudyID()), filePath, myContext);
                            Toast.makeText(getApplicationContext(), "Successfully Exported Study!", Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "File Not Found!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                        }
                        finally {
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    public void onStop() {
        super.onStop();
        /*
         * Try to write the record to internal storage
         */
        Log.d(TAG, "onStop: Started");
        try {
            jsonWriter.writeToFileRecord(theRecord, STATE_FILEPATH, this);
        } catch (Exception e) {
            Log.d(TAG, "onStop: caught exception!");
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new SiteListAdapter(myContext, R.layout.adapter_site_list, currentStudy.getAllSites());
        siteListView.setAdapter(adapter);
    }
}

