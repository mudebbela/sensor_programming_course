package com.example.hw3fragmentkeyboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ButtonPressViewModel extends ViewModel {
    private final MutableLiveData<String> string = new MutableLiveData<String>();
    public LiveData<String> getString(){
        return string;
    }

    public LiveData<String> getStringData(){
        return string;
    };

    public void setString(String newString){

        string.setValue(newString);

    }
}
