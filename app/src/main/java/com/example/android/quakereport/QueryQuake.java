package com.example.android.quakereport;

/**
 * Created by waseem on 8/25/16.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryQuake {

    private static final String LOG_TAG = " ERROR: ";

    /**
     * Create a private constructor because no one should ever create a {@link QueryQuake} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryQuake() {
    }

    /**
     * Returs the URL object from string url if url is valid
     * else return null
     * @param stringUrl is url in string format
     * @return the URL object
     */
    public static URL createURL(String stringUrl) {

        URL link = null;
        try {
            link = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("Error", e.getMessage());
        }

        return link;
    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Quake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<EarthQuake> extractEarthquakes(String stringUrl) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<EarthQuake> earthquakes = new ArrayList<>();


        URL urlObj = createURL(stringUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(urlObj);
        } catch (IOException e) {
            Log.e(LOG_TAG, "httprequest error");
        }


        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            //  Parse the response given by the SAMPLE_JSON_RESPONSE string and
            JSONObject rootNode = new JSONObject(jsonResponse);
            JSONArray features = rootNode.getJSONArray("features");

            // build up a list of Earthquake objects with the corresponding data.
            for (int i = 0, end = features.length(); i < end; i++) {

                JSONObject properties = features.getJSONObject(i).getJSONObject("properties");
                double magnitude = properties.getDouble("mag");
                String city = properties.getString("place");
                long date = properties.getLong("time");
                String url = properties.getString("url");
                earthquakes.add(new EarthQuake(magnitude, city, date, url));
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryQuake", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}