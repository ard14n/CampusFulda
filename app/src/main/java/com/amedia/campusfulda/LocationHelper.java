package com.amedia.campusfulda;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;



public class LocationHelper implements LocationListener {

    private static LocationManager locationManager;
    private static Context myContext;
    private double longitude, latitude;

    public LocationHelper(Context context){

        this.myContext = context;
        locationManager = (LocationManager) myContext.getSystemService( Context.LOCATION_SERVICE );
    }

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    public boolean isLocationEnabled(){

        if ( locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {

            return true;

        } else {

            return false;
        }


    }


    public void enableLocationDialogBuilder(){



        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(myContext);
        if(prefs.getBoolean("ASKGPS", true)) {

            if(!isLocationEnabled()) {
                //Ask the user to enable GPS
                final AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                builder.setTitle("Standort bestimmen");
                builder.setMessage("Möchtest du GPS einschalten?");
                builder.setPositiveButton("JA", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Launch settings, allowing user to make a change
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        myContext.startActivity(i);
                    }
                });

                builder.setNegativeButton("NEIN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //No location service, no Activity
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }


            prefs.edit().putBoolean("ASKGPS", false).commit();
        }



    }

}
