package com.sala7.movieapptask.ui.fragment.popular_movies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.sala7.movieapptask.R;
import com.sala7.movieapptask.databinding.FragmentPopularMoviesBinding;
import com.sala7.movieapptask.pojo.model.Movie;
import com.sala7.movieapptask.ui.fragment.popular_movies.adapter.MoviesAdapter;
import com.sala7.movieapptask.ui.fragment.popular_movies.interfaces.MovieListener;
import com.sala7.movieapptask.utills.Helper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PopularMoviesFragment extends Fragment implements MovieListener {

    private final List<Movie> tvShowList = new ArrayList<>();
    @Inject
    MoviesAdapter moviesAdapter;
    boolean isLoading = false;
    boolean isLoadingMore = false;
    private FragmentPopularMoviesBinding fragmentPopularMoviesBinding;
    private PopularMoviesViewModel popularMoviesViewModel;
    private int currentPage = 1;
    private int totalAvailablePages = 1;

    public PopularMoviesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentPopularMoviesBinding = FragmentPopularMoviesBinding.inflate(getLayoutInflater());
        return fragmentPopularMoviesBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Helper.isNetworkAvailable(requireActivity())) {
            initialization();

        } else {
            Toast.makeText(getActivity(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
        }



    }



    private void initialization() {
        fragmentPopularMoviesBinding.moviesRecyclerview.setHasFixedSize(true);
        popularMoviesViewModel = new ViewModelProvider(this).get(PopularMoviesViewModel.class);
        moviesAdapter = new MoviesAdapter();
        moviesAdapter.setList(tvShowList, this);
        fragmentPopularMoviesBinding.moviesRecyclerview.setAdapter(moviesAdapter);
        fragmentPopularMoviesBinding.moviesRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!fragmentPopularMoviesBinding.moviesRecyclerview.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1;
                        getMostPopularTVShows();
                    }
                }
            }
        });
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
        toggleLoading();
        popularMoviesViewModel.getMostPopularMovies(currentPage).observe(requireActivity(), mostPopularMoviesResponse -> {
                    toggleLoading();
                    if (mostPopularMoviesResponse != null) {
                        totalAvailablePages = mostPopularMoviesResponse.getTotalPages();
                        if ((mostPopularMoviesResponse.getResults() != null)) {
                            int oldCount = tvShowList.size();
                            tvShowList.addAll(mostPopularMoviesResponse.getResults());

                            moviesAdapter.notifyItemRangeInserted(oldCount, tvShowList.size());

                        }
                    }
                }

        );
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (isLoading) {
                fragmentPopularMoviesBinding.isLoading.setVisibility(View.GONE);
                isLoading = false;
            } else {
                fragmentPopularMoviesBinding.isLoading.setVisibility(View.VISIBLE);
                isLoading = true;
            }
        } else {

            if (isLoadingMore) {
                fragmentPopularMoviesBinding.isLoadingMore.setVisibility(View.GONE);
                isLoadingMore = false;
            } else {
                fragmentPopularMoviesBinding.isLoadingMore.setVisibility(View.VISIBLE);
                isLoadingMore = true;
            }


        }
    }

    @Override
    public void onMovieClicked(Movie movie) {
        NavDirections direction = PopularMoviesFragmentDirections.actionPopularMoviesFragmentToMovieDetailsFragment(movie.getId());
        Navigation.findNavController(requireView()).navigate((NavDirections) direction);
    }
}