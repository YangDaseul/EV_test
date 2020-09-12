package com.genesis.apps.ui.common.view.listview.test;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MyViewModel extends ViewModel {
    public final MutableLiveData<List<Link>> usersList = new MutableLiveData<>();

    @ViewModelInject
    MyViewModel(
            @Assisted SavedStateHandle savedStateHandle)
    {

    }

    public MyViewModel(List<Link> userList) {
        this.usersList.setValue(userList);
    }

    public void setUsersList(List<Link> userList) {
        this.usersList.setValue(userList);
    }

    public void addUsersList(List<Link> userList) {
//        this.usersList.getValue().addAll(userList);
//        this.usersList.setValue(this.usersList.getValue());
        this.usersList.setValue(userList);
    }
}