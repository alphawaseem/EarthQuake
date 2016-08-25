package com.example.android.quakereport;

/**
 * Created by waseem on 8/25/16.
 * Quake class is used to store information about the
 * earthquake that have took place. Quake object contains
 * 3 information
 *  1. the magnitude of the earthquake
 *  2. the city in which earthquake took place
 *  3. the date
 *
 */
public class Quake {

    private double magnitude ; // magnitude of the earthquake
    private String city ;  // city of earthquake
    private String date ; // date of earthquake

    /**
     * Create new Quake object
     *
     *
     *
     * @param magnitude is the magnitude of earthquake (e.g. 4.7)
     * @param city is the city in which earthquake took place (e.g. Tokyo)
     * @param date is the date on which earthquake took place (e.g. Feb 2,2016)
     *
     */
    public Quake(double magnitude , String city , String date) {

        this.magnitude = magnitude ;
        this.city = city;
        this.date = date ;
    }

    /**
     *
     * @return the magnitude of earthquake
     */
    public double getMagnitude() {
        return magnitude ;
    }

    /**
     *
     * @return the city of earthquake
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @return the date of earthquake ( MM DD,YYYY)
     */
    public String getDate() {
        return date;
    }

}
