package com.example.android.steamprofilefinder.utils;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Daniel Goh on 6/6/2017.
 */

public class SteamUtils {

    private final static String STEAM_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast"; //FIXME
    private final static String STEAM_QUERY_PARAM = "q";
    private final static String STEAM_APPID_PARAM = "appid";

    private final static String STEAM_APPID = "B8F6C5BAF7C8606FFFCD28F5983DFAD3";

    public static class ProfileItem implements Serializable {
        public static final String EXTRA_PROFILE_ITEM = "com.example.android.steamprofilefinder.utils.ProfileItem.SearchResult";
        public String description;
        public long temperatureLong;
    }

    public static String buildProfileURL(String forecastLocation) {
        return Uri.parse(STEAM_BASE_URL).buildUpon()
                .appendQueryParameter(STEAM_QUERY_PARAM, forecastLocation)
                .appendQueryParameter(STEAM_APPID_PARAM, STEAM_APPID)
                .build()
                .toString();
    }

    public static ArrayList<ProfileItem> parseProfileJSON(String searchResultsJSON) {
        try {
            JSONObject searchResultsObj = new JSONObject(searchResultsJSON);
            JSONArray searchResultsItems = searchResultsObj.getJSONArray("list");

            ArrayList<ProfileItem> searchResultsList = new ArrayList<ProfileItem>();
            for (int i = 0; i < searchResultsItems.length(); i++) {
                ProfileItem searchResult = new ProfileItem();
                JSONObject searchResultItem = searchResultsItems.getJSONObject(i);
                // FIXME searchResult.cityName = searchResultsObj.getJSONObject("city").getString("name");

                JSONArray ja = searchResultItem.getJSONArray("weather");
                for(int j=0; j<ja.length(); j++) {
                    JSONObject json = ja.getJSONObject(j);
                    // FIXME searchResult.weatherDescription = json.getString("main").toString();
                }
                searchResultsList.add(searchResult);
            }
            return searchResultsList;
        } catch (JSONException e) {
            return null;
        }
    }

}
