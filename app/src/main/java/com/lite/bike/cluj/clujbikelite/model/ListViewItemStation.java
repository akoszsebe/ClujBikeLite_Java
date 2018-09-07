package com.lite.bike.cluj.clujbikelite.model;

import android.support.annotation.NonNull;

public class ListViewItemStation{
    public long Id;
    public Station station;

    public ListViewItemStation(long id, Station station)
    {
        this.Id = id;
        this.station = station;
    }

}
