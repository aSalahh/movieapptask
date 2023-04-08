package com.sala7.movieapptask.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.sala7.movieapptask.BuildConfig;
import com.sala7.movieapptask.R;
import com.sala7.movieapptask.databinding.ActivityMainBinding;
import com.sala7.movieapptask.ui.fragment.popular_movies.PopularMoviesFragmentDirections;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    public NavHostFragment navHostFragment;
    public NavController navController;
    Uri data;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Movieapptask);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        handleDeepLink(getIntent());


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleDeepLink(intent);
    }

    private void handleDeepLink(Intent intent) {
        Uri deepLinkUri = intent.getData();
        if (deepLinkUri != null) {
            String deepLinkString = deepLinkUri.toString();
            if (deepLinkString.equals("http://www.movieapptask.com/popular")) {
                navController.navigate(R.id.popularMoviesFragment);
                if (BuildConfig.DEBUG) {
                    Log.d("MainActivity", "Opened deep link: " + deepLinkString);
                }
            } else {
                Pattern pattern = Pattern.compile("http://www.movieapptask.com/details_screen/(\\d+)");
                Matcher matcher = pattern.matcher(deepLinkString);
                if (matcher.matches()) {
                    int id = Integer.parseInt(matcher.group(1));
                    NavDirections action = PopularMoviesFragmentDirections.actionPopularMoviesFragmentToMovieDetailsFragment(id);
                    navController.navigate(action);

                    if (BuildConfig.DEBUG) {
                        Log.d("MainActivity", "Opened deep link: " + deepLinkString);
                    }
                }
            }
        }
    }


}