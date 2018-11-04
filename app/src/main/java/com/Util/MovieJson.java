package com.Util;

import com.Info.MovieData;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class MovieJson {
    public static ArrayList<MovieData> getMovieArrayListFromJson(String movieJson) throws Exception {
        final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
        JSONObject movieJsonObject = new JSONObject(movieJson);
        JSONArray resultsJsonArray = movieJsonObject.getJSONArray("results");
        ArrayList<MovieData> movieArrayList = new ArrayList<>(resultsJsonArray.length());
        for (int pos = 0; pos < resultsJsonArray.length(); pos++) {
            JSONObject jsonObject = resultsJsonArray.getJSONObject(pos);
            String title = jsonObject.getString("title");
            String synopsis = jsonObject.getString("overview");
            String vote = jsonObject.getString("vote_average");
            String posterURL = IMAGE_BASE_URL + jsonObject.getString("poster_path");
            String releaseDate = jsonObject.getString("release_date");
            MovieData movieData = new MovieData(title, releaseDate, posterURL, synopsis, vote);
            movieArrayList.add(pos, movieData);
        }
        return movieArrayList;
    }
}
