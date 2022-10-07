package com.uwechue.nycdemo.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.google.android.material.snackbar.Snackbar;
import com.uwechue.nycdemo.R;
import com.uwechue.nycdemo.model.ScoresRowItem;
import com.uwechue.nycdemo.network.ApiCallHelper;
import com.uwechue.nycdemo.network.ApiCallback;
import com.uwechue.nycdemo.network.ApiClient;
import com.uwechue.nycdemo.network.ApiService;
import com.uwechue.nycdemo.utility.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * ViewModel for handling the Main Fragment data,
 * Initiates the network call to fetch the JSON data.
 * Based on the response we update the data for the RecyclerView
 */
public class ScoresViewModel extends Observable {

    private static final String TAG = "ScoresViewModel";

    public ObservableInt progressBar;
    public ObservableInt statusLabel;
    public ObservableField<String> messageLabel;
    public ObservableField<String> schoolName;
    public ObservableField<String> testers;
    public ObservableField<String> mathematics;
    public ObservableField<String> reading;
    public ObservableField<String> writing;

    private static List<ScoresRowItem> rowItemList = new ArrayList<>();

    private Context context;
   // private CompositeDisposable compositeDisposable = new CompositeDisposable();
   Disposable disposable;

    public ScoresViewModel(@NonNull Context context) {
        this.context = context;

        progressBar = new ObservableInt(View.GONE);
        statusLabel = new ObservableInt(View.VISIBLE);
        messageLabel = new ObservableField<>("");
        schoolName = new ObservableField<>("");
        testers = new ObservableField<>("");
        mathematics = new ObservableField<>("");
        reading = new ObservableField<>("");
        writing = new ObservableField<>("");
    }

    public void loadData(View view) {
        initializeViews();
        if (!Utils.isNetworkAvailable(context)) {
            Snackbar.make(view, R.string.network_error, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            messageLabel.set(context.getString(R.string.network_error));
            progressBar.set(View.GONE);
            statusLabel.set(View.VISIBLE);
            return;
        }

        if(rowItemList == null || rowItemList.isEmpty())
            fetchScoresData();
        else
            progressBar.set(View.GONE);
    }

    public void initializeViews() {
        statusLabel.set(View.GONE);
        progressBar.set(View.VISIBLE);
    }

    private void fetchScoresData() {
        io.reactivex.Observable<List<ScoresRowItem>> responseObservable = ApiClient
                .getClient()
                .create(ApiService.class)
                .fetchSATScores();

        disposable = ApiCallHelper.call(responseObservable, new ApiCallback<List<ScoresRowItem>>() {
            @Override
            public void onSuccess(List<ScoresRowItem> scoresListData) {
                Log.i(TAG, "SUCCESS: Scores Data = " + scoresListData);
                progressBar.set(View.GONE);
                if (scoresListData == null) {
                    messageLabel.set(context.getString(R.string.callback_error));
                    statusLabel.set(View.VISIBLE);
                    return;
                }
                cacheRowDataList(scoresListData);
                statusLabel.set(View.GONE);
            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.i(TAG, "FAILURE: Error = " + throwable.getMessage());
                messageLabel.set(context.getString(R.string.callback_error));
                progressBar.set(View.GONE);
                statusLabel.set(View.VISIBLE);
            }
        });

    }

    private void cacheRowDataList(List<ScoresRowItem> scores) {
        Log.i(TAG, "Now cacheing data...");
        rowItemList.addAll(scores);

        setChanged();
        notifyObservers();
    }

    public List<ScoresRowItem> getRowItemList() {
        return rowItemList;
    }

    private void unSubscribeFromObservable() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void updateValues(String school,
                             String numberOfTesters,
                             String mathScore,
                             String readingScore,
                             String writingScore) {
        Log.i(TAG, "Schoolname null ? "+ (schoolName==null));
        schoolName.set(school);
        Log.i(TAG, "NumTesters null ? "+ (testers==null));
        testers.set(numberOfTesters);
        Log.i(TAG, "Math null ? "+ (mathematics==null));
        mathematics.set(mathScore);
        Log.i(TAG, "Reading null ? "+ (reading==null));
        reading.set(readingScore);
        Log.i(TAG, "Writing null ? "+ (writing==null));
        writing.set(writingScore);

        messageLabel.set("Ok. Data collected --");
        statusLabel.set(View.VISIBLE);
    }

    public void reset() {
        unSubscribeFromObservable();
        disposable = null;
        context = null;
    }

    public void showErr() {
        messageLabel.set(context.getString(R.string.callback_error));
        progressBar.set(View.GONE);
        statusLabel.set(View.VISIBLE);

        unSubscribeFromObservable();
        disposable = null;
        context = null;

    }
}

