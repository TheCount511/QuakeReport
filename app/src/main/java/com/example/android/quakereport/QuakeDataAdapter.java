package com.example.android.quakereport;

/**
 * Created by SON-OF-A3 on 3/1/2018.
 */

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by SON-OF-A3 on 10/8/2017.
 */
public class QuakeDataAdapter extends ArrayAdapter<Earthquake> {


    String bearingLocation;
    String cityLocation;

    public QuakeDataAdapter(Context context, ArrayList<Earthquake> earthquakes) {

        super(context, 0, earthquakes);
    }

    /**
     *
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }


    private String magnitude (double mag){
        DecimalFormat formatter = new DecimalFormat("0.0");
        String output = formatter.format(mag);
        return output;
    }

    private String bearingLocation(String location) {
        String firstPart;

        String bearingAlternative= "Near the";
        String location_separator = " of ";
        if (location.contains(location_separator)){
            String[] separated = location.split(location_separator);
            firstPart = separated[0] + location_separator;
        }
        else {
            firstPart = bearingAlternative;
        }

        return firstPart;
    }

    private String cityLocation(String location) {
        String secondPart;
        String location_separator = " of ";

        if (location.contains(location_separator)){
            String[] separated = location.split(location_separator);
            secondPart = separated[1];
        }
        else {secondPart = location;}
        return secondPart;
    }


    private int getMagnitudeColor(double magnitude){
        int magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude1);
        int magnitudeInt = (int) Math.floor(magnitude);
        switch (magnitudeInt){
            case 0:
            case 1:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;
            case 2:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;
            case 3:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case 4:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case 5:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case 6:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case 7:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case 8:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case 9:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;
            case 10:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
                break;
        }
        return magnitudeColor;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
        }


        // Get the data item for this position
        Earthquake currentItem = getItem(position);

        // Lookup view for data population
        TextView earthquakeMagnitude = (TextView) convertView.findViewById(R.id.quakeMagnitude);
        TextView earthquakeBearing = (TextView) convertView.findViewById(R.id.quakeGeoLocation);
        TextView earthquakeLocation = (TextView) convertView.findViewById(R.id.quakeLocation);
        TextView earthquakeDate = (TextView) convertView.findViewById(R.id.quakeDate);
        TextView earthquakeTime = (TextView) convertView.findViewById(R.id.quakeTime);


        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) earthquakeMagnitude.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentItem.getQuakeMagnitude());



        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        String formattedMagnitude = magnitude(currentItem.getQuakeMagnitude());
        earthquakeMagnitude.setText(formattedMagnitude);
        //get the magnitude for the earth quake and set it on the magnitude textView



    /*get the location and split it using the methods defined above this method splits the first half
        *that contains the bearing*/
        bearingLocation = bearingLocation(currentItem.getQuakeLocation());

        //set the bearing location to the textview
        earthquakeBearing.setText(bearingLocation);

        cityLocation = cityLocation(currentItem.getQuakeLocation());

        earthquakeLocation.setText(cityLocation);
        //get the location of the earthquake and set it on the location textView

        // Populate the data into the template view using the data object
        Date dateObject = new Date(currentItem.getQuakeTime());

        // Format the Date string (i.e. "Feb,30 2016")
        String formattedDate = formatDate(dateObject);

        //get the time of occurrence of the earthquake and set it on the time textView
        earthquakeDate.setText(formattedDate);

        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);

        // Display the time of the current earthquake in that TextView
        earthquakeTime.setText(formattedTime);




        return convertView;

    }

}