package com.example.nytimes.activities;

import android.content.Intent;
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
import com.example.nytimes.CustomCircularProgressDrawable;
import com.example.nytimes.R;
import com.example.nytimes.data.Article;
import com.example.nytimes.data.Media;
import com.example.nytimes.data.Metadata;

public class ArticleDetailsActivity extends BaseActivity {

    public static final String ARTICLE_TRANSFER = "article_transfer";

    TextView mTextViewTitle;
    TextView mTextViewAuthor;
    TextView mTextViewDate;
    ImageView mImageViewFullImage;
    TextView mTextViewImageCaption;
    TextView mTextViewArticleSummary;
    Button mButtonOpenUrl;
    TextView mTextViewTopic;
    TextView mTextViewContentType;
    TextView mTextViewTags;

    Article mArticle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        activateToolbar(true);

        mTextViewTitle = findViewById(R.id.tvTitle);
        mTextViewAuthor = findViewById(R.id.tvAuthor);
        mTextViewDate = findViewById(R.id.tvDate);
        mImageViewFullImage = findViewById(R.id.ivFullImage);
        mTextViewImageCaption = findViewById(R.id.tvImageCaption);
        mTextViewArticleSummary = findViewById(R.id.tvSummary);
        mButtonOpenUrl = findViewById(R.id.btnOpenArticle);
        mTextViewTopic = findViewById(R.id.tvTopic);
        mTextViewContentType = findViewById(R.id.tvContentType);
        mTextViewTags = findViewById(R.id.tvKeywords);

        Intent intent = getIntent();
        mArticle = (Article) intent.getSerializableExtra(ARTICLE_TRANSFER);

        if (mArticle != null) {
            String title = mArticle.getTitle();
            final String url = mArticle.getUrl();
            String tags = mArticle.getAdxKeywords();
            String topic = mArticle.getSection();
            String author = mArticle.getByline();
            String contentType = mArticle.getType();
            String summary = mArticle.getAbstract();
            String date = mArticle.getPublishedDate();

            Media media = mArticle.getMedia().get(0);
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
            displayTextOrHideView(mTextViewTags, tags.replaceAll(";", ", "), "Keywords: ");

            mButtonOpenUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openUrlInCustomTab(url);
                }
            });

            CircularProgressDrawable circularProgressDrawable;
            circularProgressDrawable = new CustomCircularProgressDrawable(this, 7, 45);

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

    private void openUrlInCustomTab(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(ArticleDetailsActivity.this, Uri.parse(url));
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
