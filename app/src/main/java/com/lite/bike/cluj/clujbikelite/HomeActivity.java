package com.lite.bike.cluj.clujbikelite;

import android.os.Build;
import android.os.Bundle;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.AbsListView;

import com.google.gson.Gson;
import com.lite.bike.cluj.clujbikelite.adapters.ViewPagerAdapter;
import com.lite.bike.cluj.clujbikelite.communication.RestClient;


import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    RestClient rc;
    private Gson gson = new Gson();
    Fragment fragment = null;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private boolean pause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setHomeButtonEnabled(true);
        }
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        TabItem tabChats = findViewById(R.id.tab_all);
        TabItem tabStatus = findViewById(R.id.tab_favorites);


        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), appBarLayout);
        viewPager.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        rc = new RestClient(this);

    }

    @Override
    protected void onResume(){
        super.onResume();
        if(adapter!= null && pause) {
            adapter.Refresh(0);
            adapter.Refresh(1);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        pause = true;
    }

}
