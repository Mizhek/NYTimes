package com.example.nytimes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nytimes.pojo.Article;
import com.example.nytimes.pojo.Media;
import com.example.nytimes.pojo.Metadata;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Article> mArticles;

    public RecyclerViewAdapter(List<Article> articles) {
        mArticles = articles;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        if ((mArticles == null) || (mArticles.size() == 0)) {
            holder.date.setText("No date");
            holder.title.setText("No title");
        } else {

            Article article = mArticles.get(position);

            String title = article.getTitle();
            String date = article.getPublishedDate();

            Media media = article.getMedia().get(0);
            //.get(1) use middle size article image for faster recyclerView loading
            Metadata metadataThumbnail = media.getMediaMetadata().get(1);

            String thumbnailUrl = metadataThumbnail.getUrl();


            Picasso.get().load(thumbnailUrl)
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.thumbnail);

            holder.title.setText(title);
            holder.date.setText(date);
        }

    }

    @Override
    public int getItemCount() {
        return ((mArticles != null) && (mArticles.size() != 0) ? mArticles.size() : 1);
    }

    public Article getArticle(int position) {
        return ((mArticles != null) && (mArticles.size() != 0) ? mArticles.get(position) : null);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView date;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.thumbnail = itemView.findViewById(R.id.ivImageThumbnail);
            this.title = itemView.findViewById(R.id.tvListArticleTitle);
            this.date = itemView.findViewById(R.id.tvListArticleDate);

        }
    }
}
