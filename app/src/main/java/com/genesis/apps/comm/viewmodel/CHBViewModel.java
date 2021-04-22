package com.genesis.apps.comm.viewmodel;

import android.text.TextUtils;
import android.text.format.DateUtils;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.api.gra.CHB_1006;
import com.genesis.apps.comm.model.api.gra.CHB_1007;
import com.genesis.apps.comm.model.api.gra.CHB_1008;
import com.genesis.apps.comm.model.api.gra.CHB_1009;
import com.genesis.apps.comm.model.api.gra.CHB_1015;
import com.genesis.apps.comm.model.api.gra.CHB_1016;
import com.genesis.apps.comm.model.api.gra.CHB_1017;
import com.genesis.apps.comm.model.api.gra.CHB_1021;
import com.genesis.apps.comm.model.api.gra.CHB_1023;
import com.genesis.apps.comm.model.api.gra.CHB_1024;
import com.genesis.apps.comm.model.api.gra.CHB_1026;
import com.genesis.apps.comm.model.repo.CHBRepo;
import com.genesis.apps.comm.model.repo.DBVehicleRepository;
import com.genesis.apps.comm.model.vo.RepairHistVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.carlife.BookingVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.excutor.ExecutorService;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import lombok.Data;

public @Data
class CHBViewModel extends ViewModel {

    private final CHBRepo repository;
    private final DBVehicleRepository dbVehicleRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<CHB_1006.Response>> RES_CHB_1006;
    private MutableLiveData<NetUIResponse<CHB_1007.Response>> RES_CHB_1007;
    private MutableLiveData<NetUIResponse<CHB_1008.Response>> RES_CHB_1008;
    private MutableLiveData<NetUIResponse<CHB_1009.Response>> RES_CHB_1009;
    private MutableLiveData<NetUIResponse<CHB_1015.Response>> RES_CHB_1015;
    private MutableLiveData<NetUIResponse<CHB_1016.Response>> RES_CHB_1016;
    private MutableLiveData<NetUIResponse<CHB_1017.Response>> RES_CHB_1017;
    private MutableLiveData<NetUIResponse<CHB_1021.Response>> RES_CHB_1021;
    private MutableLiveData<NetUIResponse<CHB_1023.Response>> RES_CHB_1023;
    private MutableLiveData<NetUIResponse<CHB_1024.Response>> RES_CHB_1024;
    private MutableLiveData<NetUIResponse<CHB_1026.Response>> RES_CHB_1026;

    private MutableLiveData<List<VehicleVO>> vehicleList;

    @ViewModelInject
    CHBViewModel(
            CHBRepo repository,
            DBVehicleRepository dbVehicleRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.dbVehicleRepository = dbVehicleRepository;
        this.savedStateHandle = savedStateHandle;

        RES_CHB_1006 = repository.RES_CHB_1006;
        RES_CHB_1007 = repository.RES_CHB_1007;
        RES_CHB_1008 = repository.RES_CHB_1008;
        RES_CHB_1009 = repository.RES_CHB_1009;

        RES_CHB_1015 = repository.RES_CHB_1015;
        RES_CHB_1016 = repository.RES_CHB_1016;
        RES_CHB_1017 = repository.RES_CHB_1017;

        RES_CHB_1021 = repository.RES_CHB_1021;
        RES_CHB_1023 = repository.RES_CHB_1023;
        RES_CHB_1024 = repository.RES_CHB_1024;
        RES_CHB_1026 = repository.RES_CHB_1026;

        vehicleList = new MutableLiveData<>();
    }

    public void reqCHB1006(final CHB_1006.Request reqData) {
        repository.REQ_CHB_1006(reqData);
    }
    public void reqCHB1007(final CHB_1007.Request reqData) {
        repository.REQ_CHB_1007(reqData);
    }
    public void reqCHB1008(final CHB_1008.Request reqData) {
        repository.REQ_CHB_1008(reqData);
    }
    public void reqCHB1009(final CHB_1009.Request reqData) {
        repository.REQ_CHB_1009(reqData);
    }
    public void reqCHB1015(final CHB_1015.Request reqData) {
        repository.REQ_CHB_1015(reqData);
    }
    public void reqCHB1016(final CHB_1016.Request reqData) {
        repository.REQ_CHB_1016(reqData);
    }
    public void reqCHB1017(final CHB_1017.Request reqData) {
        repository.REQ_CHB_1017(reqData);
    }
    public void reqCHB1021(final CHB_1021.Request reqData) {
        repository.REQ_CHB_1021(reqData);
    }
    public void reqCHB1023(final CHB_1023.Request reqData) {
        repository.REQ_CHB_1023(reqData);
    }
    public void reqCHB1024(final CHB_1024.Request reqData) {
        repository.REQ_CHB_1024(reqData);
    }
    public void reqCHB1026(final CHB_1026.Request reqData) {
        repository.REQ_CHB_1026(reqData);
    }

    public VehicleVO getMainVehicleFromDB() throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<VehicleVO> future = es.getListeningExecutorService().submit(() -> {
            VehicleVO vehicleVO = null;
            try {
                vehicleVO = dbVehicleRepository.getMainVehicleFromDB();
            } catch (Exception ignore) {
                ignore.printStackTrace();
                vehicleVO = null;
            }
            return vehicleVO;
        });

        try {
            return future.get();
        } finally {
            es.shutDownExcutor();
        }
    }

}