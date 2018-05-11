package com.lite.bike.cluj.clujbikelite;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashSet;
import java.util.Set;

public class Info_MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Toolbar toolbar;
    Double longitude;
    Double latitude;
    String station_name;
    boolean is_Favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info__maps);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        station_name = getIntent().getStringExtra("station_name");
        longitude = getIntent().getDoubleExtra("longitude",0.00);
        latitude = getIntent().getDoubleExtra("latitude",0.00);
        String bikes = getIntent().getStringExtra("bikes");
        String parking = getIntent().getStringExtra("parking");

        SharedPreferences sharedPref = getSharedPreferences("favorite_stations", MODE_PRIVATE);
        final Set<String> favorite_stations = sharedPref.getStringSet("favorite_stations", new HashSet<String>());


        TextView tv_bikes = (TextView) findViewById(R.id.textViev_bikes);
        TextView tv_parking = (TextView) findViewById(R.id.textViev_parking);

        tv_bikes.setText(tv_bikes.getText()+"     "+bikes );
        tv_parking.setText(tv_parking.getText()+"  " +parking);
        is_Favorite = favorite_stations.contains(station_name);

        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(station_name);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapView mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng marker = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(marker).title(station_name));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker,16.0f));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_info_maps_menu, menu);
        if (is_Favorite)
            menu.findItem(R.id.menu_favorite).setIcon(R.drawable.favorit_blue_filled);
        else
            menu.findItem(R.id.menu_favorite).setIcon(R.drawable.favorite_blue);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public  boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId() == R.id.menu_favorite){
            if (is_Favorite) {
                item.setIcon(R.drawable.favorite_blue);
                SharedPreferences sharedPref = getSharedPreferences("favorite_stations", Context.MODE_PRIVATE);
                final Set<String> favorite_stations = sharedPref.getStringSet("favorite_stations", new HashSet<String>());
                SharedPreferences.Editor editor = sharedPref.edit();
                Set<String> new_favorite_stations = new HashSet<>();
                new_favorite_stations.addAll(favorite_stations);
                new_favorite_stations.remove(station_name);
                editor.putStringSet("favorite_stations", new_favorite_stations);
                editor.apply();
                is_Favorite = false;
            }else{
                item.setIcon(R.drawable.favorit_blue_filled);
                SharedPreferences sharedPref = getSharedPreferences("favorite_stations", Context.MODE_PRIVATE);
                final Set<String> favorite_stations = sharedPref.getStringSet("favorite_stations", new HashSet<String>());
                SharedPreferences.Editor editor = sharedPref.edit();
                Set<String> new_favorite_stations = new HashSet<>();
                new_favorite_stations.addAll(favorite_stations);
                new_favorite_stations.add(station_name);
                editor.putStringSet("favorite_stations", new_favorite_stations);
                editor.apply();
                is_Favorite = true;
            }
        }
        return  super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}
