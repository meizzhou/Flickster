package com.example.sarahz.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie>{

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    // ViewHolder stores basic information of the movie
    private static class ViewHolder {
        TextView title;
        TextView overview;
        ImageView Image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the data item from position
        Movie movie = getItem(position);
        ViewHolder viewHolder; // view lookup cache stored in tag

        // check the existing movie being reused
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);

            viewHolder.title = (TextView) convertView.findViewById(tvTitle);
            viewHolder.overview = (TextView) convertView.findViewById(tvOverview);
            viewHolder.Image = (ImageView) convertView.findViewById(R.id.ivMovieImage);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // clear out image from convertView
        viewHolder.Image.setImageResource(0);

        // Populate text
        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.overview.setText(movie.getOverview());

        String image = "";
        int orientation = convertView.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            image = movie.getPosterPath();
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            image = movie.getBackdrop();
        }

        Picasso.with(getContext()).load(image)
            .placeholder(R.drawable.placeholder)
            .into(viewHolder.Image);
        return convertView;
    }
}
