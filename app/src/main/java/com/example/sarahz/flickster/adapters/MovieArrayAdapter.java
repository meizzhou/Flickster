package com.example.sarahz.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sarahz.flickster.R;
import com.example.sarahz.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.sarahz.flickster.R.id.tvOverview;
import static com.example.sarahz.flickster.R.id.tvTitle;

/**
 * Created by sarahz on 1/24/17.
 * this class is an adapter to put item_movie and item_movie_full to list in activity_movie
 */

public class MovieArrayAdapter extends BaseAdapter {

    private static final int SMALL_ITEM = 0;
    private static final int BIG_ITEM = 1;
    private final List<Movie> mMovies;

    public void clear() {
        mMovies.clear();
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        mMovies = movies;
    }

    // ViewHolder stores basic information of the movie
    private static class ViewHolder {
        TextView title;
        TextView overview;
        ImageView Image;
    }

    @Override
    public int getItemViewType(int position) {
        return mMovies.get(position).getVoteAverage() >= 6f ? BIG_ITEM : SMALL_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Object getItem(int position) {
        return mMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // check the existing movie being reused
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            viewHolder = new ViewHolder();
            if (getItemViewType(position) == SMALL_ITEM) {
                convertView = inflater.inflate(R.layout.item_movie, parent, false);
                viewHolder = fillViewHolder(convertView, viewHolder);
            } else {
                convertView = inflater.inflate(R.layout.item_movie_full, parent, false);
                viewHolder = fillViewHolder(convertView, viewHolder);

            }
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // clear out image from convertView
        viewHolder.Image.setImageResource(0);

        // get the data item from position
        Movie movie = (Movie) getItem(position);

        // check if title and overview are needed
        if (viewHolder.title != null && viewHolder.overview != null) {
            // Populate text
            viewHolder.title.setText(movie.getOriginalTitle());
            viewHolder.overview.setText(movie.getOverview());
        }

        return checkDirection(viewHolder, position, convertView, getItemViewType(position), movie);
    }

    // find each item's place in view
    private static ViewHolder fillViewHolder(View convertView, ViewHolder viewHolder) {
        viewHolder.title = (TextView) convertView.findViewById(tvTitle);
        viewHolder.overview = (TextView) convertView.findViewById(tvOverview);
        viewHolder.Image = (ImageView) convertView.findViewById(R.id.ivMovieImage);
        return viewHolder;
    }

    // take in the viewholder and movie's information
    // set the image to be wider for landscape, more detail for portrait
    // return the view
    private static View checkDirection(ViewHolder viewHolder, int position, View convertView, int type, Movie movie) {
        String image = movie.getBackdrop();

        int orientation = convertView.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT && type == SMALL_ITEM) {
            image = movie.getPosterPath();
        }

        Picasso.with(convertView.getContext()).load(image)
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.Image);
        return convertView;
    }
}