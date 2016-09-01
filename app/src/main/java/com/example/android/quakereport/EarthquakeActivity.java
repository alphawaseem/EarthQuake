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

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<EarthQuake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    ListView earthquakeListView;
    EarthQuakeAdapter adapter;
    private String stringUrl = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        getLoaderManager().initLoader(0, null, this);


    }

    @Override
    public Loader<ArrayList<EarthQuake>> onCreateLoader(int id, Bundle args) {
        EarthQuakeLoader loader = new EarthQuakeLoader(EarthquakeActivity.this, stringUrl);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<EarthQuake>> loader, ArrayList<EarthQuake> earthquakes) {


        adapter = new EarthQuakeAdapter(getBaseContext(), earthquakes);
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        if (earthquakeListView != null) {
            earthquakeListView.setAdapter(adapter);
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    EarthQuake currentQuake = (EarthQuake) parent.getItemAtPosition(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(currentQuake.getUrl()));
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<EarthQuake>> loader) {

        adapter.clear();

    }

}
