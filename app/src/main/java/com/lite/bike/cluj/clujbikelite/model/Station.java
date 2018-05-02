package com.lite.bike.cluj.clujbikelite.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Station {
    @SerializedName("StationName")
    private String stationName;
    @SerializedName("Address")
    private String address;
    @SerializedName("OcuppiedSpots")
    private long ocuppiedSpots;
    @SerializedName("EmptySpots")
    private long emptySpots;
    @SerializedName("MaximumNumberOfBikes")
    private long maximumNumberOfBikes;
    @SerializedName("LastSyncDate")
    private String lastSyncDate;
    @SerializedName("IdStatus")
    private long idStatus;
    @SerializedName("Status")
    private String status;
    @SerializedName("StatusType")
    private String statusType;
    @SerializedName("Latitude")
    private double latitude;
    @SerializedName("Longitude")
    private double longitude;
    @SerializedName("IsValid")
    private boolean isValid;
    @SerializedName("CustomIsValid")
    private boolean customIsValid;
    @SerializedName("Notifies")
    private Object[] notifies;
    @SerializedName("Id")
    private long id;

    public Station() {
        super();
    }

    public Station(String stationName, String address, long ocuppiedSpots, long emptySpots, long maximumNumberOfBikes, String lastSyncDate, long idStatus, String status, String statusType, double latitude, double longitude, boolean isValid, boolean customIsValid, Object[] notifies, long id) {
        this.stationName = stationName;
        this.address = address;
        this.ocuppiedSpots = ocuppiedSpots;
        this.emptySpots = emptySpots;
        this.maximumNumberOfBikes = maximumNumberOfBikes;
        this.lastSyncDate = lastSyncDate;
        this.idStatus = idStatus;
        this.status = status;
        this.statusType = statusType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isValid = isValid;
        this.customIsValid = customIsValid;
        this.notifies = notifies;
        this.id = id;
    }

    public String getStationName() { return stationName; }
    public void setStationName(String value) { this.stationName = value; }

    public String getAddress() { return address; }
    public void setAddress(String value) { this.address = value; }

    public Long getOcuppiedSpots() { return ocuppiedSpots; }
    public void setOcuppiedSpots(Long value) { this.ocuppiedSpots = value; }

    public Long getEmptySpots() { return emptySpots; }
    public void setEmptySpots(Long value) { this.emptySpots = value; }

    public Long getMaximumNumberOfBikes() { return maximumNumberOfBikes; }
    public void setMaximumNumberOfBikes(Long value) { this.maximumNumberOfBikes = value; }

    public String getLastSyncDate() { return lastSyncDate; }
    public void setLastSyncDate(String value) { this.lastSyncDate = value; }

    public Long getIDStatus() { return idStatus; }
    public void setIDStatus(Long value) { this.idStatus = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }

    public String getStatusType() { return statusType; }
    public void setStatusType(String value) { this.statusType = value; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double value) { this.latitude = value; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double value) { this.longitude = value; }

    public Boolean getIsValid() { return isValid; }
    public void setIsValid(Boolean value) { this.isValid = value; }

    public Boolean getCustomIsValid() { return customIsValid; }
    public void setCustomIsValid(Boolean value) { this.customIsValid = value; }

    public Object[] getNotifies() { return notifies; }
    public void setNotifies(Object[] value) { this.notifies = value; }

    public Long getID() { return id; }
    public void setID(Long value) { this.id = value; }

    @Override
    public String toString() {
        return "Station{" +
                "StationName='" + stationName + '\'' +
                ", Address='" + address + '\'' +
                ", OcuppiedSpots=" + ocuppiedSpots +
                ", EmptySpots=" + emptySpots +
                ", MaximumNumberOfBikes=" + maximumNumberOfBikes +
                ", LastSyncDate='" + lastSyncDate + '\'' +
                ", IdStatus=" + idStatus +
                ", Status='" + status + '\'' +
                ", StatusType='" + statusType + '\'' +
                ", Latitude=" + latitude +
                ", Longitude=" + longitude +
                ", IsValid=" + isValid +
                ", CustomIsValid=" + customIsValid +
                ", Notifies=" + Arrays.toString(notifies) +
                ", Id=" + id +
                '}';
    }
}
