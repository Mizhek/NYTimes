package com.example.nytimes.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nytimes.MyApplication;
import com.example.nytimes.R;
import com.example.nytimes.activities.ArticleDetailsActivity;
import com.example.nytimes.adapters.RecyclerViewAdapter;
import com.example.nytimes.data.Article;
import com.example.nytimes.data.NYTimesApi;
import com.example.nytimes.viewmodels.ArticlesViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainViewPagerFragment extends Fragment {

    private static final String TAG = "MainViewPagerFragment";
    private static final String TAB_NUMBER = "tab_number";

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private String mTabContentType;
    private ArticlesViewModel mArticlesViewModel;

    public static MainViewPagerFragment newInstance(int tabNumber) {

        MainViewPagerFragment fragment = new MainViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAB_NUMBER, tabNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//   Set proper content type for loading data later
        if (getArguments() != null) {
            mTabContentType = selectTabContentType(getArguments().getInt(TAB_NUMBER));
        }

//   Download data if needed and save it in viewModel
        mArticlesViewModel = ViewModelProviders.of(this).get(ArticlesViewModel.class);
        Log.d(TAG, "onCreate: viewModel created in tab: " + mTabContentType);
        if (mArticlesViewModel.getArticles().isEmpty()) {
            mArticlesViewModel.setArticles(downloadData());
        }


    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_tabs, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(createGridLayoutManager());
        mRecyclerViewAdapter = new RecyclerViewAdapter();
        mRecyclerViewAdapter.populateData(mArticlesViewModel.getArticles());
        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.ClickListener() {
            @Override
            public void onCardClick(int position) {
                navigateToArticleDetails(position);
            }

            @Override
            public void onStarClick(int position) {
                starArticle(position);
            }
        });

        mRecyclerView.setAdapter(mRecyclerViewAdapter);


        return view;
    }

    private void starArticle(int position) {
        Toast.makeText(getContext(), "Not ready yet", Toast.LENGTH_SHORT).show();
    }

    private void navigateToArticleDetails(int position) {
        Intent intent = new Intent(getContext(), ArticleDetailsActivity.class);
        intent.putExtra(ArticleDetailsActivity.ARTICLE_TRANSFER, mRecyclerViewAdapter.getArticle(position));
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mRecyclerView.setLayoutManager(createGridLayoutManager());
    }


    private String selectTabContentType(int tabNumber) {
        switch (tabNumber) {
            case 1:
                return "emailed";
            case 2:
                return "shared";
            case 3:
                return "viewed";
        }
        return null;
    }


    private RecyclerView.LayoutManager createGridLayoutManager() {

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return new GridLayoutManager(getContext(), 1);
        } else {
            return new GridLayoutManager(getContext(), 2);
        }

    }

    private List<Article> downloadData() {

        final NYTimesApi[] mNYTimesApi = {new NYTimesApi()};
        final List<Article> mArticles = new ArrayList<>();

        if (mArticles.isEmpty()) {
            MyApplication.getMostPopularApi().getArticles(mTabContentType, 30).enqueue(new Callback<NYTimesApi>() {
                @Override
                public void onResponse(Call<NYTimesApi> call, Response<NYTimesApi> response) {
                    mNYTimesApi[0] = response.body();
                    if (mNYTimesApi[0] != null) {
                        mArticles.addAll(mNYTimesApi[0].getResults());
                    }
                    if (mRecyclerView != null) {
                        (mRecyclerView.getAdapter()).notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<NYTimesApi> call, Throwable t) {
//                    Show error toast only for the first tab
                    if (mTabContentType.equals("emailed")) {
                        Toast.makeText(getContext(), "An error occurred. Check internet connection or open Favorites to see saved articles.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        return mArticles;
    }

}