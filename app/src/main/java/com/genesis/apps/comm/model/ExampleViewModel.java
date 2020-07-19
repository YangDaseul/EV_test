package com.genesis.apps.comm.model;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class ExampleViewModel extends ViewModel {

    private final ExampleRepository repository;
    private final SavedStateHandle savedStateHandle;

    @ViewModelInject
    ExampleViewModel(
            ExampleRepository repository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;
    }
}