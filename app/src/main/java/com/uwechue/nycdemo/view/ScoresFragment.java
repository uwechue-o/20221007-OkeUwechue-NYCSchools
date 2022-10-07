package com.uwechue.nycdemo.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.uwechue.nycdemo.R;
import com.uwechue.nycdemo.databinding.FragmentScoresBinding;
import com.uwechue.nycdemo.model.ScoresRowItem;
import com.uwechue.nycdemo.viewmodel.ScoresViewModel;
import com.uwechue.nycdemo.viewmodel.SharedViewModel;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class ScoresFragment extends Fragment implements Observer {

    private static final String TAG = "ScoresFragment";
    private FragmentScoresBinding binding;
    private ScoresViewModel scoresViewModel;

    private SharedViewModel sharedViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when ScoresFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // navigate to the destination fragment
                NavController navController = NavHostFragment.findNavController(ScoresFragment.this);
                navController.navigate(R.id.action_scores_fragment_to_schools_fragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentScoresBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        scoresViewModel = new ScoresViewModel(getContext());
        binding.setScoresViewModel(scoresViewModel);

        //Add Observable for receiving data changes
        scoresViewModel.addObserver(this);

        //fetch and display the schools data
        scoresViewModel.loadData(binding.getRoot());
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
        if (o instanceof ScoresViewModel) {

            // retrieve the cached search term
            String schoolName = sharedViewModel.getData();
            Log.i(TAG, "Try matching on name : ["+schoolName+"]");

            Log.i(TAG, "List of school names:\n");
            scoresViewModel.getRowItemList()
                    .stream()
                    .map(item -> item.getSchoolName())
                    .forEach(System.out::println);

            Optional<ScoresRowItem> matchingEntry = scoresViewModel
                    .getRowItemList()
                    .stream()
                    .filter(item -> schoolName.equalsIgnoreCase(item.getSchoolName()))
                    .findFirst();

            // if match found, display the test score data
            if(matchingEntry.isPresent()) {
                Log.i(TAG, "Matching Entry FOUND");
                scoresViewModel.updateValues(schoolName,
                        matchingEntry.get().getNumberOfTesters(),
                        matchingEntry.get().getMathScore(),
                        matchingEntry.get().getReadingScore(),
                        matchingEntry.get().getWritingScore());
            } else {
                Log.i(TAG, "Matching Entry NOT FOUND");
                scoresViewModel.showErr();
            }

        }
    }
}
