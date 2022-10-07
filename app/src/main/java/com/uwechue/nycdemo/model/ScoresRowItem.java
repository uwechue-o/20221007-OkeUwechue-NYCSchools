package com.uwechue.nycdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * POJO model class to hold the Row data items response from the server
 */
public class ScoresRowItem {

    @SerializedName("school_name")
    @Expose
    private String schoolName;

    @SerializedName("num_of_sat_test_takers")
    @Expose
    private String numberOfTesters;

    @SerializedName("sat_critical_reading_avg_score")
    @Expose
    private String readingScore;

    @SerializedName("sat_math_avg_score")
    @Expose
    private String mathScore;

    @SerializedName("sat_writing_avg_score")
    @Expose
    private String writingScore;

    // GETTERS
    public String getSchoolName() { return schoolName; }
    public String getNumberOfTesters() { return numberOfTesters; }
    public String getReadingScore() { return readingScore; }
    public String getMathScore() { return mathScore; }
    public String getWritingScore() { return writingScore; }

}