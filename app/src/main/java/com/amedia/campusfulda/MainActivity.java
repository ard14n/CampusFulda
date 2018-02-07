package com.amedia.campusfulda;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.amedia.campusfulda.fragments.CategoryFragment;
import com.amedia.campusfulda.fragments.QuicklinksFragment;
import com.amedia.campusfulda.fragments.NavFragment;
import com.amedia.campusfulda.fragments.InfoFragment;

/*
* MainActivity der Applikation und ist für grundlegende Dinge zuständig, wie
*
* - Titel der ActionBar setzten
* - ShiftMode der BottomBar ausschalten
* - Standard-Fragment aufrufen
* - Klicks auf die einzelnen Menüpunkte
* - Klick auf Zurück-Button
*
*
*
* */

public class MainActivity extends AppCompatActivity implements CategoryFragment.OnFragmentInteractionListener,
                                                                NavFragment.OnFragmentInteractionListener,
                                                                InfoFragment.OnFragmentInteractionListener,
                                                                QuicklinksFragment.OnFragmentInteractionListener {

    private String json;
    private DatabaseHelper myDatabase;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setzt den Titel der App
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);

        //Holt die BottomNavigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.navView);

        //Sorgt dafür, dass sich die Elemente nicht verschieben
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        //Setzt das Fragment, dass man beim App-Start sieht
        switchToDefaultFragment();

        //Der BottomNavListener
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_quicklinks:
                                switchToQuicklinksFragment();
                                break;
                            case R.id.action_search:
                                //Toast.makeText(getApplicationContext(), "suche", Toast.LENGTH_SHORT).show();
                                switchToCategoryFragment();
                                break;
                            case R.id.action_info:
                                //Toast.makeText(getApplicationContext(), "einstellungen", Toast.LENGTH_SHORT).show();
                                switchToInfoFragment();
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

    /**
     * Wechselt zum Standard-Fragment bei App-Start, ohne dass das Fragment
     * zum BackStack hinzugefügt wird.
     * Fixt den Whitescreen-Fragment-Bug wenn man lange genug auf Zurück gedrückt hat
     *
     */

    public void switchToDefaultFragment(){
        Fragment fragment = null;
        Class fragmentClass = CategoryFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }


    //Wechselt zum Kategorien-Fragment
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

    //Wechselt zum Info-Fragment
    public void switchToInfoFragment() {

        Fragment fragment = null;
        Class fragmentClass = InfoFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
    }

    //Wechselt zum Karten-Fragment
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

    //Wechselt zum Quicklinks-Fragment
    public void switchToQuicklinksFragment() {

        Fragment fragment = null;
        Class fragmentClass = QuicklinksFragment.class;

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

        /*
        * Setzt in den SharedPreferences den Wert für ASKGPS auf True, wenn die App geschlossen wird.
        * Damit beim nächsten Start wieder nachgefragt wird ob der Nutzer sein GPS einschalten will,
        * falls er es noch nicht getan hat.
        *
        */
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putBoolean("ASKGPS", true).commit();

        //DEBUG
        //Log.d("LOCATION", "I AM CALLLEEEEEEEEEEEEEEEED");

    }


    //Ist zuständig für das Drücken der Zurück-Taste
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }





}
