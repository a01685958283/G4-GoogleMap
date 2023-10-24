package com.example.googlemap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap myMap;
    private SearchView mapSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapSearchView = findViewById(R.id.mapSearch);

        mapSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                String location = mapSearchView.getQuery().toString();
                List<Address> addressList = null;

                if(location != null){
                    Geocoder geocoder = new Geocoder(MainActivity.this);

                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    myMap.addMarker(new MarkerOptions().position(latLng).title(s));
                }

                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
        mapFragment.getMapAsync(MainActivity.this);

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
//        LatLng hanoi = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        LatLng hnoi = new LatLng(21.0285, 105.8542);
        myMap.addMarker(new MarkerOptions().position(hnoi).title("Hanoi"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(hnoi));

        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setCompassEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        int none = GoogleMap.MAP_TYPE_NONE;
        int normal = GoogleMap.MAP_TYPE_NORMAL;
        int satellite = GoogleMap.MAP_TYPE_SATELLITE;
        int terrain = GoogleMap.MAP_TYPE_TERRAIN;
        int hybrid = GoogleMap.MAP_TYPE_HYBRID;

        if(id == R.id.mapNone){

            myMap.setMapType(none);
        } else if (id == R.id.mapNormal) {
            myMap.setMapType(normal);
        } else if (id == R.id.mapSatellite) {
            myMap.setMapType(satellite);
        } else if (id == R.id.mapTerrain) {
            myMap.setMapType(terrain);
        } else if (id == R.id.mapHybrid) {
            myMap.setMapType(hybrid);
        }
        return super.onOptionsItemSelected(item);
    }
}