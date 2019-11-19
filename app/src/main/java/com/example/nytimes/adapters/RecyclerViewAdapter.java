package com.example.nytimes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.nytimes.CustomCircularProgressDrawable;
import com.example.nytimes.R;
import com.example.nytimes.data.Article;
import com.example.nytimes.data.Media;
import com.example.nytimes.data.Metadata;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Article> mArticles;
    private ClickListener mClickListener;

    public void populateData(List<Article> articles) {
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

        holder.bind(position);


    }

    @Override
    public int getItemCount() {
        return ((mArticles != null) && (mArticles.size() != 0) ? mArticles.size() : 1);
    }

    public Article getArticle(int position) {
        return ((mArticles != null) && (mArticles.size() != 0) ? mArticles.get(position) : null);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    public interface ClickListener {
        void onCardClick(int position);

        void onStarClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgThumbnail;
        TextView txtTitle;
        TextView txtDate;
        CardView cardView;
        ImageButton btnStar;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgThumbnail = itemView.findViewById(R.id.ivImageThumbnail);
            this.txtTitle = itemView.findViewById(R.id.tvListArticleTitle);
            this.txtDate = itemView.findViewById(R.id.tvListArticleDate);
            this.cardView = itemView.findViewById(R.id.cardView);
            this.btnStar = itemView.findViewById(R.id.ibFavoriteToggle);


        }


        public void bind(final int position) {
            CircularProgressDrawable circularProgressDrawable;
            circularProgressDrawable = new CustomCircularProgressDrawable(imgThumbnail.getContext(), 7, 45);
            imgThumbnail.setImageDrawable(circularProgressDrawable);


            if ((mArticles == null) || (mArticles.size() == 0)) {
                txtTitle.setText("No data to display");
                txtDate.setVisibility(View.GONE);
            } else {
                if (position == 0) {
                    txtDate.setVisibility(View.VISIBLE);
                }
                Article article = mArticles.get(position);

                String title = article.getTitle();
                String date = article.getPublishedDate();

                Media media = article.getMedia().get(0);
                //.get(1) use middle size list_item_article image for faster recyclerView loading
                Metadata metadataThumbnail = media.getMediaMetadata().get(1);

                String thumbnailUrl = metadataThumbnail.getUrl();


                Glide.with(imgThumbnail.getContext())
                        .load(thumbnailUrl)
                        .placeholder(circularProgressDrawable)
                        .into(imgThumbnail);

                this.txtTitle.setText(title);
                this.txtDate.setText(date);

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mClickListener != null) {
                            mClickListener.onCardClick(position);
                        }
                    }
                });

                btnStar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mClickListener != null) {
                            mClickListener.onStarClick(position);
                        }
                    }
                });


            }
        }
    }
}
