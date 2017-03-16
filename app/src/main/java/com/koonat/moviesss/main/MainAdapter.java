package com.koonat.moviesss.main;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koonat.moviesss.R;
import com.koonat.moviesss.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Natig on 3/16/17.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private final OnItemClickListener listener;
    private List<Movie> movies;
    private Context context;

    public MainAdapter(Context context, List<Movie> movies, OnItemClickListener listener) {
        this.listener = listener;
        this.context = context;
        this.movies = movies;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movie, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.click(movie, listener);

        Picasso.with(context).load(movie.getImageUrl())
                .error(R.drawable.default_movie_thumb)
                .placeholder(R.drawable.default_movie_thumb)
                .into(holder.thumbnailIV);

        holder.ratingTV.setText(movie.getVoteAverage().toString());
        holder.titleTV.setText(movie.getTitle() + " (" + movie.getYear() + ")");
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface OnItemClickListener {
        void onClick(Movie movie);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rowTitle)
        TextView titleTV;

        @BindView(R.id.rowRating)
        TextView ratingTV;

        @BindView(R.id.rowThumb)
        ImageView thumbnailIV;

        @BindView(R.id.layoutMovieRow)
        RelativeLayout layout;

        @BindView(R.id.listMoviesCardView)
        CardView cardView;

        @BindView(R.id.clickCover)
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void click(final Movie movie, final OnItemClickListener listener) {
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(movie);
                }
            });
        }
    }
}