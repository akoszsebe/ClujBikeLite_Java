package com.lite.bike.cluj.clujbikelite;

import android.os.Bundle;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        TabItem tabChats = findViewById(R.id.tab_all);
        TabItem tabStatus = findViewById(R.id.tab_favorites);

        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1){
                    adapter.RefreshFavorite();
                }
                else {
                    adapter.RefreshAll();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //tabLayout.setupWithViewPager(viewPager);

        rc = new RestClient(this);
    }

//    boolean LoadFragment(int id)
//    {
//        switch (id)
//        {
//            case R.id.menu_all:
//                fragment = AllFragment.newInstance();
//                break;
//            case R.id.menu_favourites:
//                fragment = FavoriteFragment.newInstance();
//                break;
//        }
//        if (fragment == null)
//            return false;
//
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.content_frame, fragment)
//                .commit();
//        return true;
//    }
//
//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            if (fragment != null)
//            {
//                fragment.onDestroy();
//            }
//            return LoadFragment(item.getItemId());
//        }
//    };



}
