package com.uwechue.nycdemo.view.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.uwechue.nycdemo.R;
import com.uwechue.nycdemo.databinding.ItemRowsBinding;
import com.uwechue.nycdemo.model.SchoolsRowItem;
import com.uwechue.nycdemo.viewmodel.ItemRowViewModel;

import java.util.Collections;
import java.util.List;

/**
 * The Recycler View Adapter.
 * Fetches data from the ViewModel when the Databinding Observable gets called.
 * Adapter class also gets the clean data from the Observable update() method
 */
public class HeaderDataAdapter extends RecyclerView.Adapter<HeaderDataAdapter.RowItemAdapterViewHolder> {

    private List<SchoolsRowItem> rowItems;

    private View lastClickedView;

    public HeaderDataAdapter() {
        this.rowItems = Collections.emptyList();
    }

    @Override
    public RowItemAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemRowsBinding itemRowsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_rows, parent, false);
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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Item Clicked - ", Toast.LENGTH_SHORT).show();
                if (lastClickedView != null) {
                    lastClickedView.setBackgroundColor(Color.parseColor("#EEEEEE"));
                }
                view.setBackgroundColor(Color.parseColor("#333333"));
                lastClickedView = view;
            }
        });
    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public void setRowItems(List<SchoolsRowItem> rowItems) {
        this.rowItems = rowItems;
        notifyDataSetChanged();
    }

    public static class RowItemAdapterViewHolder extends RecyclerView.ViewHolder {

        ItemRowsBinding itemRowsBinding;

        public RowItemAdapterViewHolder(ItemRowsBinding itemRowsBinding) {
            super(itemRowsBinding.itemRowCardview);
            this.itemRowsBinding = itemRowsBinding;
        }

        void bindRowView(SchoolsRowItem rowItem) {
            if (itemRowsBinding.getMainViewModel() == null) {
                itemRowsBinding.setMainViewModel(new ItemRowViewModel(rowItem, itemView.getContext()));
            } else {
                itemRowsBinding.getMainViewModel().setRowItem(rowItem);
            }
        }
    }
}
