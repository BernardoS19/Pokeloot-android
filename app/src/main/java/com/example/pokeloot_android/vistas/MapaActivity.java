package com.example.pokeloot_android.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.AsyncTaskLoader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pokeloot_android.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    private static final int REQUEST_CODE = 101;

    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
        longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
                    assert mapFragment != null;
                    mapFragment.getMapAsync(MapaActivity.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng coordenadas = new LatLng(latitude, longitude);
        LatLng currLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        map.addMarker(new MarkerOptions().position(coordenadas).title("Local do Evento"));
        map.addMarker(new MarkerOptions().position(currLocation).title("Minha Localização"));

        Polyline polyline = map.addPolyline(new PolylineOptions().add(currLocation, coordenadas));
        polyline.setTag("Precurso");

        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMaxZoomPreference(20);
        map.setMinZoomPreference(5);
        map.moveCamera(CameraUpdateFactory.newLatLng(currLocation));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 10));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }

}