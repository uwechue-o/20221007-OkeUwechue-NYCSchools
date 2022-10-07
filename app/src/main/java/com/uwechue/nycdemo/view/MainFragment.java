package com.uwechue.nycdemo.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.uwechue.nycdemo.R;
import com.uwechue.nycdemo.databinding.FragmentMainBinding;
import com.uwechue.nycdemo.view.adapter.SchoolsDataAdapter;
import com.uwechue.nycdemo.viewmodel.MainViewModel;
import com.uwechue.nycdemo.viewmodel.SharedViewModel;

import java.util.Observable;
import java.util.Observer;

public class MainFragment extends Fragment implements Observer {

    private static final String TAG = "MainFragment";
    private FragmentMainBinding binding;
    private MainViewModel mainViewModel;

    private SharedViewModel sviewModel;

    // View initialization logic
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        sviewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        mainViewModel = new MainViewModel(getContext());
        binding.setMainViewModel(mainViewModel);

        //Creating Adapter
        SchoolsDataAdapter schoolsDataAdapter = new SchoolsDataAdapter(sviewModel,
                                                                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment));
        binding.dataList.setAdapter(schoolsDataAdapter);
        binding.dataList.setLayoutManager(new LinearLayoutManager(getContext()));

        //Add Observable
        mainViewModel.addObserver(this);

        //fetch and display the schools data
        mainViewModel.loadData(binding.getRoot());
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
            SchoolsDataAdapter schoolsDataAdapter = (SchoolsDataAdapter) binding.dataList.getAdapter();
            MainViewModel vm = (MainViewModel) o;
            if (schoolsDataAdapter != null) {
                for (int i = 0; i < vm.getRowItemList().size(); i++) {
                    if (vm.getRowItemList().get(i).getSchoolName() == null
                            && vm.getRowItemList().get(i).getOverviewParagraph() == null)
                        vm.getRowItemList().remove(i);

                    if (vm.getRowItemList().get(i).getSchoolName() == null
                            && vm.getRowItemList().get(i).getOverviewParagraph() == null)
                        vm.getRowItemList().remove(i);
                }
                schoolsDataAdapter.setRowItems(vm.getRowItemList());
            }
        }
    }
}
