package com.amedia.campusfulda.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amedia.campusfulda.LocationHelper;
import com.amedia.campusfulda.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 *
 * Zeigt die Details der ausgewählten Location an
 * und berechnet Entfernung, sowie Fußweg bis zum Ziel
 *
 *
 */

public class CategoryItemDetailsFragment extends Fragment implements OnMapReadyCallback {

    private TextView context, distance, info, opened, openedCaption, address, walking_distance;
    private MapView mapViewDetail;
    private LocationHelper locationHelper;
    private boolean isGpsEnabled;
    LocationManager lm;
    Location location;
    private double clat, clong;
    private double myLat, myLong;
    private String subject_result;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflater setzt das Fragment auf den Container und zeigt es an
        View view = inflater.inflate(R.layout.fragment_categoryitemdetails, container, false);


        //Google Maps Karte
        mapViewDetail = view.findViewById(R.id.mapviewdetail);
        mapViewDetail.onCreate(savedInstanceState);
        mapViewDetail.getMapAsync(this);

        //Location Helper Objekt
        locationHelper = new LocationHelper(getContext());

        //Prüft ob GPS eingeschaltet ist
        isGpsEnabled = locationHelper.isLocationEnabled();

        //Zeigt einen Dialog, über welchen man sein GPS einschalten kann
        locationHelper.enableLocationDialogBuilder();

        Bundle b = getArguments();

        //TextViews werden geholt
        context = view.findViewById(R.id.context);
        distance = view.findViewById(R.id.distance);
        info = view.findViewById(R.id.info);
        opened = view.findViewById(R.id.opened);
        openedCaption = view.findViewById(R.id.openedCaption);
        walking_distance = view.findViewById(R.id.walking_time);
        address = view.findViewById(R.id.address_content);

        //Holt die Daten aus dem Bundle
        subject_result = b.getString("subject");
        String info_result = b.getString("info");
        String address_result = b.getString("address");
        String opened_result = b.getString("opened");

        //Längen und Breitengrad
        clat = b.getDouble("clat");
        clong = b.getDouble("clong");

        //DEBUG
        //Toast.makeText(getContext(), "CLAT: "+clat+" CLONG: "+clong, Toast.LENGTH_SHORT).show();


        //Setzt die TextViews
        context.setText(subject_result);
        info.setText(android.text.Html.fromHtml(info_result));
        address.setText(address_result);


        //Falls Öffnungszeiten leer sind setze auf keine Öffnungszeiten
        if (opened_result.equals("")) {
            openedCaption.setText("Keine Öffnungszeiten vorhanden");
        } else {

            opened_result = opened_result.toString().replaceAll("\\<\\br>", "\n");
            opened_result = opened_result.toString().replaceAll("\\<.*?>", "");
        }

        opened.setText(opened_result);

        //Überprüft ob die Berechtigungen für GPS erteilt wurden und ob GPS an ist
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //Toast.makeText(getContext(), "Überprüfe bitte die Berechtigungen deiner App", Toast.LENGTH_SHORT).show();
            distance.setText("");

        } else {

            if (isGpsEnabled) {

                lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                //Toast.makeText(getContext(), "Okay dein GPS ist wohl an", Toast.LENGTH_SHORT).show();
                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        5000,
                        10, locationListenerGPS);

                distance.setText("GPS Signal gefunden - Berechne Entfernung");

            } else {
                distance.setText("Kein GPS");


            }
        }

        return view;

    }

    //Location Listener wartet auf Änderungen der Koordinaten
    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            //DEBUG
            //String msg="New Latitude: "+latitude + "New Longitude: "+longitude;
            //Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();

            //Holt die Entfernung
            float result[] = {0};
            android.location.Location.distanceBetween(location.getLatitude(), location.getLongitude(), clat, clong, result);
            int res = (int) result[0];

            //Holt die Entfernung und die Gehminuten
            double res_walking = (double) result[0];
            int walking_time = walkingSpeedCalculator(res_walking);

            //Setzt die Entfernung und die Gehminuten bis zum Ziel
            distance.setText(String.valueOf(res) + " Meter von dir entfernt");
            walking_distance.setText("ca. " + walking_time + " Min Fußweg");

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    @Override
    public void onResume() {
        mapViewDetail.onResume();
        //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGPS);
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapViewDetail.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapViewDetail.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapViewDetail.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        final LatLng clickedLocation = new LatLng(clat, clong);

        //Setzt den Marker auf den geklickten Standort
        map.addMarker(new MarkerOptions()
                .position(new LatLng(clat, clong))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title(subject_result));


        //Optionen der Map
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(clickedLocation, 18));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                askForPermission();
            return;
        }
        map.setMyLocationEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    //Berechnet die ungefähre Gehzeit zum Ziel anhand der Entfernung vom Standort bis zum Ziel
    private int walkingSpeedCalculator(Double distance){

        //Normale Gehgeschwindigkeit des Menschen beträgt ca 150cm/s
        //Für 1 Min gehen in Meter = 1.5*60
        double walking_speed_per_minute = 1.5*60;

        double result =  distance/walking_speed_per_minute;

        int without_decimals = (int) result;

        return without_decimals;

    }

    private void askForPermission(){

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {


                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Toast.makeText(getContext(), "Dankeeeee für die Berechtigung", Toast.LENGTH_LONG).show();

                    if(!isGpsEnabled){

                        //Toast.makeText(getContext(), "Du musst noch dein GPS einschalten", Toast.LENGTH_LONG).show();


                    } else {
                        //Toast.makeText(getContext(), "Okay die Berechtigung wurde erteilt und dein GPS ist an", Toast.LENGTH_LONG).show();

                    }

                } else {

                    //Toast.makeText(getContext(), "Ja gut dann halt nicht", Toast.LENGTH_LONG).show();


                }
                return;
            }

        }
    }





}
