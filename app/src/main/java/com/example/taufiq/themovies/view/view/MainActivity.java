package com.example.taufiq.themovies.view.view;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import com.example.taufiq.themovies.R;
import com.example.taufiq.themovies.view.view.fragment.MoviesFragment;
import com.example.taufiq.themovies.view.view.fragment.TvFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    public static final String KEY_FRAGMENT = "fragment";

    private SparseArray<Fragment.SavedState> savedStateSparseArray = new SparseArray<>();

    private int current = R.id.movies_menu;

    private static final String SAVED_STATE_CONTAINER_KEY = "ContainerKey";
    private static final String SAVED_STATE_CURRENT_TAB_KEY = "CurrentTabKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.nav_bottom);
        bottomNavigationSetup(bottomNavigationView);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }


        if (savedInstanceState != null) {
            savedStateSparseArray = savedInstanceState.getSparseParcelableArray(SAVED_STATE_CONTAINER_KEY);
            current = savedInstanceState.getInt(SAVED_STATE_CURRENT_TAB_KEY);
        } else
            swapFragments(current, KEY_FRAGMENT, new MoviesFragment());


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSparseParcelableArray(SAVED_STATE_CONTAINER_KEY, savedStateSparseArray);
        outState.putInt(SAVED_STATE_CURRENT_TAB_KEY, current);
    }

    private void bottomNavigationSetup(BottomNavigationView bottomNavigationView) {

        bottomNavigationView.setOnNavigationItemSelectedListener(
                menuItem -> {

                    int nav_id = menuItem.getItemId();
                    switch (nav_id) {
                        case R.id.movies_menu:
                            swapFragments(nav_id, "Movie", new MoviesFragment());
                            break;

                        case R.id.tv_menu:
                            swapFragments(nav_id, "Tv Show", new TvFragment());
                            break;

                        case R.id.settings_id:
                            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                            startActivity(intent);
                            break;
                    }

                    return true;
                });
    }

    /**
     * @param fragment initializing fragment transaction
     */
    private void initFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();


    }


    private void saveFragmentState(int actionId) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment != null) {
            savedStateSparseArray.put(current, getSupportFragmentManager().saveFragmentInstanceState(currentFragment));
        }

        current = actionId;

    }


    private void swapFragments(int actionId, String key, Fragment fragment) {
        if (getSupportFragmentManager().findFragmentByTag(key) == null) {
            saveFragmentState(actionId);
            initFragment(fragment);
        }
    }


}
