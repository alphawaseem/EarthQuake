package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        String magnitude = Double.toString(currentQuakeItem.getMagnitude()) ;
        magnitudeTextView.setText(magnitude);

        //add city to the listView
        TextView cityTextView = (TextView) listItemView.findViewById(R.id.city);
        cityTextView.setText(currentQuakeItem.getCity());

        //add date to the listView
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date) ;

        // change time format from long to local format
        Date dateObject = new Date(currentQuakeItem.getDate());

        String date = getDate(dateObject);
        String time = getTime(dateObject);
        String dateTime = date + "\n" + time;

        dateTextView.setText(dateTime);


        //return the listItemview
        return listItemView ;

    }

    private String getDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy");
        return dateFormat.format(date);
    }

    private String getTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        return dateFormat.format(date);
    }
}
