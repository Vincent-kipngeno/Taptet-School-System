package com.moringaschool.schoolsystem.ui.fees;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FeesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FeesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is fees fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}