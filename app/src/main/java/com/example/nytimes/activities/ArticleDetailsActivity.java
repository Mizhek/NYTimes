package com.example.nytimes.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.nytimes.R;
import com.example.nytimes.pojo.Article;
import com.example.nytimes.pojo.Media;
import com.example.nytimes.pojo.Metadata;
import com.squareup.picasso.Picasso;

public class ArticleDetailsActivity extends BaseActivity {
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        activateToolbar(true);

        mTextViewTitle = findViewById(R.id.details_tvTitle);
        mTextViewAuthor = findViewById(R.id.details_tvAuthor);
        mTextViewDate = findViewById(R.id.details_tvDate);
        mImageViewFullImage = findViewById(R.id.details_ivFullImage);
        mTextViewImageCaption = findViewById(R.id.details_tvFullImageCaption);
        mTextViewArticleSummary = findViewById(R.id.details_tvArticleSummary);
        mButtonOpenUrl = findViewById(R.id.details_btnOpenUrl);
        mTextViewTopic = findViewById(R.id.details_tvTopic);
        mTextViewContentType = findViewById(R.id.details_tvContentType);
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

            Metadata metadata = media.getMediaMetadata().get(2);
            String imageUrl = metadata.getUrl();
            String fullImageUrl = imageUrl.replaceFirst("-mediumThreeByTwo440.jpg", "-superJumbo.jpg?quality=50");

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
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            });




            Picasso.get().load(fullImageUrl)
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(mImageViewFullImage);

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
