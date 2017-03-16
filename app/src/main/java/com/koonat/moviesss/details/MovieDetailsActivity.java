package com.koonat.moviesss.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.koonat.moviesss.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String KEY_TITLE = "title";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_OVERVIEW = "overview";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.thumb)
    ImageView imageView;

    @BindView(R.id.detailsTV)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        Picasso.with(this).load(intent.getStringExtra(KEY_IMAGE))
                .error(R.drawable.default_movie_thumb)
                .placeholder(R.drawable.default_movie_thumb)
                .into(imageView);

        textView.setText(Html.fromHtml("<div style=\"text-align:center\"><h2>" + intent.getStringExtra(KEY_TITLE) + "</h2></div><br/>" + "<p>" + intent.getStringExtra(KEY_OVERVIEW) + "</p>"));
    }

    public static Intent newIntent(Activity callerActivity, String title, String image, String overview) {
        Intent intent = new Intent(callerActivity, MovieDetailsActivity.class);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_IMAGE, image);
        intent.putExtra(KEY_OVERVIEW, overview);
        return intent;
    }
}
