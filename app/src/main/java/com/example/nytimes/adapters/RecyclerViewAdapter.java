package com.example.nytimes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.nytimes.CustomCircularProgressDrawable;
import com.example.nytimes.R;
import com.example.nytimes.pojo.Article;
import com.example.nytimes.pojo.Media;
import com.example.nytimes.pojo.Metadata;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Article> mArticles;
    private ClickListener clickListener;

    public RecyclerViewAdapter(List<Article> articles) {
        mArticles = articles;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, final int position) {


        CircularProgressDrawable circularProgressDrawable;
        circularProgressDrawable = new CustomCircularProgressDrawable(holder.thumbnail.getContext(), 7, 45);
        holder.thumbnail.setImageDrawable(circularProgressDrawable);


        if ((mArticles == null) || (mArticles.size() == 0)) {
            holder.title.setText("No data to display");
            holder.date.setVisibility(View.GONE);
        } else {
            if (position == 0) {
                holder.date.setVisibility(View.VISIBLE);
            }
            Article article = mArticles.get(position);

            String title = article.getTitle();
            String date = article.getPublishedDate();

            Media media = article.getMedia().get(0);
            //.get(1) use middle size list_item_article image for faster recyclerView loading
            Metadata metadataThumbnail = media.getMediaMetadata().get(1);

            String thumbnailUrl = metadataThumbnail.getUrl();


            Glide.with(holder.thumbnail.getContext())
                    .load(thumbnailUrl)
                    .placeholder(circularProgressDrawable)
                    .into(holder.thumbnail);

            holder.title.setText(title);
            holder.date.setText(date);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onItemClick(position);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return ((mArticles != null) && (mArticles.size() != 0) ? mArticles.size() : 1);
    }

    public Article getArticle(int position) {
        return ((mArticles != null) && (mArticles.size() != 0) ? mArticles.get(position) : null);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView date;
        CardView cardView;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.thumbnail = itemView.findViewById(R.id.ivImageThumbnail);
            this.title = itemView.findViewById(R.id.tvListArticleTitle);
            this.date = itemView.findViewById(R.id.tvListArticleDate);
            this.cardView = itemView.findViewById(R.id.cardView);


        }


    }
}
