package com.lite.bike.cluj.clujbikelite.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lite.bike.cluj.clujbikelite.R;
import com.lite.bike.cluj.clujbikelite.fragments.AllFragment;
import com.lite.bike.cluj.clujbikelite.fragments.FavoriteFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 2;
    private AllFragment allFragment;
    private  FavoriteFragment favoriteFragment;

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        allFragment = AllFragment.newInstance();
        favoriteFragment = FavoriteFragment.newInstance();
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                return allFragment;
            case 1:
                return favoriteFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    public void RefreshAll(){
        allFragment.SyncData();
    }

    public void RefreshFavorite(){
        favoriteFragment.SyncData();
    }

}
