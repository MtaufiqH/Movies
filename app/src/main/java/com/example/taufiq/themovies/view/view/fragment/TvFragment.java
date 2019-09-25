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
import com.example.taufiq.themovies.view.adapter.AdapterTv;
import com.example.taufiq.themovies.view.view.callback.RequestCallback;
import com.example.taufiq.themovies.view.viewmodel.MyViewModel;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment implements RequestCallback {

    private ProgressBar progressBar;
    private AdapterTv adapterTv;

    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // init UI
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_tv);
        progressBar = view.findViewById(R.id.tv_progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);

        model.requestCallback = this;

        model.getTvs().observe(this,tvResults -> {
            this.adapterTv = new AdapterTv(tvResults);
            this.adapterTv.notifyDataSetChanged();
            recyclerView.setAdapter(adapterTv);
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
