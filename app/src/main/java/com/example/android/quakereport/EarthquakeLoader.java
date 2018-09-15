package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader< List<Earthquake>> {
    List<Earthquake> result;
    private String urls;
  private static final String LOG_TAG = EarthquakeActivity.class.getName();


  public EarthquakeLoader (Context context, String url){
        super(context);
        urls = url;
    }

    @Override
    protected void onStartLoading() {
        android.util.Log.i(LOG_TAG, "TEST: onStartLoading() called...");
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
      android.util.Log.i(LOG_TAG, "TEST: loadInBackground() called...");
      if (urls == null) {
            return null;
        }
        result = QueryUtils.fetchEarthquakeData(urls)   ;
        return result;


    }



}


