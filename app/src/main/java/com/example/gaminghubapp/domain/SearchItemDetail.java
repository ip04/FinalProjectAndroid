package com.example.gaminghubapp.domain;

import java.util.List;

import com.example.gaminghubapp.domain.Datum;
import com.example.gaminghubapp.domain.Metadata;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchItemDetail {

    @SerializedName("data")
    @Expose
    private List<com.example.gaminghubapp.domain.Datum> data;
    @SerializedName("metadata")
    @Expose
    private com.example.gaminghubapp.domain.Metadata metadata;

    public List<com.example.gaminghubapp.domain.Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public com.example.gaminghubapp.domain.Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

}
