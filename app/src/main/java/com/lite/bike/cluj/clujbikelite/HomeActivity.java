package com.lite.bike.cluj.clujbikelite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lite.bike.cluj.clujbikelite.adapters.ViewPagerAdapter;

import java.util.Objects;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout  tabLayout;
    @BindView(R.id.appbar) AppBarLayout appBarLayout;

    @BindDrawable(R.drawable.icon_white) Drawable icon_white;

    private ViewPagerAdapter adapter;
    private boolean pause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ButterKnife.bind(this);

        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setIcon(icon_white);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        SharedPreferences sharedPref = this.getSharedPreferences("allchecked", MODE_PRIVATE);
        boolean allchecked = sharedPref.getBoolean("allchecked",true);

        if (allchecked) {
            viewPager.setCurrentItem(0);
        }
        else {
            viewPager.setCurrentItem(1);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public  boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_info_settings){
            Intent info_MapsActivity = new Intent(this, Info_SettingActivity.class);
            startActivity(info_MapsActivity);
        }
        return  super.onOptionsItemSelected(item);
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
