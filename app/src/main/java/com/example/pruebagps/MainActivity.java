package com.example.pruebagps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import com.google.android.gms.maps.model.StreetViewSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements OnStreetViewPanoramaReadyCallback {

    private StreetViewPanorama streetViewPanorama;
    private boolean secondlocation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportStreetViewPanoramaFragment streetViewPanoramaFragment = (SupportStreetViewPanoramaFragment)getSupportFragmentManager()
                .findFragmentById(R.id.googlemapsstreetview);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondlocation = !secondlocation;
                onStreetViewPanoramaReady(streetViewPanorama);
            }
        });

    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        streetViewPanorama = streetViewPanorama;
        if(secondlocation){
            streetViewPanorama.setPosition(new LatLng(51.52887,-0.1726073), StreetViewSource.OUTDOOR);
        }else {
            streetViewPanorama.setPosition(new LatLng(51.52887, -0.1726073));
        }
        streetViewPanorama.setStreetNamesEnabled(true);
        streetViewPanorama.setPanningGesturesEnabled(true);
        streetViewPanorama.setZoomGesturesEnabled(true);
        streetViewPanorama.setUserNavigationEnabled(true);
        streetViewPanorama.animateTo(
                new StreetViewPanoramaCamera.Builder().orientation(new StreetViewPanoramaOrientation(20,20))
                        .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                        .build(), 2000
        );
        streetViewPanorama.setOnStreetViewPanoramaCameraChangeListener((StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener) panoramachangelistner);
    }

    private StreetViewPanorama.OnStreetViewPanoramaChangeListener panoramachangelistner = new StreetViewPanorama.OnStreetViewPanoramaChangeListener() {
        @Override
        public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
            Toast.makeText(MainActivity.this, "Location update", Toast.LENGTH_SHORT).show();
        }
    };
}