package com.example.hava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class search_results extends AppCompatActivity {
    //
    //Results display area.
    ListView list_view;
    //
    //
    ArrayList<String> tripList;
    //
    //
    ArrayAdapter<String> listAdapter;
    //
    //
    Handler mainHandler= new Handler();
    //
    //
    ProgressDialog progressDialog;
    //
    //
    String results;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        //
        //Get a user's selection.
        Intent data_intent = getIntent();
        //
        //Collect the data sent by the main activity in a bundle.
        Bundle extras = data_intent.getExtras();
        //
        //The user input.
        String keyword = extras.getString("keyword");
        String toggle_switch_val = extras.getString("toggle_switch_val");
        String radioButton1_val = extras.getString("radioButton1_val");
        String radioButton2_val = extras.getString("radioButton2_val");
        //
        //The data display section.
        list_view = findViewById(R.id.list_view);
        //
        //
        initializeTripList();
        //
        //Instantiate the class that fetches data in the background.
        new fetch_data().start();
    }
    //
    //Append the data to the list view.
    private void initializeTripList() {
        //
        //
        tripList = new ArrayList<>();
        //
        //
        listAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1,tripList);
        //
        //Set the listview adapter.
        list_view.setAdapter(listAdapter);
    }

    //
    //Fetch the data in the background.
    class fetch_data extends Thread{
        //
        String data = "";
        //
        @Override
        public void run() {
            //
            //
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    //
                    //
                    progressDialog = new ProgressDialog(search_results.this);
                    progressDialog.setMessage("Loading data");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });
            //
            //
            try {
                //
                //The url to fetch the data from
                URL url = new URL("https://hr.hava.bz/trips/recent.json");
                //
                //
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //
                //
                InputStream inputStream = httpURLConnection.getInputStream();
                //
                //
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                //
                //
                String line;
                //
                //Loop through the data stream.
                while((line = bufferedReader.readLine()) != null){
                    //
                    //
                    data = data + line;
                }
                //
                //
                if(!data.isEmpty()){
                    //
                    //
                    JSONObject jsonObject = new JSONObject(data);
                    //
                    //
                    JSONArray trips = jsonObject.getJSONArray("trips");
                    //
                    tripList.clear();
                    //
                    for (int i = 0; i < trips.length(); i++) {
                        //
                        //
                        JSONObject names = trips.getJSONObject(i);
                        //
                        //Get some results.
                        results += "Request Date:   ";
                        results += names.getString("request_date");
                        results += "\n\n";
                        results += "Pickup Location:       ";
                        results += names.getString("pickup_location");
                        results += "\n\n";
                        results += "Dropoff Location:       ";
                        results += names.getString("dropoff_location");
                        results += "\n\n";
                        results += "Cost:       ";
                        results += names.getString("cost");
                        results += " ";
                        results += names.getString("cost_unit");
                        results += "\n\n";
                        results += "Driver's rating:       ";
                        results += names.getString("driver_rating");
                        results += "\n\n";
                        results += "Status:       ";
                        results += names.getString("status");
                        results += "\n\n";
                    }
                    //
                    //
                    tripList.add(results);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //
            //
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    //
                    //
                    if(progressDialog.isShowing()){
                        //
                        //
                        progressDialog.dismiss();
                        //
                        //
                        listAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}