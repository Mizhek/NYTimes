package com.example.nytimes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentEmailedTab extends Fragment implements RecyclerClickListener.onRecyclerClickListener {

    private RecyclerView mRecyclerView;
    private Pojo mPojo;
    private List<Article> mArticles;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPojo = new Pojo();
        mArticles = new ArrayList<>();

        if (mArticles.isEmpty()) {
            MyApplication.getMostPopularApi().getArticles("emailed", 30).enqueue(new Callback<Pojo>() {
                @Override
                public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                    mPojo = response.body();
                    if (mPojo != null) {
                        mArticles.addAll(mPojo.getResults());
                    }
                    Objects.requireNonNull(mRecyclerView.getAdapter()).notifyDataSetChanged();
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
        View view = inflater.inflate(R.layout.fragment_emailed, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_emailed);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerViewAdapter = new RecyclerViewAdapter(mArticles);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerClickListener(getContext(), mRecyclerView, this));

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), ArticleDetailsActivity.class);
        intent.putExtra(BaseActivity.ARTICLE_TRANSFER, mRecyclerViewAdapter.getArticle(position));
        startActivity(intent);
    }
}
