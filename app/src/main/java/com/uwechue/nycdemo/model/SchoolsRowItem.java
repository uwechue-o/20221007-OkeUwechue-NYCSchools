package com.uwechue.nycdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * POJO model class to hold the Row data items response from the server
 */
public class SchoolsRowItem {

    @SerializedName("school_name")
    @Expose
    private String schoolName;
    @SerializedName("overview_paragraph")
    @Expose
    private String overviewParagraph;

    @Expose
    private String location;

    @Expose
    private String borough;

    @Expose
    private String neighborhood;

    @SerializedName("phone_number")
    @Expose
    private String phoneNUmber;

    @SerializedName("school_email")
    @Expose
    private String schoolEmail;

    @Expose
    private String website;

    @Expose
    private String grades2018;

    @Expose
    private String city;

    @Expose
    private String zip;

    @SerializedName("attendance_rate")
    @Expose
    private String attendanceRate;

    @SerializedName("finalgrades")
    @Expose
    private String finalGrades;

    @SerializedName("extracurricular_activities")
    @Expose
    private String extracurricularActivities;

    @SerializedName("total_students")
    @Expose
    private String totalStudents;

    @SerializedName("school_sports")
    @Expose
    private String schoolSports;

    @SerializedName("state_code")
    @Expose
    private String stateCode;

    @Expose(deserialize = false)
    private String boro;

    @Expose(deserialize = false)
    private String dbn;

    @Expose(deserialize = false)
    private String subway;

    @Expose(deserialize = false)
    private String bus;

    @Expose(deserialize = false)
    private String latitude;

    @Expose(deserialize = false)
    private String longitude;

    @SerializedName("community_board")
    @Expose(deserialize = false)
    private String communityBoard;

    @SerializedName("council_district")
    @Expose(deserialize = false)
    private String councilDistrict;

    // GETTERS
    public String getSchoolName() { return schoolName; }
    public String getOverviewParagraph() { return overviewParagraph; }

    // SETTERS
    public void setSchoolName(String title) { this.schoolName = schoolName; }
    public void setDescription(String description) { this.overviewParagraph = overviewParagraph; }

}