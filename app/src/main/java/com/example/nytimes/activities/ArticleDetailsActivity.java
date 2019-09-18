package com.example.nytimes.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.nytimes.R;
import com.example.nytimes.pojo.Article;
import com.example.nytimes.pojo.Media;
import com.example.nytimes.pojo.Metadata;

public class ArticleDetailsActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        activateToolbar(true);

        TextView mTextViewTitle;
        mTextViewTitle = findViewById(R.id.details_tvTitle);
        TextView mTextViewAuthor;
        mTextViewAuthor = findViewById(R.id.details_tvAuthor);
        TextView mTextViewDate;
        mTextViewDate = findViewById(R.id.details_tvDate);
        ImageView mImageViewFullImage;
        mImageViewFullImage = findViewById(R.id.details_ivFullImage);
        TextView mTextViewImageCaption;
        mTextViewImageCaption = findViewById(R.id.details_tvFullImageCaption);
        TextView mTextViewArticleSummary;
        mTextViewArticleSummary = findViewById(R.id.details_tvArticleSummary);
        Button mButtonOpenUrl;
        mButtonOpenUrl = findViewById(R.id.details_btnOpenUrl);
        TextView mTextViewTopic;
        mTextViewTopic = findViewById(R.id.details_tvTopic);
        TextView mTextViewContentType;
        mTextViewContentType = findViewById(R.id.details_tvContentType);
        TextView mTextViewTags;
        mTextViewTags = findViewById(R.id.details_tvTags);

        Intent intent = getIntent();
        Article article = (Article) intent.getSerializableExtra(ARTICLE_TRANSFER);

        if (article != null) {
            String title = article.getTitle();
            final String url = article.getUrl();
            String tags = article.getAdxKeywords();
            String topic = article.getSection();
            String author = article.getByline();
            String contentType = article.getType();
            String summary = article.getAbstract();
            String date = article.getPublishedDate();

            Media media = article.getMedia().get(0);
            String imageCaption = media.getCaption();

            // .get(2) biggest available image
            Metadata metadata = media.getMediaMetadata().get(2);
            String imageUrl = metadata.getUrl();


            mTextViewTitle.setText(title);
            displayTextOrHideView(mTextViewAuthor, author, "Author(s): ");
            displayTextOrHideView(mTextViewDate, date, "");
            displayTextOrHideView(mTextViewArticleSummary, summary, "Summary: ");
            displayTextOrHideView(mTextViewImageCaption, imageCaption, "");
            displayTextOrHideView(mTextViewTopic, topic, "Topic: ");
            displayTextOrHideView(mTextViewContentType, contentType, "Content type: ");
            displayTextOrHideView(mTextViewTags, tags.replaceAll(";", ", "), "Tags: ");

            mButtonOpenUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(ArticleDetailsActivity.this, Uri.parse(url));
                }
            });

            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
            circularProgressDrawable.setCenterRadius(45f);
            circularProgressDrawable.setStrokeWidth(7f);
            circularProgressDrawable.start();
            circularProgressDrawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.icons), PorterDuff.Mode.ADD));

            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(circularProgressDrawable)
                    .into(mImageViewFullImage);

        } else {
            mTextViewTitle.setText(R.string.network_error_message);
            mTextViewAuthor.setVisibility(View.GONE);
            mTextViewDate.setVisibility(View.GONE);
            mButtonOpenUrl.setVisibility(View.GONE);
            mTextViewImageCaption.setVisibility(View.GONE);
            mTextViewContentType.setVisibility(View.GONE);
            mTextViewTags.setVisibility(View.GONE);
            mTextViewArticleSummary.setVisibility(View.GONE);

        }


    }

    private void displayTextOrHideView(TextView textView, String text, String textPrefix) {

        if (text == null) {
            textView.setVisibility(View.GONE);
        } else {

            if (text.length() > 0 && textPrefix.length() > 0) {
                SpannableStringBuilder str = new SpannableStringBuilder(textPrefix + text);
                str.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 0, textPrefix.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(str);
            } else if (text.length() > 0) {
                textView.setText(text);
            } else {
                textView.setVisibility(View.GONE);
            }
        }
    }


}
