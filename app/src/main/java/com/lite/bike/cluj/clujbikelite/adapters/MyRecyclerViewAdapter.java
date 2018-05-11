package com.lite.bike.cluj.clujbikelite.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lite.bike.cluj.clujbikelite.Info_MapsActivity;
import com.lite.bike.cluj.clujbikelite.R;
import com.lite.bike.cluj.clujbikelite.model.ListViewItemStation;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private List<ListViewItemStation> mDataset;
    Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvstationname;
        public TextView tvstationAddress;
        public TextView tvactivedevicenumber;
        public TextView tvValue;
        public ImageView ivsettings;
        public ImageView imageviewActive;

        public ViewHolder(View itemView){
            super(itemView);
            tvstationname = (TextView)itemView.findViewById(R.id.tvStationName);
            tvstationAddress = (TextView)itemView.findViewById(R.id.tvStationAddress);
            tvactivedevicenumber = (TextView)itemView.findViewById(R.id.tvOcuppiedSpotsNumber);
            tvValue = (TextView) itemView.findViewById(R.id.tvValue);
            ivsettings = (ImageView)itemView.findViewById(R.id.imageviewAddToFavourites);
            imageviewActive = (ImageView)itemView.findViewById(R.id.imageviewActive);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyRecyclerViewAdapter(List<ListViewItemStation> myDataset,Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stationistviewitem, parent, false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final ListViewItemStation station = mDataset.get(position);
        if (!station.station.getStatus().equals("Functionala") && !station.station.getStatusType().equals("Online"))
        {
            holder.imageviewActive.setImageResource(R.drawable.in_active);
        }

        holder.ivsettings.setOnClickListener(new View.OnClickListener() {
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

        holder.tvstationname.setText( mDataset.get(position).station.getStationName());
        holder.tvstationAddress.setText( mDataset.get(position).station.getAddress());
        holder.tvactivedevicenumber.setText("Bikes "+ mDataset.get(position).station.getOcuppiedSpots());
        holder.tvValue.setText("Parking "+ mDataset.get(position).station.getEmptySpots());

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
}