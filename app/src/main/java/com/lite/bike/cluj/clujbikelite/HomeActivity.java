package com.lite.bike.cluj.clujbikelite;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.lite.bike.cluj.clujbikelite.communication.RestClient;
import com.lite.bike.cluj.clujbikelite.fragments.AllFragment;
import com.lite.bike.cluj.clujbikelite.fragments.FavoriteFragment;
import com.lite.bike.cluj.clujbikelite.model.StationsData;

import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    RestClient rc;
    private Gson gson = new Gson();
    BottomNavigationView bottomNavigation;
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);

        }

        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        LoadFragment(R.id.menu_all);

        rc = new RestClient(this);
    }

    boolean LoadFragment(int id)
    {
        switch (id)
        {
            case R.id.menu_all:
                fragment = AllFragment.newInstance();
                break;
            case R.id.menu_favourites:
                fragment = FavoriteFragment.newInstance();
                break;
        }
        if (fragment == null)
            return false;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (fragment != null)
            {
                fragment.onDestroy();
            }
            return LoadFragment(item.getItemId());
        }
    };



}
