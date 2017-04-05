package com.example.gordon.bikelist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.List;
import java.util.prefs.PreferenceChangeListener;

public class MainActivity extends AppCompatActivity {

    ConnectivityCheck myCheck;
    public List<Bike> myList;
    JSONArray myArray;
    DownloadTask myTask;
    private String myURL = "http://www.tetonsoftware.com/pets/pets.json";

    SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        myCheck = new ConnectivityCheck(this);
        if(doNetworkWirelessCheck()){
            myTask = new DownloadTask(this);
            myTask.execute(myURL);
        } else {
            Toast.makeText(this, R.string.NETWORK_NOT_REACHABLE,Toast.LENGTH_SHORT).show();
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if(key.equals("listPref")){
                    setMyURL(prefs.getString("listPref", myURL));
                    myTask.cancel(true);
                    myTask.execute(myURL);
                }
            }
        };

        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    private void loadImage() {

    }

    private boolean doNetworkWirelessCheck() {
        if(!myCheck.isNetworkReachable()&&!myCheck.isWifiReachable())
            return false;
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.pets_spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        //      R.array.spinner_list_item_array, android.R.layout.simple_spinner_item);

        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //spinner.setAdapter(adapter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    public void processJSON(String string) {
        try {
            JSONObject jsonobject = new JSONObject(string);

            myArray = jsonobject.getJSONArray("pets");

        }  catch (Exception e){
            Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
        }

    }

    public String getMyURL() {
        return myURL;
    }

    public void setMyURL(String myURL) {
        this.myURL = myURL;
    }
}
