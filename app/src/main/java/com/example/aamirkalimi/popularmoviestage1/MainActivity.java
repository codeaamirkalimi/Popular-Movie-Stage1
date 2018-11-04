package com.example.aamirkalimi.popularmoviestage1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.Info.MovieAdapter;
import com.Info.MovieData;
import com.Util.MovieJson;
import com.Util.Network;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    private ProgressBar mNetworkLoadProgressBar;
    private TextView mErrorTextView;
    private RecyclerView mMoviesRecyclerView;
    private MovieAdapter mMovieAdapter;
    Button mRefreshButton;
    private static final int SPAN_COUNT = 2;
    private static Network.preferredSortType currentSortType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNetworkLoadProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mErrorTextView = (TextView) findViewById(R.id.error_text_view);
        mMoviesRecyclerView = (RecyclerView) findViewById(R.id.movie_list);
        mRefreshButton = (Button) findViewById(R.id.refresh_button);
        GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mMoviesRecyclerView.setLayoutManager(layoutManager);
        mMoviesRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this);
        mMoviesRecyclerView.setAdapter(mMovieAdapter);
        currentSortType = Network.preferredSortType.POPULARITY;
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchMovieData();
            }
        });
        fetchMovieData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_1_menu:
                currentSortType = Network.preferredSortType.POPULARITY;
                fetchMovieData();
                if (mErrorTextView.getVisibility() == View.VISIBLE) {
                    Toast.makeText(this, getString(R.string.error_response), Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.option_2_menu:
                currentSortType = Network.preferredSortType.RATING;
                fetchMovieData();
                if (mErrorTextView.getVisibility() == View.VISIBLE) {
                    Toast.makeText(this, getString(R.string.error_response), Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickItem(MovieData movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("Movie", movie);
        startActivity(intent);
    }

    private void fetchMovieData() {
        showMoviesList();
        new FetchMoviesTask().execute(currentSortType);
    }

    private void showMoviesList() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        mRefreshButton.setVisibility(View.INVISIBLE);
        mMoviesRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideMoviesList() {
        mErrorTextView.setVisibility(View.VISIBLE);
        mRefreshButton.setVisibility(View.VISIBLE);
        mMoviesRecyclerView.setVisibility(View.INVISIBLE);
    }

    private class FetchMoviesTask extends AsyncTask<Network.preferredSortType, Void, ArrayList<MovieData>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mNetworkLoadProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<MovieData> doInBackground(Network.preferredSortType... params) {
            ArrayList<MovieData> movieArrayList = null;
            if (params[0] == null) {
                return null;
            }
            try {
                URL url = Network.buildURL(params[0]);
                String jsonData = Network.getJsonFromHttpUrl(url);
                movieArrayList = MovieJson.getMovieArrayListFromJson(jsonData);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return movieArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> movieArrayList) {
            mNetworkLoadProgressBar.setVisibility(View.INVISIBLE);
            if (movieArrayList != null) {
                showMoviesList();
                mMovieAdapter.setMovieArrayList(movieArrayList);
            } else {
                hideMoviesList();
            }
        }
    }
}