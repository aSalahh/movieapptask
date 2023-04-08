package com.sala7.movieapptask.ui.fragment.popular_movies;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sala7.movieapptask.data.remote.MoviesApiService;
import com.sala7.movieapptask.pojo.response.PopularMoviesResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMoviesRepository {
    private final MoviesApiService moviesApiService;
    private final MutableLiveData<PopularMoviesResponse> popularMoviesResponseMutableLiveData = new MutableLiveData<>();


    @Inject
    public PopularMoviesRepository(MoviesApiService moviesApiService) {
        this.moviesApiService = moviesApiService;
    }

    public LiveData<PopularMoviesResponse> getPopularMoviesList(int page) {
        moviesApiService.getPopularMoviesList(page).enqueue(new Callback<PopularMoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<PopularMoviesResponse> call, @NonNull Response<PopularMoviesResponse> response) {
                popularMoviesResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<PopularMoviesResponse> call, @NonNull Throwable t) {
                popularMoviesResponseMutableLiveData.setValue(null);
            }
        });
        return popularMoviesResponseMutableLiveData;
    }


}
