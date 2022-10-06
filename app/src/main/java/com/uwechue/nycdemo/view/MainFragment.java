package com.uwechue.nycdemo.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.uwechue.nycdemo.R;
import com.uwechue.nycdemo.databinding.FragmentMainBinding;
import com.uwechue.nycdemo.view.adapter.HeaderDataAdapter;
import com.uwechue.nycdemo.viewmodel.MainViewModel;

import java.util.Observable;
import java.util.Observer;

public class MainFragment extends Fragment implements Observer {

    private static final String TAG = "MainFragment";
    private FragmentMainBinding binding;
    private MainViewModel mainViewModel;

    // View initialization logic
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    // Post view initialization logic
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        binding.toolbar.setTitle(R.string.app_name);
    }

    @Override
    public void onResume() {
        super.onResume();

        mainViewModel = new MainViewModel(getContext());
        binding.setMainViewModel(mainViewModel);

        //Creating Adapter
        HeaderDataAdapter headerDataAdapter = new HeaderDataAdapter();
        binding.dataList.setAdapter(headerDataAdapter);
        binding.dataList.setLayoutManager(new LinearLayoutManager(getContext()));

        //Add Observable
        mainViewModel.addObserver(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainViewModel.reset();
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MainViewModel) {
            HeaderDataAdapter headerDataAdapter = (HeaderDataAdapter) binding.dataList.getAdapter();
            MainViewModel headerViewModel = (MainViewModel) o;
            if (headerDataAdapter != null) {
                for (int i = 0; i < headerViewModel.getRowItemList().size(); i++) {
                    if (headerViewModel.getRowItemList().get(i).getSchoolName() == null &&
                            headerViewModel.getRowItemList().get(i).getOverviewParagraph() == null)
                        headerViewModel.getRowItemList().remove(i);

                    if (headerViewModel.getRowItemList().get(i).getSchoolName() == null &&
                            headerViewModel.getRowItemList().get(i).getOverviewParagraph() == null)
                        headerViewModel.getRowItemList().remove(i);
                }
                headerDataAdapter.setRowItems(headerViewModel.getRowItemList());
            }
        }
    }
}
