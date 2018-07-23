package com.lite.bike.cluj.clujbikelite.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lite.bike.cluj.clujbikelite.R;
import com.lite.bike.cluj.clujbikelite.adapters.MyRecyclerViewAdapter;
import com.lite.bike.cluj.clujbikelite.model.ListViewItemStation;
import com.lite.bike.cluj.clujbikelite.model.Station;
import com.lite.bike.cluj.clujbikelite.model.StationsData;
import com.lite.bike.cluj.clujbikelite.rest.ApiClient;
import com.lite.bike.cluj.clujbikelite.rest.BikeRestService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.MODE_PRIVATE;

public class FavoriteFragment  extends Fragment {

    @BindView(R.id.my_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.refresher) SwipeRefreshLayout refresher;

    private MyRecyclerViewAdapter mAdapter;
    private Thread syncThread;
    private BikeRestService bikeRestService;


    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create your fragment here
    }

    public static FavoriteFragment newInstance() {
        FavoriteFragment frag1 = new FavoriteFragment();
        frag1.setArguments(new Bundle());
        return frag1;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main,container,false);
        ButterKnife.bind(this,view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(this.getContext()), LinearLayoutManager.VERTICAL));

        mAdapter = new MyRecyclerViewAdapter(new ArrayList<ListViewItemStation>(),this.getContext());
        mRecyclerView.setAdapter(mAdapter);
        bikeRestService = ApiClient.getClient().create(BikeRestService.class);

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

    public void SyncData() {
        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getSharedPreferences("favorite_stations", MODE_PRIVATE);
        final Set<String> favorite_stations = sharedPref.getStringSet("favorite_stations", new HashSet<String>());
        syncThread = new Thread(){
            @Override
            public void run() {
                Call<StationsData> call = bikeRestService.getStationData();
                call.enqueue(new Callback<StationsData>() {
                    @Override
                    public void onResponse(@NonNull Call<StationsData> call, @NonNull retrofit2.Response<StationsData> response) {
                        StationsData resource = response.body();
                        assert resource != null;
                        Station[] stations;
                        final List<ListViewItemStation> items;
                        stations = resource.getData();
                        items = new ArrayList<>();
                        for (int i = 0; i < stations.length; i++) {
                            if (favorite_stations.contains(stations[i].getStationName()))
                                items.add(new ListViewItemStation(i, stations[i]));
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.addData(items);
                                mAdapter.notifyDataSetChanged();
                            }
                        });

                    }

                    @Override
                    public void onFailure(@NonNull Call<StationsData> call, @NonNull Throwable t) {
                        call.cancel();
                    }
                });
            }
        };
        syncThread.start();
    }

    @Override
    public void onDestroy() {
        if (syncThread != null)
            syncThread.interrupt();
        super.onDestroy();
    }

}
