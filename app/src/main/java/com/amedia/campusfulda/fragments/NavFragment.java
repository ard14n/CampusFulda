package com.amedia.campusfulda.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amedia.campusfulda.CampusItems;
import com.amedia.campusfulda.DatabaseHelper;
import com.amedia.campusfulda.LocationHelper;
import com.amedia.campusfulda.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;


public class NavFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private DatabaseHelper myDatabase;
    private boolean isGpsEnabled;
    private LocationHelper locationHelper;
    private List<CampusItems> campuslist;


    public NavFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        locationHelper = new LocationHelper(getContext());

        //isGpsEnabled = locationHelper.isLocationEnabled();

        myDatabase = DatabaseHelper.createInstance(getContext(), null, 1);

        mapView = view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        return view;

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }



    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    /*
    * Wenn die Map fertig geladen ist werden die CampusItems aus der Datenbank geholt
    * und in einer for-Schleife als Marker auf der Karte gesetzt
    *
    * */

    @Override
    public void onMapReady(GoogleMap map) {

        campuslist = myDatabase.getAllItems();
        final LatLng HSFULDA = new LatLng(50.565739, 9.686574);

        for (ListIterator<CampusItems> iter = campuslist.listIterator(); iter.hasNext(); ) {
            CampusItems c = iter.next();


            String name = c.getName();
            String info = c.getInfo();
            double clat = c.getLat();
            double clong = c.getLong();

            map.addMarker(new MarkerOptions()
                    .position(new LatLng(clat, clong))
                    .title(name)
                    .snippet(info)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));



        }


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(HSFULDA, 15));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.setTrafficEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
    }




}
