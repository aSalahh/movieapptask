package com.sala7.movieapptask.ui.fragment.movie_details;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sala7.movieapptask.data.remote.MoviesApiService;
import com.sala7.movieapptask.pojo.response.MovieDetailsResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsRepository {
    private final MoviesApiService moviesApiService;
    private final MutableLiveData<MovieDetailsResponse> movieDetailsResponseMutableLiveData = new MutableLiveData<>();


    @Inject
    public MovieDetailsRepository(MoviesApiService moviesApiService) {
        this.moviesApiService = moviesApiService;
    }

    public LiveData<MovieDetailsResponse> getMovieDetails(int movieId) {
        moviesApiService.getMovieDetails(movieId).enqueue(new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetailsResponse> call, @NonNull Response<MovieDetailsResponse> response) {
                movieDetailsResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetailsResponse> call, @NonNull Throwable t) {
                movieDetailsResponseMutableLiveData.setValue(null);
            }
        });
        return movieDetailsResponseMutableLiveData;
    }


}
