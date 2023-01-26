package com.example.pokeloot_android.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pokeloot_android.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;

    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
        longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng coordenadas = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(coordenadas).title("Local do Evento"));
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMaxZoomPreference(20);
        map.setMinZoomPreference(5);
        map.moveCamera(CameraUpdateFactory.newLatLng(coordenadas));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 10));
    }
}