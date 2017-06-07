package com.example.android.steamprofilefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.steamprofilefinder.utils.SteamUtils;

/**
 * Created by Daniel Goh on 6/6/2017.
 */

public class ProfileItemDetailActivity extends AppCompatActivity {
    private TextView mProfileItemTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_item_detail);

        mProfileItemTV = (TextView)findViewById(R.id.tv_profile_item);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(SteamUtils.ProfileItem.EXTRA_PROFILE_ITEM)) {
            SteamUtils.ProfileItem profileItem = (SteamUtils.ProfileItem)intent.getSerializableExtra(SteamUtils.ProfileItem.EXTRA_PROFILE_ITEM);
            // FIXME mProfileItemTV.setText(profileItem.cityName);
        }
    }

}
