package com.example.sarahz.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sarahz on 1/24/17.
 * this class stores the basic information of each movie
 */

public class Movie {
    private String backdropPath;
    private String id;
    private String originalTitle;
    private String posterPath;
    private String overview;
    private int voteCount;
    private double voteAverage;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.id = jsonObject.getString("id");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.posterPath = jsonObject.getString("poster_path");
        this.voteAverage = jsonObject.getDouble("vote_average");
        this.voteCount = jsonObject.getInt("vote_count");
    }

    public String getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    // poster path is not full url
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdrop() {
        return String.format("https://image.tmdb.org/t/p/w780/%s", backdropPath);
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    // puts JSONArray item to list
    public static ArrayList<Movie> fromJsonArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<Movie>();

        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
