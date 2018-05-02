package com.lite.bike.cluj.clujbikelite.model;

public class ListViewItemStation {
    public long Id;
    public Station station;
    public boolean is_favorite;

    public ListViewItemStation(long id, Station station,boolean is_favorite)
    {
        this.Id = id;
        this.station = station;
        this.is_favorite = is_favorite;

    }
}
