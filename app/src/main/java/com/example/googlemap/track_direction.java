package com.example.googlemap;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class track_direction extends AppCompatActivity {

    private EditText etFromLocation;
    private EditText etToLocation;
    private Button btnGetDirection;

    private GoogleMap myMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_direction);

        //get edit text and button by id

        etFromLocation = findViewById(R.id.etFromLocation);
        etToLocation = findViewById(R.id.etToLocation);
        btnGetDirection = findViewById(R.id.btnGetDirection);


            //check null
        btnGetDirection.setOnClickListener(view -> {
            String userLocation = etFromLocation.getText().toString();
            String userDestination = etToLocation.getText().toString();

            if(userLocation.equals("") || userDestination.equals("")){
                Toast.makeText(this, "Please enter your location and destination!", Toast.LENGTH_SHORT).show();
            }else {

                getDirection(userLocation, userDestination);

            }

        });

    }

    private void getDirection(String from, String to){
        try {
            Uri uri = Uri.parse("https://www.google.com/maps/dir/"+from+"/"+to);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


        }catch (Exception e){
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }



}
