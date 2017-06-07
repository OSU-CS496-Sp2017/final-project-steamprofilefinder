package com.example.android.steamprofilefinder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.steamprofilefinder.utils.SteamUtils;
import com.example.android.steamprofilefinder.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Daniel Goh on 6/6/2017.
 */

public class ProfileItemDetailActivity extends AppCompatActivity {
    private static final String TAG = ProfileItemDetailActivity.class.getSimpleName();

    private static final String STEAMID_URL_KEY = "steamidUrl";
    private static final int STEAMID_LOADER_ID = 0;

    private TextView mProfileItemTV;
    private TextView mPersonaNameTV;
    private TextView mPersonaStateTV;

    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_item_detail);

        mProfileItemTV = (TextView)findViewById(R.id.tv_profile_item);
        mPersonaNameTV = (TextView)findViewById(R.id.tv_persona_name);
        mPersonaStateTV = (TextView)findViewById(R.id.tv_persona_state);

        mLoadingIndicatorPB = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = (TextView)findViewById(R.id.tv_loading_error_message);

    //   String profileURL = SteamUtils.buildUserProfileURL(steamid);
    //    Bundle argsBundle = new Bundle();
    //    argsBundle.putString(STEAMID_URL_KEY, profileURL);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(SteamUtils.ProfileItem.EXTRA_PROFILE_ITEM)) {
            SteamUtils.ProfileItem profileItem = (SteamUtils.ProfileItem)intent.getSerializableExtra(SteamUtils.ProfileItem.EXTRA_PROFILE_ITEM);
            getPlayerSummaries(profileItem.steamid);
        }
    }

    private void getPlayerSummaries(String steamid) {
        String SteamIDURL = SteamUtils.buildUserProfileURL(steamid);
        Log.d(TAG, "got search url: " + SteamIDURL);
        new SteamIDSearchTask().execute(SteamIDURL);
    }

    public class SteamIDSearchTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String SteamIDURL = params[0];
            String searchResults = null;
            try {
                searchResults = NetworkUtils.doHTTPGet(SteamIDURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
            if (s != null) {
                mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
                ArrayList<SteamUtils.SteamIDItem> searchResultsList = SteamUtils.parseSteamIDJSON(s);
            //    mPersonaNameTV.setText(searchResultsList.personaname);

                // FIXME            mForecastAdapter.updateForecastData(searchResultsList);
            } else {
    // FIXME            mForecastListRV.setVisibility(View.INVISIBLE);
                mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
            }
        }
    }

}
