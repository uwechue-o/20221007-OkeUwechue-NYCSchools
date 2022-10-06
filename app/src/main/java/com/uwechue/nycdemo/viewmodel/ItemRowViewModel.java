package com.uwechue.nycdemo.viewmodel;

import android.content.Context;
import androidx.databinding.BaseObservable;
import com.uwechue.nycdemo.model.SchoolsRowItem;

/**
 * This class is responsible to update the individual Rows of the recycler View
 * We get the data from the RecyclerView Adapter class
 */
public class ItemRowViewModel extends BaseObservable {

    private SchoolsRowItem rowItem;
    private Context context;

    public ItemRowViewModel(SchoolsRowItem rowItem, Context context) {
        this.rowItem = rowItem;
        this.context = context;
    }

    public String getOverview() {
        return rowItem.getOverviewParagraph();
    }

    public String getTitle() {
        return rowItem.getSchoolName();
    }

    public String getAttendanceRate() {
        return rowItem.getAttendanceRate();
    }

    public String getBorough() {
        return rowItem.getBorough();
    }

    public String getPhoneNumber() {
        return rowItem.getPhoneNumber();
    }

    public String getWebsite() {
        return rowItem.getWebsite();
    }

    public void setRowItem(SchoolsRowItem rowItem) {
        this.rowItem = rowItem;
        notifyChange();
    }
}
