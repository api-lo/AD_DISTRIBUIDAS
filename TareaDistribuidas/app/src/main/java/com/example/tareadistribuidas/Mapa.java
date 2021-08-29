package com.example.tareadistribuidas;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mapa;
    JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        Bundle bundle = this.getIntent().getExtras();
        try {
            json = new JSONObject(bundle.getString("json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void dibujarRectangulo() {
        PolylineOptions lineas = null;
        try {
            lineas = new PolylineOptions()
                    .add(new LatLng(json.getDouble("North"), json.getDouble("West")))
                    .add(new LatLng(json.getDouble("North"),json.getDouble("East")))
                    .add(new LatLng(json.getDouble("South"), json.getDouble("East")))
                    .add(new LatLng(json.getDouble("South"), json.getDouble("West")))
                    .add(new LatLng(json.getDouble("North"), json.getDouble("West")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        lineas.width(6);
        lineas.color(Color.GREEN);
        mapa.addPolyline(lineas);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.getUiSettings().setZoomControlsEnabled(true);
        dibujarRectangulo();
        LatLng central = null;
        try {
            central = new LatLng(json.getDouble("North"), json.getDouble("West"));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(central)
                    .build();
            mapa.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}