package com.genesis.apps.comm.viewmodel;

import android.text.TextUtils;

import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.gra.api.CMN_0001;
import com.genesis.apps.comm.model.gra.api.CMN_0002;
import com.genesis.apps.comm.model.gra.api.CMN_0003;
import com.genesis.apps.comm.model.gra.api.CMN_0004;
import com.genesis.apps.comm.model.repo.CMNRepo;
import com.genesis.apps.comm.model.repo.DBContentsRepository;
import com.genesis.apps.comm.model.repo.DBGlobalDataRepository;
import com.genesis.apps.comm.model.vo.CardVO;
import com.genesis.apps.comm.model.vo.FloatingMenuVO;
import com.genesis.apps.comm.model.vo.QuickMenuVO;
import com.genesis.apps.comm.model.vo.WeatherVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.genesis.apps.room.ResultCallback;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

import static com.genesis.apps.comm.model.vo.CardVO.CARD_STATUS_20;
import static com.genesis.apps.comm.model.vo.CardVO.CARD_STATUS_30;

public @Data
class CMNViewModel extends ViewModel {

    private final CMNRepo repository;
    private final DBGlobalDataRepository dbGlobalDataRepository;
    private final DBContentsRepository dbContentsRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<CMN_0001.Response>> RES_CMN_0001;
    private MutableLiveData<NetUIResponse<CMN_0002.Response>> RES_CMN_0002;
    private MutableLiveData<NetUIResponse<CMN_0003.Response>> RES_CMN_0003;
    private MutableLiveData<NetUIResponse<CMN_0004.Response>> RES_CMN_0004;

    @ViewModelInject
    CMNViewModel(
            CMNRepo repository,
            DBGlobalDataRepository dbGlobalDataRepository,
            DBContentsRepository dbContentsRepository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.repository = repository;
        this.dbGlobalDataRepository = dbGlobalDataRepository;
        this.dbContentsRepository = dbContentsRepository;
        this.savedStateHandle = savedStateHandle;

        RES_CMN_0001 = repository.RES_CMN_0001;
        RES_CMN_0002 = repository.RES_CMN_0002;
        RES_CMN_0003 = repository.RES_CMN_0003;
        RES_CMN_0004 = repository.RES_CMN_0004;
    }

    public void reqCMN0001(final CMN_0001.Request reqData){
        repository.REQ_CMN_0001(reqData);
    }
    public void reqCMN0002(final CMN_0002.Request reqData){
        repository.REQ_CMN_0002(reqData);
    }
    public void reqCMN0003(final CMN_0003.Request reqData){
        repository.REQ_CMN_0003(reqData);
    }
    public void reqCMN0004(final CMN_0004.Request reqData){
        repository.REQ_CMN_0004(reqData);
    }

    public void updateNotiDt(String notiDt){
        dbGlobalDataRepository.update(KeyNames.KEY_NAME_DB_GLOBAL_DATA_NOTIDT, notiDt);
    }

    public boolean setWeatherList(List<WeatherVO> list){
        return dbContentsRepository.setWeatherList(list);
    }

    public boolean setQuickMenu(List<QuickMenuVO> list, String type){
        return dbContentsRepository.setQuickMenu(list,type);
    }
    public boolean setFloatingMenu(List<FloatingMenuVO> list, String type){
        return dbContentsRepository.setFloatingMenu(list,type);
    }

    public void setContents(CMN_0002.Response data, ResultCallback callback){
        ExecutorService es = new ExecutorService("");
        Futures.addCallback(es.getListeningExecutorService().submit(() -> {

            boolean isSuccess=false;

            try {
                isSuccess = setWeatherList(data.getWthrInsgtList())
                        ||setQuickMenu(data.getMenu0000().getQckMenuList(), "menu0000")
                        ||setFloatingMenu(data.getMenu0000().getMenuList(), "menu0000")
                        ||setQuickMenu(data.getMenuCV().getQckMenuList(), "menuCV")
                        ||setFloatingMenu(data.getMenuCV().getMenuList(), "menuCV")
                        ||setQuickMenu(data.getMenuNV().getQckMenuList(), "menuNV")
                        ||setFloatingMenu(data.getMenuNV().getMenuList(), "menuNV")
                        ||setQuickMenu(data.getMenuOV().getQckMenuList(), "menuOV")
                        ||setFloatingMenu(data.getMenuOV().getMenuList(), "menuOV");
            } catch (Exception e1) {
                e1.printStackTrace();
                isSuccess=false;
            }
            return isSuccess;
        }), new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(@NullableDecl Boolean isSuccess) {
                callback.onSuccess(isSuccess);
                es.shutDownExcutor();
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onError(t);
                es.shutDownExcutor();
            }
        }, es.getUiThreadExecutor());
    }


}