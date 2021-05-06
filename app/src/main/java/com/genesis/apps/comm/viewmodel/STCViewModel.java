package com.genesis.apps.comm.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.api.gra.STC_1001;
import com.genesis.apps.comm.model.api.gra.STC_1002;
import com.genesis.apps.comm.model.api.gra.STC_1003;
import com.genesis.apps.comm.model.api.gra.STC_1004;
import com.genesis.apps.comm.model.api.gra.STC_1005;
import com.genesis.apps.comm.model.api.gra.STC_1006;
import com.genesis.apps.comm.model.api.gra.STC_2001;
import com.genesis.apps.comm.model.repo.DBVehicleRepository;
import com.genesis.apps.comm.model.repo.STCRepo;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.excutor.ExecutorService;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import lombok.Data;

/**
 * @author Ki-man Kim
 */
public @Data
class STCViewModel extends ViewModel {

    private STCRepo repository;
    private final DBVehicleRepository dbVehicleRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<STC_1001.Response>> RES_STC_1001;
    private MutableLiveData<NetUIResponse<STC_1002.Response>> RES_STC_1002;
    private MutableLiveData<NetUIResponse<STC_1003.Response>> RES_STC_1003;
    private MutableLiveData<NetUIResponse<STC_1004.Response>> RES_STC_1004;
    private MutableLiveData<NetUIResponse<STC_1005.Response>> RES_STC_1005;
    private MutableLiveData<NetUIResponse<STC_1006.Response>> RES_STC_1006;
    private MutableLiveData<NetUIResponse<STC_2001.Response>> RES_STC_2001;

    private MutableLiveData<List<VehicleVO>> vehicleList;

    @ViewModelInject
    public STCViewModel(
            STCRepo repository,
            DBVehicleRepository dbVehicleRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.dbVehicleRepository = dbVehicleRepository;
        this.savedStateHandle = savedStateHandle;
        this.vehicleList = new MutableLiveData<>();

        RES_STC_1001 = repository.RES_STC_1001;
        RES_STC_1002 = repository.RES_STC_1002;
        RES_STC_1003 = repository.RES_STC_1003;
        RES_STC_1004 = repository.RES_STC_1004;
        RES_STC_1005 = repository.RES_STC_1005;
        RES_STC_1006 = repository.RES_STC_1006;
        RES_STC_2001 = repository.RES_STC_2001;
    }

    public void reqSTC1001(final STC_1001.Request reqData) {
        repository.REQ_STC_1001(reqData);
    }

    public void reqSTC1002(final STC_1002.Request reqData) {
        repository.REQ_STC_1002(reqData);
    }

    public void reqSTC1003(final STC_1003.Request reqData) {
        repository.REQ_STC_1003(reqData);
    }

    public void reqSTC1004(final STC_1004.Request reqData) {
        repository.REQ_STC_1004(reqData);
    }

    public void reqSTC1005(final STC_1005.Request reqData) {
        repository.REQ_STC_1005(reqData);
    }

    public void reqSTC1006(final STC_1006.Request reqData) {
        repository.REQ_STC_1006(reqData);
    }

    public void reqSTC2001(final STC_2001.Request reqData) {
        repository.REQ_STC_2001(reqData);
    }


    public VehicleVO getMainVehicle() throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<VehicleVO> future = es.getListeningExecutorService().submit(()->{
            VehicleVO vehicleVO = null;
            try {
                vehicleVO = dbVehicleRepository.getMainVehicleSimplyFromDB();
            } catch (Exception ignore) {
                ignore.printStackTrace();
                vehicleVO = null;
            }
            return vehicleVO;
        });

        try {
            return future.get();
        }finally {
            es.shutDownExcutor();
        }
    }

} // end of class STCViewModel
