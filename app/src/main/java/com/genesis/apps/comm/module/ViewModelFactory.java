//package com.genesis.apps.comm.module;
//
//import java.util.Map;
//
//import javax.inject.Provider;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.ViewModel;
//import androidx.lifecycle.ViewModelProvider;
//
//public class ViewModelFactory implements ViewModelProvider.Factory {
//    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
//        mProviderMap = providerMap;
//    }
//
//    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> mProviderMap;
//
//    @SuppressWarnings("unchecked")
//    @NonNull
//    @Override
//    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        return (T) mProviderMap.get(modelClass).get();
//    }
//}
