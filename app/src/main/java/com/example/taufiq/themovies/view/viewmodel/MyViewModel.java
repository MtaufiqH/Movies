package com.example.taufiq.themovies.view.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.taufiq.themovies.BuildConfig;
import com.example.taufiq.themovies.view.api.Api_Client;
import com.example.taufiq.themovies.view.api.Api_Route;
import com.example.taufiq.themovies.view.model.remote.movies.Movie;
import com.example.taufiq.themovies.view.model.remote.movies.MovieResult;
import com.example.taufiq.themovies.view.model.remote.tvs.TvResult;
import com.example.taufiq.themovies.view.model.remote.tvs.TvShows;
import com.example.taufiq.themovies.view.view.callback.RequestCallback;

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

    public RequestCallback requestCallback = null;

    public MutableLiveData<ArrayList<MovieResult>> getMovies() {

        // call the onStarted callback to handle the initial event
        requestCallback.onStarted();

        Api_Route api_route = Api_Client.RetrofitClient().create(Api_Route.class);
        Call<Movie> call = api_route.getMovie(API_KEY, language);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NotNull Call<Movie> call, @NotNull Response<Movie> response) {

                if (response.isSuccessful()) {

                    // call the onFinish callback to handle success event
                    requestCallback.onFinish();

                    movies.postValue((ArrayList<MovieResult>) response.body()
                            .getMovie_results());
                }

            }

            @Override
            public void onFailure(@NotNull Call<Movie> call, @NotNull Throwable t) {
                t.printStackTrace();

                // call the onFailure callback to handle failure condition
                requestCallback.onFailure(t.getMessage());
            }
        });

        return movies;
    }

    public MutableLiveData<ArrayList<TvResult>> getTvs(){

        // call the onStarted callback to handle the initial event
        requestCallback.onStarted();

        Api_Route api_route = Api_Client.RetrofitClient().create(Api_Route.class);
        Call<TvShows> call = api_route.getTvShows(API_KEY,language);
        call.enqueue(new Callback<TvShows>() {
            @Override
            public void onResponse(@NotNull Call<TvShows> call, @NotNull Response<TvShows> response) {
                if (response.isSuccessful()) {

                    // call the onFinish callback to handle success event
                    requestCallback.onFinish();

                    tvShows.postValue((ArrayList<TvResult>) response.body()
                            .getMovie_results()) ;
                }
            }

            @Override
            public void onFailure(@NotNull Call<TvShows> call, @NotNull Throwable t) {
                t.printStackTrace();

                // call the onFailure callback to handle failure condition
                requestCallback.onFailure(t.getMessage());
            }
        });

        return tvShows;

    }


}
