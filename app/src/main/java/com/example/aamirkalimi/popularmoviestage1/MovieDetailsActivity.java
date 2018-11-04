package com.example.aamirkalimi.popularmoviestage1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.Info.MovieData;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {
    private MovieData currentMovie;
    private TextView mTitleTextView;
    private TextView mReleaseDateTextView;
    private TextView mRatingTextView;
    private TextView mSynopsisTextView;
    private ImageView mPosterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);
        mTitleTextView = (TextView) findViewById(R.id.movie_title);
        mReleaseDateTextView = (TextView) findViewById(R.id.release_date);
        mRatingTextView = (TextView) findViewById(R.id.rating_value);
        mSynopsisTextView = (TextView) findViewById(R.id.synopsis_text);
        mPosterImageView = (ImageView) findViewById(R.id.movie_poster);
        Intent intentStartingThisActivity = getIntent();
        if (intentStartingThisActivity != null && intentStartingThisActivity.hasExtra("Movie")) {
            currentMovie = intentStartingThisActivity.getParcelableExtra("Movie");
            if (currentMovie != null) {
                fillLayoutWithData();
            }
        }
    }

    private void fillLayoutWithData() {
        mTitleTextView.setText(currentMovie.getTitle());
        mReleaseDateTextView.setText(currentMovie.getReleaseDate());
        mRatingTextView.setText(currentMovie.getVoteAverage());
        mSynopsisTextView.setText(currentMovie.getSynopsis());
        Picasso.with(this)
                .load(Uri.parse(currentMovie.getPosterURL()))
                .fit()
                .centerInside()
                .into(mPosterImageView);
    }
}
