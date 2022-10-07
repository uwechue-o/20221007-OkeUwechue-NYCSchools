package com.uwechue.nycdemo.view.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uwechue.nycdemo.R;
import com.uwechue.nycdemo.databinding.ItemRowsBinding;
import com.uwechue.nycdemo.model.SchoolsRowItem;
import com.uwechue.nycdemo.viewmodel.SchoolItemRowViewModel;
import com.uwechue.nycdemo.viewmodel.SharedViewModel;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

/**
 * The Recycler View Adapter.
 * Fetches data from the ViewModel when the Databinding Observable gets called.
 * Adapter class also gets the clean data from the Observable update() method
 */
public class SchoolsDataAdapter extends RecyclerView.Adapter<SchoolsDataAdapter.RowItemAdapterViewHolder> {

    private static final String TAG = "SchoolsDataAdapter";
    private final WeakReference<SharedViewModel> svm;

    private List<SchoolsRowItem> rowItems;

    private WeakReference<View> lastClickedView;
    private final WeakReference<NavController> nav;

    public SchoolsDataAdapter(SharedViewModel svm, NavController nav) {
        this.rowItems = Collections.emptyList();
        this.svm = new WeakReference<>(svm);
        this.nav = new WeakReference<>(nav);
    }

    @Override
    public RowItemAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemRowsBinding itemRowsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                                                                    R.layout.item_rows,
                                                                    parent,
                                                        false);
        return new RowItemAdapterViewHolder(itemRowsBinding);
    }

    @Override
    public void onBindViewHolder(RowItemAdapterViewHolder holder, int position) {
        holder.bindRowView(rowItems.get(position));

        if (rowItems.get(position).getOverviewParagraph() == null) {
            holder.itemRowsBinding.labelDescription.setVisibility(View.GONE);
        } else {
            holder.itemRowsBinding.labelDescription.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public void setRowItems(List<SchoolsRowItem> rowItems) {
        this.rowItems = rowItems;
        notifyDataSetChanged();
    }

    public class RowItemAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemRowsBinding itemRowsBinding;

        public RowItemAdapterViewHolder(ItemRowsBinding itemRowsBinding) {
            super(itemRowsBinding.itemRowCardview);
            this.itemRowsBinding = itemRowsBinding;

            itemRowsBinding.itemRowCardview.setOnClickListener(this);
        }

        void bindRowView(SchoolsRowItem rowItem) {
            if (itemRowsBinding.getMainViewModel() == null) {
                itemRowsBinding.setMainViewModel(new SchoolItemRowViewModel(rowItem, itemView.getContext()));
            } else {
                itemRowsBinding.getMainViewModel().setRowItem(rowItem);
            }
        }
        @Override
        public void onClick(View view) {
            //View tmp = lastClickedView == null ? null : lastClickedView.get();
            //if (tmp != null) {
            //    tmp.setBackgroundColor(Color.parseColor("#EEEEEE"));
            //}
            view.setBackgroundColor(Color.parseColor("#333333"));
           // lastClickedView = new WeakReference<>(view);

            // transmit name of selected school to the destination fragment
            SharedViewModel safe_svm = svm.get();
            NavController safe_nav = nav.get();
            if(svm.get()!=null && nav.get()!=null) {
                Log.i(TAG, "Writing data to shared viewmodel... ["+itemRowsBinding.labelRowTitle.getText().toString()+"]");
                svm.get().setData(itemRowsBinding.labelRowTitle.getText().toString());
                // navigate to the destination fragment
                nav.get().navigate(R.id.action_schools_fragment_to_scores_fragment);
            } else {
                Log.i(TAG, "WeakRefs were NULL (!)");
            }

        }
    }
}
