package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by waseem on 8/29/16.
 */
public class EarthQuakeLoader extends AsyncTaskLoader<ArrayList<EarthQuake>> {

    private String url;

    public EarthQuakeLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<EarthQuake> loadInBackground() {

        if (url.length() < 1 || url == null) {
            return null;
        }
        return QueryQuake.extractEarthquakes(url);
    }
}