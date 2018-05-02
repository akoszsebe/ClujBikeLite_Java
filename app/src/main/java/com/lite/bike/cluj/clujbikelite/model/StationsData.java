package com.lite.bike.cluj.clujbikelite.model;

import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.*;
import com.google.gson.annotations.SerializedName;

public class StationsData {
    @SerializedName("Data")
    private Station[] data;
    @SerializedName("Total")
    private long total;
    @SerializedName("AggregateResults")
    private Object aggregateResults;
    @SerializedName("Errors")
    private Object errors;

    public StationsData() {
        super();
    }

    public StationsData(Station[] data, long total, Object aggregateResults, Object errors) {
        this.data = data;
        this.total = total;
        this.aggregateResults = aggregateResults;
        this.errors = errors;
    }

    public Station[] getData() { return data; }
    public void setData(Station[] value) { this.data = value; }

    public Long getTotal() { return total; }
    public void setTotal(Long value) { this.total = value; }

    public Object getAggregateResults() { return aggregateResults; }
    public void setAggregateResults(Object value) { this.aggregateResults = value; }

    public Object getErrors() { return errors; }
    public void setErrors(Object value) { this.errors = value; }

    @Override
    public String toString() {
        return "StationsData{" +
                "Data=" + Arrays.toString(data) +
                ", Total=" + total +
                ", AggregateResults=" + aggregateResults +
                ", Errors=" + errors +
                '}';
    }
}
