package com.lite.bike.cluj.clujbikelite.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lite.bike.cluj.clujbikelite.Info_MapsActivity;
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
        ImageView imageviewActive = (ImageView)view.findViewById(R.id.imageviewActive);

        final ListViewItemStation station = itemList.get(position);
        if (!station.station.getStatus().equals("Functionala") && !station.station.getStatusType().equals("Online"))
        {
            imageviewActive.setImageResource(R.drawable.in_active);
        }

        ivsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info_MapsActivity = new Intent(context, Info_MapsActivity.class);
                info_MapsActivity.putExtra("station_name", station.station.getStationName());
                info_MapsActivity.putExtra("longitude", station.station.getLongitude());
                info_MapsActivity.putExtra("latitude", station.station.getLatitude());
                info_MapsActivity.putExtra("bikes", station.station.getOcuppiedSpots().toString());
                info_MapsActivity.putExtra("parking", station.station.getEmptySpots().toString());
                context.startActivity(info_MapsActivity);
            }
        });

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
