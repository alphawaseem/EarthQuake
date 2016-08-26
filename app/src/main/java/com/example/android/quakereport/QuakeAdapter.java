package com.example.android.quakereport;

import android.content.Context;
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
 * Created by waseem on 8/25/16.
 */
public class QuakeAdapter extends ArrayAdapter<Quake> {



    private static final String LOG_TAG = QuakeAdapter.class.getSimpleName();
    /**
     * This is own constructor it does mirror ArrayAdapter constructor
     *
     * this constructor calls super constructor which takes 3
     * parameters. second parameter is used to refer default textView
     * in this case we dont need it hense we pass 0 for second argument
     *
     * @param context the current context used to inflate layout
     * @param earthquakes list of Quakes objects
     */
    public QuakeAdapter(Context context , ArrayList<Quake> earthquakes) {
        super(context,0,earthquakes);

    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }

        // get current earthQuake item
        Quake currentQuakeItem = getItem(position);

        // add magnitude to the listView
        inflateMagnitude(listItemView, currentQuakeItem);

        //add city to the listView
        inflateCity(listItemView, currentQuakeItem);

        //add date to the listView
        inflateDateAndTime(listItemView, currentQuakeItem);


        //return the listItemview
        return listItemView ;

    }

    /**
     * This function will inflate the magnitude to listItemView from the
     * current Quake object
     *
     * @param listItemView     is the View that will take the magnitude
     * @param currentQuakeItem is the quake object from which magnitude is extracted
     */
    private void inflateMagnitude(View listItemView, Quake currentQuakeItem) {
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        String magnitude = magnitudeFormat.format(currentQuakeItem.getMagnitude());
        magnitudeTextView.setText(magnitude);

    }

    /**
     * This function will inflate the city and offset to listItemView from the
     * current Quake object
     *
     * @param listItemView     is the View that will take the city and offset
     * @param currentQuakeItem is the quake object from which city is extracted
     */
    private void inflateCity(View listItemView, Quake currentQuakeItem) {
        TextView cityTextView = (TextView) listItemView.findViewById(R.id.city);
        TextView offsetTextView = (TextView) listItemView.findViewById(R.id.city_offset);

        String offsetAndCity = currentQuakeItem.getCity();
        if (offsetAndCity.contains(" of ")) {
            String[] strings = offsetAndCity.split(" of ");

            offsetTextView.setText(strings[0] + " of");
            cityTextView.setText(strings[1]);
        } else {
            offsetTextView.setText("Near to");
            cityTextView.setText(offsetAndCity);
        }

    }

    /**
     * This function will inflate the date and time to listItemView from the
     * current Quake object
     *
     * @param listItemView     is the View that will take the date and time
     * @param currentQuakeItem is the quake object from which date and time is extracted
     */
    private void inflateDateAndTime(View listItemView, Quake currentQuakeItem) {
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);

        // change time format from long to local format
        Date dateObject = new Date(currentQuakeItem.getDate());

        String date = getDate(dateObject);
        String time = getTime(dateObject);
        String dateTime = date + "\n" + time;

        dateTextView.setText(dateTime);
    }

    /**
     * This function will return the string representation of date
     * from the date object in the form MMM DD, yyyy (e.g. Jan 30, 2016)
     *
     * @param date Date object
     * @return the string date
     */
    private String getDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy");
        return dateFormat.format(date);
    }

    /**
     * This function will return the string representation of time
     * from the date object in the form h:mm a (e.g. 8:45 AM)
     * @param date Date object
     * @return the string date
     */
    private String getTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        return dateFormat.format(date);
    }
}
