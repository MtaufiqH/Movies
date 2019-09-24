package com.example.taufiq.themovies.view.view.callback;

/**
 * Created by suryamudti on 24/09/2019.
 */
public interface RequestCallback {

    public void onStarted();

    public void onFinish();

    public void onFailure(String message);
}
