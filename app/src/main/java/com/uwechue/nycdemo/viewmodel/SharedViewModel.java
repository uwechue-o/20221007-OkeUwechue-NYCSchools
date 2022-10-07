package com.uwechue.nycdemo.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Allows fragments in the navigation flow to share data between each other
 *
 */
public class SharedViewModel extends ViewModel {

    private MutableLiveData<String> schoolNameLivedata = new MutableLiveData<>();

    public void setData(String str){
        this.schoolNameLivedata.setValue(str);
    }

    public String getData(){
        return(schoolNameLivedata.getValue());
    }

}
