package com.sala7.movieapptask.ui.fragment.popular_movies.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sala7.movieapptask.databinding.ItemContainerMovieBinding;
import com.sala7.movieapptask.pojo.model.Movie;
import com.sala7.movieapptask.ui.fragment.popular_movies.interfaces.MovieListener;
import com.sala7.movieapptask.utills.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private List<Movie> moviesList = new ArrayList<>();
    private MovieListener movieListener;

    @Inject
    public MoviesAdapter() {

    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerMovieBinding binding = ItemContainerMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MoviesViewHolder(binding);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        String imageUrl = moviesList.get(position).getPosterPath();
        Picasso.get().load(Constants.POSTER_BASE_URL + imageUrl).into(holder.binding.itemContainerMovieImage);

        holder.binding.itemContainerTextMovieName.setText(moviesList.get(position).getOriginalTitle());
        holder.binding.itemContainerTextMovieReleasedDate.setText("ReleaseDate: " + moviesList.get(position).getReleaseDate());
        holder.binding.itemContainerTextMovieOriginalLanguage.setText("(" + moviesList.get(position).getOriginalLanguage() + ")");
        holder.binding.itemContainerTextMovieVoteAvarage.setText(moviesList.get(position).getVoteAverage().toString());
        holder.binding.getRoot().setOnClickListener(v -> movieListener.onMovieClicked(moviesList.get(position)));

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Movie> moviesList, MovieListener movieListener) {
        this.moviesList = moviesList;
        this.movieListener = movieListener;
        notifyDataSetChanged();
    }


    static class MoviesViewHolder extends RecyclerView.ViewHolder {
        ItemContainerMovieBinding binding;

        public MoviesViewHolder(ItemContainerMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}
