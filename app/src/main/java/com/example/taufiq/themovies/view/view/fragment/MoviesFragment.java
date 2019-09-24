package com.example.taufiq.themovies.view.view.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.taufiq.themovies.R;
import com.example.taufiq.themovies.view.adapter.AdapterMovies;
import com.example.taufiq.themovies.view.view.callback.RequestCallback;
import com.example.taufiq.themovies.view.viewmodel.MyViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements RequestCallback {

    ProgressBar progressBar;
    AdapterMovies adapterMovies;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movies_, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // init ui
        progressBar = view.findViewById(R.id.my_progres_dialog);
        final RecyclerView recyclerView = view.findViewById(R.id.rv_movies);


        MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);

        model.requestCallback = this;

        model.getMovies().observe(this, movieResults -> {
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            this.adapterMovies = new AdapterMovies(movieResults);
            this.adapterMovies.notifyDataSetChanged();
            recyclerView.setAdapter(adapterMovies);
        });
    }

    @Override
    public void onStarted() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinish() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String message) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }
}

