package com.lite.bike.cluj.clujbikelite.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lite.bike.cluj.clujbikelite.Info_MapsActivity;
import com.lite.bike.cluj.clujbikelite.R;
import com.lite.bike.cluj.clujbikelite.model.ListViewItemStation;
import com.lite.bike.cluj.clujbikelite.model.Station;

import java.util.List;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    @BindString(R.string.bikes_UpperCase) String str_bikes;
    @BindString(R.string.parking_Uppercase) String str_parking;

    private List<ListViewItemStation> mDataset;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @BindView(R.id.tvStationName) TextView tvstationname;
        @BindView(R.id.tvStationAddress) TextView tvstationAddress;
        @BindView(R.id.tvOcuppiedSpotsNumber) TextView tvactivedevicenumber;
        @BindView(R.id.tvValue) TextView tvValue;
        @BindView(R.id.imageviewAddToFavourites) ImageView ivsettings;
        @BindView(R.id.imageviewActive) ImageView imageviewActive;

        public View view;

        ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyRecyclerViewAdapter(List<ListViewItemStation> myDataset,Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stationistviewitem, parent, false);
        ButterKnife.bind(this,view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListViewItemStation station = mDataset.get(position);

        holder.imageviewActive.setImageResource(statusIconSelector(station.station));

        final Intent info_MapsActivity = new Intent(context, Info_MapsActivity.class);
        info_MapsActivity.putExtra("station_name", station.station.getStationName());
        info_MapsActivity.putExtra("longitude", station.station.getLongitude());
        info_MapsActivity.putExtra("latitude", station.station.getLatitude());
        info_MapsActivity.putExtra("bikes", station.station.getOcuppiedSpots().toString());
        info_MapsActivity.putExtra("parking", station.station.getEmptySpots().toString());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(info_MapsActivity);
            }
        });

        holder.ivsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:"+station.station.getLatitude()+","+station.station.getLongitude()+"?q="+station.station.getLatitude()+","+station.station.getLongitude() + "("+station.station.getStationName()+")");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });

        holder.tvstationname.setText( mDataset.get(position).station.getStationName());
        holder.tvstationAddress.setText( mDataset.get(position).station.getAddress());
        holder.tvactivedevicenumber.setText(String.format(Locale.ENGLISH,"%s %d", str_bikes, mDataset.get(position).station.getOcuppiedSpots()));
        holder.tvValue.setText(String.format(Locale.ENGLISH,"%s %d", str_parking, mDataset.get(position).station.getEmptySpots()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addData(List<ListViewItemStation> items)
    {
        mDataset.clear();
        mDataset.addAll(items);//.toArray(new ListViewItemStation[items.size()]);
    }


    private int statusIconSelector(Station station){
        if (station.getStatus().contains("Functional")){
            if(station.getStatusType().equals("Online"))
                return R.drawable.active;
            if (station.getStatusType().equals("Suprapopulated"))
                    return  R.drawable.overcrowded;
            if (station.getStatusType().equals("Subpopulated"))
                    return  R.drawable.underpopulated;
            else if (station.getStatusType().equals("Offline"))
                return R.drawable.offline;
        }
        return R.drawable.in_active;
    }
}
