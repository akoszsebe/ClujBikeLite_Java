package com.lite.bike.cluj.clujbikelite.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lite.bike.cluj.clujbikelite.R;
import com.lite.bike.cluj.clujbikelite.model.ListViewItemStation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyStationListAdapter extends BaseAdapter {

    List<ListViewItemStation> itemList;
    Context context;
    Activity activity;
    boolean favorite;

    public MyStationListAdapter(Activity context, boolean favorite)
    {
        this.activity = context;
        this.context = context;
        this.favorite = favorite;
        itemList = new ArrayList<ListViewItemStation>();
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return itemList.get(position).Id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.stationistviewitem, parent, false);
        TextView tvstationname = (TextView)view.findViewById(R.id.tvStationName);
        TextView tvstationAddress = (TextView)view.findViewById(R.id.tvStationAddress);
        TextView tvactivedevicenumber = (TextView)view.findViewById(R.id.tvOcuppiedSpotsNumber);
        TextView tvValue = (TextView) view.findViewById(R.id.tvValue);
        ImageView ivsettings = (ImageView)view.findViewById(R.id.imageviewAddToFavourites);
        ImageView img_favorite = (ImageView)view.findViewById(R.id.imageviewAddToFavourites);
        ImageView imageviewActive = (ImageView)view.findViewById(R.id.imageviewActive);

        if (itemList.get(position).is_favorite)
        {
            img_favorite.setImageResource(R.drawable.favouriteselected_48);
        }
        else
        {
            img_favorite.setImageResource(R.drawable.favourite_50);
        }




        final ListViewItemStation station = itemList.get(position);
        if (!station.station.getStatus().equals("Functionala") && !station.station.getStatusType().equals("Online"))
        {
            imageviewActive.setImageResource(R.drawable.in_active);
        }

        if (favorite)
        {
            img_favorite.setImageResource(R.drawable.favouriteselected_48);
            img_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPref = activity.getSharedPreferences("favorite_stations", Context.MODE_PRIVATE);
                    final Set<String> favorite_stations = sharedPref.getStringSet("favorite_stations", new HashSet<String>());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    Set<String> new_favorite_stations = new HashSet<>();
                    new_favorite_stations.addAll(favorite_stations);
                    new_favorite_stations.remove(station.station.getStationName());
                    editor.putStringSet("favorite_stations", new_favorite_stations);
                    editor.apply();
                    itemList.remove(station);
                    notifyDataSetChanged();
                }
            });
        }
        else
        {
            img_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPref = activity.getSharedPreferences("favorite_stations", Context.MODE_PRIVATE);
                    final Set<String> favorite_stations = sharedPref.getStringSet("favorite_stations", new HashSet<String>());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    Set<String> new_favorite_stations = new HashSet<>();
                    new_favorite_stations.addAll(favorite_stations);
                    new_favorite_stations.add(station.station.getStationName());
                    editor.putStringSet("favorite_stations", new_favorite_stations);
                    editor.apply();
                    itemList.get((int)station.Id).is_favorite = true;
                    notifyDataSetChanged();
                }
            });
        }

        tvstationname.setText(itemList.get(position).station.getStationName());
        tvstationAddress.setText(itemList.get(position).station.getAddress());
        tvactivedevicenumber.setText("Bikes "+itemList.get(position).station.getOcuppiedSpots());
        tvValue.setText("Parking "+itemList.get(position).station.getEmptySpots());

        return view;
    }

    public void addData(List<ListViewItemStation> items)
    {
        itemList.clear();
        itemList.addAll(items);
    }
}
