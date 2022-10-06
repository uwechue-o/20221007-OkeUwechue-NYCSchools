package com.uwechue.nycdemo.network;

import static com.uwechue.nycdemo.utility.Utils.LEVEL_1_ENDPOINT;
import static com.uwechue.nycdemo.utility.Utils.LEVEL_2_ENDPOINT;

import com.uwechue.nycdemo.model.SchoolsListData;
import com.uwechue.nycdemo.model.SchoolsRowItem;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Interface defining all the API calling method signatures.
 */
public interface ApiService {

    // Fetch the schools data
    @GET(LEVEL_1_ENDPOINT)
    Observable<List<SchoolsRowItem>> fetchSchoolsData();

    // Fetch the schools data
    @GET(LEVEL_2_ENDPOINT)
    Observable<List<SchoolsRowItem>> fetchSATScores();

}