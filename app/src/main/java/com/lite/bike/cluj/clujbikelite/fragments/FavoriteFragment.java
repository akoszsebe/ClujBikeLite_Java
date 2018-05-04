package com.lite.bike.cluj.clujbikelite.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.lite.bike.cluj.clujbikelite.R;
import com.lite.bike.cluj.clujbikelite.adapters.MyStationListAdapter;
import com.lite.bike.cluj.clujbikelite.communication.RestClient;
import com.lite.bike.cluj.clujbikelite.model.ListViewItemStation;
import com.lite.bike.cluj.clujbikelite.model.Station;
import com.lite.bike.cluj.clujbikelite.model.StationsData;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class FavoriteFragment  extends Fragment {


    private ListView mlistview;
    private MyStationListAdapter adapter;
    private SwipeRefreshLayout refresher;
    private Thread syncThread;
    RestClient rc;


    @Override
    public  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Create your fragment here
    }

    public static FavoriteFragment newInstance()
    {
        FavoriteFragment frag1 = new FavoriteFragment();
        frag1.setArguments(new Bundle());
        return frag1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View ignored = super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main, null);

        mlistview = (ListView)view.findViewById(R.id.listViewStations);
        refresher = (SwipeRefreshLayout)view.findViewById(R.id.refresher);

        rc = new RestClient(this.getContext());
        adapter = new MyStationListAdapter(this.getActivity(), true);
        mlistview.setAdapter(adapter);

        refresher.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        SyncData();
                        refresher.setRefreshing(false);
                    }
                }
        );

        SyncData();

        return view;
    }

    public void SyncData()
    {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("favorite_stations", MODE_PRIVATE);
        final Set<String> favorite_stations = sharedPref.getStringSet("favorite_stations", new HashSet<String>());
        syncThread = new Thread(){
            @Override
            public void run() {
                rc.GetStationsData(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response : " + response.toString());
                        Gson gson = new Gson();
                        StationsData returnObj = gson.fromJson(response.toString(), StationsData.class);
                        System.out.println(returnObj.toString());
                        if (returnObj != null) {
                            Station[] stations;
                            final List<ListViewItemStation> items;
                            stations = returnObj.getData();
                            items = new ArrayList<ListViewItemStation>();
                            for (int i = 0; i < stations.length; i++) {
                                if (favorite_stations.contains(stations[i].getStationName()))
                                    items.add(new ListViewItemStation(i, stations[i], true));
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.addData(items);
                                    adapter.notifyDataSetChanged();
                                }
                            });

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error : " + error.toString());
                        //mTextMessage.setText("Error : "+ error.toString());
                    }
                });
            }
        };
        syncThread.start();
    }

    @Override
    public void onDestroy()
    {
        if (syncThread != null)
            syncThread.interrupt();
        super.onDestroy();
    }
}
