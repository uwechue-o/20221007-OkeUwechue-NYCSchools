package com.uwechue.nycdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * POJO model class to hold the JSON response from the server
 * This holds the entire data response
 */
public class ScoresListData {

    @SerializedName("rows")
    @Expose
    private List<ScoresRowItem> rows = null;

    public List<ScoresRowItem> getRows() {
        return rows;
    }

    public void setRows(List<ScoresRowItem> rows) {
        this.rows = rows;
    }

}