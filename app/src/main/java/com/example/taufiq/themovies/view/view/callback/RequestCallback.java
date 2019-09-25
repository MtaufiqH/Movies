package com.example.taufiq.themovies.view.view.callback;

/**
 * Created on 24/09/2019.
 */
public interface RequestCallback {

     void onStarted();

     void onFinish();

     void onFailure(String message);
}
