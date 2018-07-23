package com.lite.bike.cluj.clujbikelite.rest;

import com.lite.bike.cluj.clujbikelite.model.StationsData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BikeRestService {
    @POST("Station/Read")
    Call<StationsData> getStationData();
}
