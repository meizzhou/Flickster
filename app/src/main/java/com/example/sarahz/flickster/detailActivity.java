package com.example.sarahz.flickster;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class detailActivity extends YouTubeBaseActivity {
    @BindView(R.id.tvDetailTitle)
    TextView tvDetailTitle;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.tvDetailOverview)
    TextView tvDetailOverview;
    @BindView(R.id.tvRating)
    TextView tvRating;
    @BindView(R.id.tvVoteCount)
    TextView tvVoteCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        ButterKnife.bind(this);
        Intent intent = getIntent();

        // sets up content to pass to view
        tvDetailTitle.setText(intent.getStringExtra("title"));
        tvVoteCount.setText(String.valueOf(intent.getIntExtra("voteCount", 0)) + " Votes");
        tvDetailOverview.setText(intent.getStringExtra("overview"));
        ratingBar.setRating((int) intent.getDoubleExtra("voteAverage", 0.0) / 2);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

        DecimalFormat formatDec = new DecimalFormat("#.#");
        formatDec.setDecimalSeparatorAlwaysShown(false);
        String rating = formatDec.format(intent.getDoubleExtra("voteAverage", 0.0)) + "/10";
        tvRating.setText(rating + " Rating");

        // make http call for video API
        String id = intent.getStringExtra("id");
        String url = "https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;
                try {
                    movieJsonResults = response.getJSONArray("results");
                    JSONObject key = movieJsonResults.getJSONObject(0);
                    final String keyString = key.getString("key");

                    YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment)
                            getFragmentManager().findFragmentById(R.id.youtubeFragment);
                    // please don't hack me ><
                    youtubeFragment.initialize("AIzaSyAcpc7HZkHeTUEUWWJwCREg8EI7ma1YFUw",
                            new YouTubePlayer.OnInitializedListener() {
                                @Override
                                public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                    YouTubePlayer youTubePlayer, boolean b) {
                                    // do any work here to cue video, play video, etc.
                                    youTubePlayer.cueVideo(keyString);
                                }
                                @Override
                                public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                    YouTubeInitializationResult youTubeInitializationResult) {

                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}