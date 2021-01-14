package com.genesis.apps.comm.viewmodel;

import com.genesis.apps.comm.model.repo.MenuRepository;
import com.genesis.apps.comm.model.vo.MenuVO;
import com.genesis.apps.comm.net.NetUIResponse;

import java.util.List;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

public @Data
class MenuViewModel extends ViewModel {

    private final MenuRepository repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<List<MenuVO>>> menuList;
    private MutableLiveData<NetUIResponse<List<MenuVO>>> recentlyMenuList;
    private MutableLiveData<NetUIResponse<List<MenuVO>>> keywordMenuList;

    @ViewModelInject
    MenuViewModel(
            MenuRepository repository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;

        menuList = repository.menuList;
        recentlyMenuList = repository.recentlyMenuList;
        keywordMenuList = repository.keywordMenuList;
    }
    public void reqMenuList(){
//        menuList.setValue(repository.getMenuList().getValue());
        repository.getMenuList();
    }
    public void reqRecentlyMenuList(int action, MenuVO menuVO){
        repository.getRecentlyMenuList(action,menuVO);
    }
    public void reqKeywordMenuList(MenuVO menuVO){
        repository.getKeywordMenuList(menuVO).getValue();
    }
}