package com.example.taufiq.themovies.view.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;
import android.widget.ProgressBar;

import com.example.taufiq.themovies.BuildConfig;
import com.example.taufiq.themovies.view.api.Api_Client;
import com.example.taufiq.themovies.view.api.Api_Route;
import com.example.taufiq.themovies.view.model.remote.movies.Movie;
import com.example.taufiq.themovies.view.model.remote.movies.MovieResult;
import com.example.taufiq.themovies.view.model.remote.tvs.TvResult;
import com.example.taufiq.themovies.view.model.remote.tvs.TvShows;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created By Taufiq on 9/14/2019.
 */
public class MyViewModel extends ViewModel {

    private static final MutableLiveData<ArrayList<MovieResult>> movies = new MutableLiveData<>();
    private static final MutableLiveData<ArrayList<TvResult>> tvShows = new MutableLiveData<>();
    private String API_KEY = BuildConfig.API_KEY;
    private String language = "en-US";
    private ProgressBar progressBar;


    public MutableLiveData<ArrayList<MovieResult>> getMovies() {

        Api_Route api_route = Api_Client.RetrofitClient().create(Api_Route.class);
        Call<Movie> call = api_route.getMovie(API_KEY, language);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NotNull Call<Movie> call, @NotNull Response<Movie> response) {
                if (response.body() != null) {
                    movies.postValue((ArrayList<MovieResult>) response.body()
                            .getMovie_results());
                }

            }

            @Override
            public void onFailure(@NotNull Call<Movie> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });

        return movies;
    }

    public MutableLiveData<ArrayList<TvResult>> getTvs(){
        Api_Route api_route = Api_Client.RetrofitClient().create(Api_Route.class);
        Call<TvShows> call = api_route.getTvShows(API_KEY,language);
        call.enqueue(new Callback<TvShows>() {
            @Override
            public void onResponse(@NotNull Call<TvShows> call, @NotNull Response<TvShows> response) {
                if (response.body() != null) {
                    tvShows.postValue((ArrayList<TvResult>) response.body()
                            .getMovie_results()) ;
                }

            }

            @Override
            public void onFailure(@NotNull Call<TvShows> call, @NotNull Throwable t) {
                t.printStackTrace();

            }
        });

        return tvShows;

    }


}
