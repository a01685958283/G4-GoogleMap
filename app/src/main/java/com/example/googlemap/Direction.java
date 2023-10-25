package com.example.googlemap;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class Direction extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap myMap;

    //convert String to LatLng
    public LatLng stringToLatLng(String location) {
        try {
            List<Address> addressList = null;

            if (location != null) {
                Geocoder geocoder = new Geocoder(Direction.this);

                try {
                    addressList = geocoder.getFromLocationName(location, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Xử lý lỗi khi không thể truy cập dịch vụ địa chỉ
                    System.out.println("ERROR: at geocoder");

                    return null; // hoặc giá trị mặc định khác tùy bạn
                }

                if (addressList != null && !addressList.isEmpty()) {
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    return latLng;
                } else {
                    // Xử lý trường hợp không tìm thấy địa chỉ
                    return null; // hoặc giá trị mặc định khác tùy bạn
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi khác (nếu có)
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        LatLng from = null;
        LatLng to = null;

        String fromStr = null;
        String toStr = null;

        Intent intent = getIntent();
        if (intent != null) {
            fromStr = intent.getStringExtra("userLocation");
            toStr = intent.getStringExtra("userDestination");

            // Chuyển đổi chuỗi thành LatLng
            from = stringToLatLng(fromStr);
            to = stringToLatLng(toStr);

            System.out.println("TEST from and to: "+from+" : "+to);

            // Bây giờ bạn có thể sử dụng `from` và `to` trong hoạt động Direction


//            myMap.addMarker(new MarkerOptions().position(to).title(toStr));
//            myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(to, 10));

        }

//        myMap.addMarker(new MarkerOptions().position(from).title(fromStr));
//        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(from, 10));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(Direction.this);


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setCompassEnabled(true);

        // Xác định điểm xuất phát và điểm đích (LatLng)
        LatLng origin = new LatLng(37.7749, -122.4194); // Ví dụ: San Francisco
        LatLng destination = new LatLng(34.0522, -118.2437); // Ví dụ: Los Angeles

        // Gửi yêu cầu hướng dẫn đến Google Maps Directions API
        String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=" + origin.latitude + "," + origin.longitude +
                "&destination=" + destination.latitude + "," + destination.longitude +
                "&key=YOUR_API_KEY";
    }


}
