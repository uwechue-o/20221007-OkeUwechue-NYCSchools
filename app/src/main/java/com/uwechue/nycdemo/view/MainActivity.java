package com.uwechue.nycdemo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.uwechue.nycdemo.R;

public class MainActivity extends AppCompatActivity {

    //region CREATION CYCLE
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_container);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

    }
    //endregion

    /**
     * Returns fragment currently being displayed
     *
     * @return
     */
    private Fragment getCurrentChild(){
        Fragment currentChild;

        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        currentChild = navHostFragment.getChildFragmentManager().getFragments().get(0);

        return(currentChild);
    }

}