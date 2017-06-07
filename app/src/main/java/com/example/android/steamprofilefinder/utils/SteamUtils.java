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

    private final static String STEAM_BASE_URL = "http://api.steampowered.com/ISteamUser/ResolveVanityURL/v0001/";
    private final static String STEAM_USER_PROFILE_URL = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/";
    private final static String STEAM_APPID_PARAM = "key";
    private final static String STEAM_VANITYURL_PARAM = "vanityurl";
    private final static String STEAM_STEAMID_PARAM = "steamids";

    private final static String STEAM_APPID = "B8F6C5BAF7C8606FFFCD28F5983DFAD3";


    public static class ProfileItem implements Serializable {
        public static final String EXTRA_PROFILE_ITEM = "com.example.android.steamprofilefinder.utils.ProfileItem.SearchResult";
        public String success;
        public String steamid;
        public String description;
    }

    public static class SteamIDItem implements Serializable {
        public static final String EXTRA_STEAMID_ITEM = "com.example.android.steamprofilefinder.utils.SteamIDItem.SearchResult";
        public String personaname;
        public String profilestate;
    }

    public static String buildProfileURL(String profile) {
        return Uri.parse(STEAM_BASE_URL).buildUpon()
                .appendQueryParameter(STEAM_APPID_PARAM, STEAM_APPID)
                .appendQueryParameter(STEAM_VANITYURL_PARAM, profile)
                .build()
                .toString();
    }

    public static ArrayList<ProfileItem> parseProfileJSON(String searchResultsJSON) {
        try {
            JSONObject searchResultsObj = new JSONObject(searchResultsJSON);

            ArrayList<ProfileItem> searchResultsList = new ArrayList<ProfileItem>();
            ProfileItem searchResult = new ProfileItem();
            searchResult.success = searchResultsObj.getJSONObject("response").getString("success");

            if (searchResult.success.equals("1")){
                searchResult.description = "Found Player! Tap for details";
                searchResult.steamid = searchResultsObj.getJSONObject("response").getString("steamid");
                searchResultsList.add(searchResult);
            }
            else { //FIXME return 0?
            }
            return searchResultsList;
        } catch (JSONException e) {
            return null;
        }
    }

    public static String buildUserProfileURL(String profile) {
        return Uri.parse(STEAM_USER_PROFILE_URL).buildUpon()
                .appendQueryParameter(STEAM_APPID_PARAM, STEAM_APPID)
                .appendQueryParameter(STEAM_STEAMID_PARAM, profile)
                .build()
                .toString();
    }

    public static ArrayList<SteamIDItem> parseSteamIDJSON(String searchResultsJSON) {
        try {
            JSONObject searchResultsObj = new JSONObject(searchResultsJSON);
            JSONArray searchResultsItems = searchResultsObj.getJSONObject("response").getJSONArray("players");

            ArrayList<SteamIDItem> searchResultsList = new ArrayList<SteamIDItem>();
            for (int i = 0; i < searchResultsItems.length(); i++) {
                SteamIDItem searchResult = new SteamIDItem();
                JSONObject searchResultItem = searchResultsItems.getJSONObject(i);
                searchResult.personaname = searchResultItem.getString("personaname");
                searchResult.profilestate = searchResultItem.getString("profilestate");

                searchResultsList.add(searchResult);
            }
            return searchResultsList;
        } catch (JSONException e) {
            return null;
        }
    }

}
