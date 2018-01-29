package com.amedia.campusfulda;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.util.Log;
import android.view.MenuItem;

import com.amedia.campusfulda.fragments.CategoryFragment;
import com.amedia.campusfulda.fragments.HomeFragment;
import com.amedia.campusfulda.fragments.NavFragment;
import com.amedia.campusfulda.fragments.SettingsFragment;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements CategoryFragment.OnFragmentInteractionListener,
                                                                NavFragment.OnFragmentInteractionListener,
                                                                SettingsFragment.OnFragmentInteractionListener,
                                                                HomeFragment.OnFragmentInteractionListener {


    private String json;
    private DatabaseHelper myDatabase;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        String apptitle = "Campusplan Fulda";

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(apptitle);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navView);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        Fragment fragment = null;
        Class fragmentClass = CategoryFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                switchToHomeFragment();
                                break;
                            case R.id.action_search:
                                //Toast.makeText(getApplicationContext(), "suche", Toast.LENGTH_SHORT).show();
                                switchToCategoryFragment();
                                break;
                            case R.id.action_settings:
                                //Toast.makeText(getApplicationContext(), "einstellungen", Toast.LENGTH_SHORT).show();
                                switchToSettingsFragment();
                                break;
                            case R.id.action_navigation:
                               // Toast.makeText(getApplicationContext(), "navigation", Toast.LENGTH_SHORT).show();
                                switchToNavFragment();
                                break;

                        }
                        return true;
                    }
                });





    }

    public void switchToCategoryFragment() {
        Fragment fragment = null;
        Class fragmentClass = CategoryFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
    }

    public void switchToSettingsFragment() {

        Fragment fragment = null;
        Class fragmentClass = SettingsFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }



        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
    }

    public void switchToNavFragment() {

        Fragment fragment = null;
        Class fragmentClass = NavFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }


        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
    }

    public void switchToHomeFragment() {

        Fragment fragment = null;
        Class fragmentClass = HomeFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }


        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onStop() {

        super.onStop();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putBoolean("ASKGPS", true).commit();
        Log.d("LOCATION", "I AM CALLLEEEEEEEEEEEEEEEED");

    }



    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }





}
