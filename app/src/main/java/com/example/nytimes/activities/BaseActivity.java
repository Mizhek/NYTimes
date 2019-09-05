package com.example.nytimes.activities;

import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nytimes.R;
import com.example.nytimes.pojo.Article;
import com.example.nytimes.pojo.Pojo;

import java.util.ArrayList;

public abstract class BaseActivity extends AppCompatActivity {
    public static final String ARTICLE_TRANSFER = "ARTICLE_TRANSFER";

    void activateToolbar(boolean enableHome) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            if (toolbar != null) {
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
            }
        }

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(enableHome);
        }
    }


}
