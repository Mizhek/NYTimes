package com.example.nytimes.activities;

import android.os.Bundle;

import com.example.nytimes.R;
import com.example.nytimes.fragments.SettingsFragment;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();

        activateToolbar(true);
    }

}