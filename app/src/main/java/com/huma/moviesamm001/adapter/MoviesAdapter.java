package com.huma.moviesamm001.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huma.moviesamm001.MovieDetailActivity;
import com.huma.moviesamm001.MovieDetailFragment;
import com.huma.moviesamm001.MovieListActivity;
import com.huma.moviesamm001.R;
import com.huma.moviesamm001.data.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.huma.moviesamm001.MovieDetailActivity.KEY_MOVIE;

/**
 * User: YourPc
 * Date: 7/14/2017
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Movie> mMovies;

    private boolean mTwoPane;

    public MoviesAdapter(Context context, List<Movie> movies) {
        mContext = context;
        mMovies = movies;
        mTwoPane = context.getResources().getBoolean(R.bool.is_tablet);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);

        holder.mItemTitle.setText(movie.getOriginalTitle());

        Glide.with(mContext)
                .load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath())
                .centerCrop()
                .crossFade()
                .into(holder.mItemImage);

        holder.mView.setOnClickListener(v -> {
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putParcelable(KEY_MOVIE, movie);
                MovieDetailFragment fragment = new MovieDetailFragment();
                fragment.setArguments(arguments);
                ((MovieListActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, fragment)
                        .commit();
            } else {
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra(KEY_MOVIE, movie);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        @BindView(R.id.item_image) ImageView mItemImage;
        @BindView(R.id.item_title) TextView mItemTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }
    }
}



