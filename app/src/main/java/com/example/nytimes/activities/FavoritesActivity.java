package com.example.nytimes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nytimes.R;

public class FavoritesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        activateToolbar(true);
    }
}
