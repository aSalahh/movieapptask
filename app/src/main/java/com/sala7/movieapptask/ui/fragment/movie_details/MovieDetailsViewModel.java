package com.sala7.movieapptask.ui.fragment.movie_details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sala7.movieapptask.pojo.response.MovieDetailsResponse;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MovieDetailsViewModel extends ViewModel {


    @Inject
    MovieDetailsRepository movieDetailsRepository;

    @Inject
    public MovieDetailsViewModel() {
    }


    public LiveData<MovieDetailsResponse> getMovieDetails(int page) {
        return movieDetailsRepository.getMovieDetails(page);
    }
}
