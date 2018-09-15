/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&starttime=2018-05-01&endtime=2018-09-01&minmag=5&limit=10";
    private QuakeDataAdapter adapter;
    private TextView emptyView;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "TEST: Earthquake Activity onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        //find a reference to the empty textvVew in the layout
        //and assign it to the TextView created
        emptyView = (TextView) findViewById(R.id.empty);

        //set the EmptyView to the emptyView textView
        earthquakeListView.setEmptyView(emptyView);

        //  find a reference to the progress bar in the layout
        // and assign it to the progress bar created
        loading = (ProgressBar) findViewById(R.id.loading_spinner);

        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = new QuakeDataAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //get the string value of the earthquake uri
                Earthquake currentEarthquake = adapter.getItem(position);
                Uri earthquakeUrl = Uri.parse(currentEarthquake.getGetEarthquakeUrl());

                //parse the string as a uri in the intent
                Intent intent = new Intent(Intent.ACTION_VIEW, earthquakeUrl);
                startActivity(intent);
            }
        });

        //this initializes the network status method
        // and proceeds with running the app if there is a network else it handles it
        // by giving the user an appropriate feedback
        initialTestForNetwork();
    }

    public void initialTestForNetwork() {
        /** This apps running and functionality starts with this method
         * and as explained in the onCreate method it checks if there is an active method
         * and proceeds with running the app as appropriate depending of the network status
         */
// Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (activeNetwork != null && activeNetwork.isConnected()) {

            // Get a reference to the LoaderManager, in order to interact with loaders.
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid// because this activity implements the LoaderCallbacks interface).
            Log.i(LOG_TAG, "TEST: calling initLoader()...");
            getLoaderManager().initLoader(1, null, this).forceLoad();


        } else {

            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            loading.setVisibility(View.GONE);

            // Update empty state with no connection error message
            emptyView.setText(R.string.no_connection);

        }

        /** boolean isConnected = activeNetwork != null &&
         activeNetwork.isConnectedOrConnecting();
         return isConnected;**/
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "TEST: onCreateLoader() called...");
        return new EarthquakeLoader(EarthquakeActivity.this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        Log.i(LOG_TAG, "TEST: onLoadFinished() called...");

        //set the emptyView text to the string resource on finished loading
        emptyView.setText(R.string.empty_adapter);

        //set the visibility of the loading progress bar to gone on finished loading
        loading.setVisibility(View.GONE);

        adapter.clear();

        //if there is a valid list of {@link Earthquake}s, then add them to the adapters data set. This will trigger the Listview to update.
        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        }


        //
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        //clear the adapter of the previous earthquake data
        Log.i(LOG_TAG, "TEST: onLoaderReset() called...");
        adapter.clear();
    }


}
