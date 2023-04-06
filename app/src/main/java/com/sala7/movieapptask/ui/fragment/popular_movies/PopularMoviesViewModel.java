package com.sala7.movieapptask.ui.fragment.popular_movies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.sala7.movieapptask.pojo.response.PopularMoviesResponse;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PopularMoviesViewModel extends ViewModel {


    @Inject
     PopularMoviesRepository popularMoviesRepository;

    @Inject
    public PopularMoviesViewModel() {
    }


    public LiveData<PopularMoviesResponse> getMostPopularMovies(int page){
        return popularMoviesRepository.getPopularMoviesList(page);
    }
}
