package com.example.nytimes.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nytimes.MyApplication;
import com.example.nytimes.R;
import com.example.nytimes.RecyclerClickListener;
import com.example.nytimes.RecyclerViewAdapter;
import com.example.nytimes.activities.ArticleDetailsActivity;
import com.example.nytimes.activities.BaseActivity;
import com.example.nytimes.pojo.Article;
import com.example.nytimes.pojo.Pojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivityTabsFragment extends Fragment implements RecyclerClickListener.onRecyclerClickListener {
    private static final String TAG = "MainActivityTabsFragmen";

    private RecyclerView mRecyclerView;
    private Pojo mPojo;
    private List<Article> mArticles;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private String mTabContentType;

    private static final String TAB_NUMBER = "tab_number";

    public static MainActivityTabsFragment newInstance(int index) {

        MainActivityTabsFragment fragment = new MainActivityTabsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAB_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            setTabContentType(getArguments().getInt(TAB_NUMBER));
        }

        mPojo = new Pojo();
        mArticles = new ArrayList<>();

        if (mArticles.isEmpty()) {
            MyApplication.getMostPopularApi().getArticles(mTabContentType, 30).enqueue(new Callback<Pojo>() {
                @Override
                public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                    mPojo = response.body();
                    if (mPojo != null) {
                        mArticles.addAll(mPojo.getResults());
                    }
                    if (mRecyclerView != null) {
                        (mRecyclerView.getAdapter()).notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<Pojo> call, Throwable t) {
                    Toast.makeText(getActivity(), "An error occurred during networking in Emailed Tab", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }
    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_tabs, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(createGridLayoutManager());
        mRecyclerViewAdapter = new RecyclerViewAdapter(mArticles);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerClickListener(getContext(), mRecyclerView, this));
        return view;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mRecyclerView.setLayoutManager(createGridLayoutManager());
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), ArticleDetailsActivity.class);
        intent.putExtra(BaseActivity.ARTICLE_TRANSFER, mRecyclerViewAdapter.getArticle(position));
        startActivity(intent);
    }

    private void setTabContentType(int tabNumber) {
        switch (tabNumber) {
            case 1:
                mTabContentType = "emailed";
                break;
            case 2:
                mTabContentType = "shared";
                break;
            case 3:
                mTabContentType = "viewed";
        }
    }


    private RecyclerView.LayoutManager createGridLayoutManager() {

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return new GridLayoutManager(getContext(), 1);
        } else {
            return new GridLayoutManager(getContext(), 2);
        }

    }

}