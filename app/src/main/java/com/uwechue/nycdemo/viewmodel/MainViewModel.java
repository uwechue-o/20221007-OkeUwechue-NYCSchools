package com.uwechue.nycdemo.viewmodel;

import android.content.Context;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.uwechue.nycdemo.R;
import com.uwechue.nycdemo.model.SchoolsRowItem;
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
public class MainViewModel extends Observable {

    private static final String TAG = "MainViewModel";

    public ObservableInt progressBar;
    public ObservableInt rowRecycler;
    public ObservableInt rowLabel;
    public ObservableField<String> messageLabel;

    private List<SchoolsRowItem> rowItemList;

    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Context context) {
        this.context = context;
        this.rowItemList = new ArrayList<>();
        progressBar = new ObservableInt(View.GONE);
        rowRecycler = new ObservableInt(View.GONE);
        rowLabel = new ObservableInt(View.VISIBLE);
        messageLabel = new ObservableField<>(context.getString(R.string.load_data));
    }

    public void onClickFabToLoad(View view) {
        initializeViews();
        if (!Utils.isNetworkAvailable(context)) {
            Snackbar.make(view, R.string.network_error, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            messageLabel.set(context.getString(R.string.network_error)
                    + "\n"
                    + context.getString(R.string.load_data));
            progressBar.set(View.GONE);
            rowLabel.set(View.VISIBLE);
            rowRecycler.set(View.GONE);
            return;
        }
        fetchSchoolsData();
    }

    public void initializeViews() {
        rowLabel.set(View.GONE);
        rowRecycler.set(View.GONE);
        progressBar.set(View.VISIBLE);
    }

    private void fetchSchoolsData() {
        io.reactivex.Observable<List<SchoolsRowItem>> responseObservable = ApiClient
                .getClient()
                .create(ApiService.class)
                .fetchSchoolsData();

        Disposable disposable = ApiCallHelper.call(responseObservable, new ApiCallback<List<SchoolsRowItem>>() {
            @Override
            public void onSuccess(List<SchoolsRowItem> headerListData) {
                Log.d(TAG, "List of data = " + headerListData);
                if (headerListData == null) {
                    messageLabel.set(context.getString(R.string.callback_error));
                    progressBar.set(View.GONE);
                    rowLabel.set(View.VISIBLE);
                    rowRecycler.set(View.GONE);
                    return;
                }
                updateRowDataList(headerListData);
                progressBar.set(View.GONE);
                rowLabel.set(View.GONE);
                rowRecycler.set(View.VISIBLE);
            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.d(TAG, "Error = " + throwable.getMessage());
                messageLabel.set(context.getString(R.string.callback_error));
                progressBar.set(View.GONE);
                rowLabel.set(View.VISIBLE);
                rowRecycler.set(View.GONE);
            }
        });

        compositeDisposable.add(disposable);
    }

    private void updateRowDataList(List<SchoolsRowItem> peoples) {
        rowItemList.addAll(peoples);

        setChanged();
        notifyObservers();
    }

    public List<SchoolsRowItem> getRowItemList() {
        return rowItemList;
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }
}

