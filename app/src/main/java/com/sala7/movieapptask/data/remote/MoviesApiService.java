package com.sala7.movieapptask.data.remote;



import com.sala7.movieapptask.pojo.response.MovieDetailsResponse;
import com.sala7.movieapptask.pojo.response.PopularMoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApiService {

    @GET("movie/popular")
    Call<PopularMoviesResponse> getPopularMoviesList(@Query("page") int page);

    @GET("movie/{movie_id}")
    Call<MovieDetailsResponse> getMovieDetails(@Path("movie_id") int id);
}



