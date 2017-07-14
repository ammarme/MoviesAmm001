package com.huma.moviesamm001;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.huma.moviesamm001.adapter.MoviesAdapter;
import com.huma.moviesamm001.api.TheMovieDbAPI;
import com.huma.moviesamm001.data.Movie;
import com.huma.moviesamm001.data.Movies;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieListActivity extends AppCompatActivity {
    private static final String TAG = MovieListActivity.class.getSimpleName();

    public static final String KEY_SPINNER_POSITION = "key_spinner_position";
    //
    String mPopularity = TheMovieDbAPI.POPULAR;

    @BindView(R.id.movie_list) RecyclerView mMovieList;
    @BindView(R.id.spinner) Spinner mSpinner;


    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

        mSpinner.setSelection(sharedPreferences.getInt(KEY_SPINNER_POSITION, 0));

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mPopularity = TheMovieDbAPI.POPULAR;
                        loadMovies(mPopularity);
                        break;
                    case 1:
                        mPopularity = TheMovieDbAPI.TOP_RATED;
                        loadMovies(mPopularity);
                        break;
                    case 2:
                        loadFromDb();
                        break;
                }
                sharedPreferencesEditor.putInt(KEY_SPINNER_POSITION, position);
                sharedPreferencesEditor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        }
    }

    //    private void loadFromDb() {
//        MoviesDBProviderUtils dbHelper = new MoviesDBProviderUtils(this);
//        initList(dbHelper.getMovies());
//    }
//
    private void loadMovies(String popularity) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TheMovieDbAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TheMovieDbAPI api = retrofit.create(TheMovieDbAPI.class);

        api.getMovies(popularity).enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                initList(response.body().getResults());
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

            }
        });
    }

    private void initList(List<Movie> movies) {
        int spanCount = (int) (mMovieList.getWidth() / pxFromDp(MovieListActivity.this, 100f));
        mMovieList.setLayoutManager(new GridLayoutManager(MovieListActivity.this, spanCount));
        mMovieList.setAdapter(new MoviesAdapter(MovieListActivity.this, movies));
    }

    private float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
