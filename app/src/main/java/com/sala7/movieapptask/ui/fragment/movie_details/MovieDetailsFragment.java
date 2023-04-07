package com.sala7.movieapptask.ui.fragment.movie_details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.sala7.movieapptask.R;
import com.sala7.movieapptask.databinding.FragmentMovieDetailsBinding;
import com.sala7.movieapptask.utills.Constants;
import com.squareup.picasso.Picasso;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MovieDetailsFragment extends Fragment {

    private FragmentMovieDetailsBinding fragmentMovieDetailsBinding;
    private MovieDetailsFragmentArgs args;

    private MovieDetailsViewModel movieDetailsViewModel;


    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentMovieDetailsBinding = FragmentMovieDetailsBinding.inflate(getLayoutInflater());
        return fragmentMovieDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization();


    }

    private void initialization() {
        args = MovieDetailsFragmentArgs.fromBundle(getArguments());
        int movieId = args.getMovieId();
        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        getMovieDetails(movieId);

    }

    @SuppressLint("SetTextI18n")
    private void getMovieDetails(int movieId) {
        fragmentMovieDetailsBinding.isLoading.setVisibility(View.VISIBLE);
        movieDetailsViewModel.getMovieDetails(movieId).observe(requireActivity(), movieDetailsResponse -> {
            fragmentMovieDetailsBinding.isLoading.setVisibility(View.GONE);
            if (movieDetailsResponse != null) {
                String backImageUrl = Constants.POSTER_BASE_URL + movieDetailsResponse.getBackdropPath();
                String movieImageUrl = Constants.POSTER_BASE_URL + movieDetailsResponse.getPosterPath();
                Picasso.get().load(backImageUrl).into(fragmentMovieDetailsBinding.movieBackDrpoPath);
                Picasso.get().load(movieImageUrl).into(fragmentMovieDetailsBinding.imageTVShow);
                fragmentMovieDetailsBinding.textMovieName.setText(movieDetailsResponse.getOriginalTitle());
                fragmentMovieDetailsBinding.textDescription.setText(movieDetailsResponse.getOverview());
                fragmentMovieDetailsBinding.textStatus.setText(movieDetailsResponse.getStatus());
                fragmentMovieDetailsBinding.textOriginalLanguage.setText(movieDetailsResponse.getOriginalLanguage());
                fragmentMovieDetailsBinding.textReleasedDate.setText(movieDetailsResponse.getReleaseDate());
                fragmentMovieDetailsBinding.textTagline.setText(movieDetailsResponse.getTagline());
                fragmentMovieDetailsBinding.buttonWebsite.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(MaterialButton button, boolean isChecked) {
                        //TODO implement navigation to website
                    }
                });
                fragmentMovieDetailsBinding.textRuntime.setText(movieDetailsResponse.getRuntime() + " Min");

                fragmentMovieDetailsBinding.textReadMore.setOnClickListener(v -> {
                    if (fragmentMovieDetailsBinding.textReadMore.getText().toString().equals("Read More")) {
                        fragmentMovieDetailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                        fragmentMovieDetailsBinding.textDescription.setEllipsize(null);
                        fragmentMovieDetailsBinding.textReadMore.setText(R.string.read_less);
                    } else {
                        fragmentMovieDetailsBinding.textDescription.setMaxLines(4);
                        fragmentMovieDetailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                        fragmentMovieDetailsBinding.textReadMore.setText(R.string.read_more);

                    }
                });
            }

        });

    }


}