package com.example.android.quakereport;

/**
 * Created by SON-OF-A3 on 3/1/2018.
 */

public class Earthquake {

    /**
     *magnitude of earthQuake
     **/
    private Double mQuakeMagnitude;

    /**
     *
     **/
    private String mQuakeLocation;

    /**
     * time of the earthquakes occurrence
     **/
    private long mQuakeTimeInMilliseconds;


    /**
     * USGS web page for each earthquake
     */
    private String mEarthquakeUrl;


    /**
     * @param quakeMagnitude this variable stores the  magnitude of earthQuake
     * @param quakeLocation this variable stores location of the earthQuake
     * @param quakeTimeInMilliseconds this variable stores the time of occurrence for the earthQuake
     * @param earthquakeUrl this variable holds the url to the USGS web page for each earthquake
     */
    public Earthquake(Double quakeMagnitude, String quakeLocation, long quakeTimeInMilliseconds, String earthquakeUrl) {
        mQuakeMagnitude =  quakeMagnitude;
        mQuakeLocation = quakeLocation;
        mQuakeTimeInMilliseconds = quakeTimeInMilliseconds;
        mEarthquakeUrl = earthquakeUrl;
    }

    /**
     * get the earthQuake Magnitude
     **/
    public Double getQuakeMagnitude() {
        return mQuakeMagnitude;
    }

    /**
     * get the earthQuake Location
     **/
    public String getQuakeLocation() {
        return mQuakeLocation;
    }

    public long getQuakeTime() {
        return mQuakeTimeInMilliseconds;
    }

    public String getGetEarthquakeUrl () {return mEarthquakeUrl;}
}
