package com.genesis.apps.comm.model.main.myg;

import com.genesis.apps.comm.model.vo.MenuVO;
import com.genesis.apps.comm.net.NetUIResponse;

import java.util.List;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

import static com.genesis.apps.comm.model.main.myg.MenuRepository.ACTION_GET_MENU_ALL;

public @Data
class MenuViewModel extends ViewModel {

    private final MenuRepository repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<List<MenuVO>>> menuList;
    private MutableLiveData<NetUIResponse<List<MenuVO>>> recentlyMenuList;
    private MutableLiveData<NetUIResponse<List<MenuVO>>> keywordMenuList;

//    public final LiveData<VehicleVO> carVO = Transformations.map(RES_LGN_0001, input -> input.data.getCarVO());
//    public final LiveData<VehicleVO> carVO =
//            Transformations.switchMap(RES_LGN_0001, new Function<NetUIResponse<LGN_0001.Response>, LiveData<VehicleVO>>() {
//                @Override
//                public LiveData<VehicleVO> apply(NetUIResponse<LGN_0001.Response> input) {
//                    return input.data.getCarVO(); repo에서 getcarvo로 가저올수있는.. 다른걸 요청 가능
//                }
//            });

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