package com.example.android.quakereport;

/**
 * Created by waseem on 8/26/16.
 */
public class EarthQuake extends Quake {

    private String url;

    public EarthQuake(double magnitude, String city, long date, String url) {
        super(magnitude, city, date);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
