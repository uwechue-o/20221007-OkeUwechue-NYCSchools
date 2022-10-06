package com.uwechue.nycdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * POJO model class to hold the JSON response from the server
 * This holds the entire data response
 */
public class SchoolsListData {

    @SerializedName("rows")
    @Expose
    private List<SchoolsRowItem> rows = null;

    public List<SchoolsRowItem> getRows() {
        return rows;
    }

    public void setRows(List<SchoolsRowItem> rows) {
        this.rows = rows;
    }

}