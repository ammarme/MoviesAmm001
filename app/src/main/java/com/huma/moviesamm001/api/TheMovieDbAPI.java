package com.huma.moviesamm001.api;


import com.huma.moviesamm001.data.Movies;
import com.huma.moviesamm001.data.Reviews;
import com.huma.moviesamm001.data.Trailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TheMovieDbAPI {
    String API_KEY = "397b65dc1146c99252660a80e3d34c6d";
    String BASE_URL = "http://api.themoviedb.org/3/";

    String TOP_RATED = "top_rated", POPULAR = "popular";

    @GET("movie/{sort_by}?api_key=" + API_KEY)
    Call<Movies> getMovies(@Path("sort_by") String sortBy);

    @GET("movie/{id}/videos?api_key=" + API_KEY)
    Call<Trailers> getTrailers(@Path("id") int id);

    @GET("movie/{id}/reviews?api_key=" + API_KEY)
    Call<Reviews> getReviews(@Path("id") int id);
}

























