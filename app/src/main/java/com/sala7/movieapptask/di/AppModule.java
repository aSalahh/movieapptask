package com.sala7.movieapptask.di;


import static com.sala7.movieapptask.utills.Constants.API_KEY;
import static com.sala7.movieapptask.utills.Constants.BASE_URL;
import static com.sala7.movieapptask.utills.Constants.NETWORK_TIMEOUT;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sala7.movieapptask.BuildConfig;
import com.sala7.movieapptask.data.remote.MoviesApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {


    @Provides
    @Singleton
    public String provideBaseUrl() {
        return BASE_URL;
    }

    @Provides
    @Singleton
    public Integer provideConnectionTimeout() {
        return NETWORK_TIMEOUT;
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder().setLenient().create();
    }


    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient() {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            Interceptor requestInterceptor = chain -> {
                HttpUrl url = chain.request().url().newBuilder().addQueryParameter("api_key", API_KEY).build();

                Request request = chain.request().newBuilder().url(url).build();

                return chain.proceed(request);
            };

            return new OkHttpClient.Builder().addInterceptor(requestInterceptor).addInterceptor(loggingInterceptor).build();
        } else {
            return new OkHttpClient.Builder().build();
        }
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(String baseUrl, Gson gson, OkHttpClient client) {
        return new Retrofit.Builder().baseUrl(baseUrl).client(client).addConverterFactory(GsonConverterFactory.create(gson)).build();
    }


    @Provides
    @Singleton
    public MoviesApiService provideMoviesApiService(Retrofit retrofit) {
        return retrofit.create(MoviesApiService.class);
    }

}

