package com.genesis.apps.comm.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.gra.api.LGN_0001;
import com.genesis.apps.comm.model.gra.api.LGN_0002;
import com.genesis.apps.comm.model.gra.api.LGN_0003;
import com.genesis.apps.comm.model.gra.api.LGN_0004;
import com.genesis.apps.comm.model.gra.api.LGN_0005;
import com.genesis.apps.comm.model.gra.api.LGN_0006;
import com.genesis.apps.comm.model.gra.api.LGN_0007;
import com.genesis.apps.comm.model.gra.api.STO_1001;
import com.genesis.apps.comm.model.gra.api.STO_1002;
import com.genesis.apps.comm.model.repo.DBUserRepo;
import com.genesis.apps.comm.model.repo.DBVehicleRepository;
import com.genesis.apps.comm.model.repo.LGNRepo;
import com.genesis.apps.comm.model.repo.STORepo;
import com.genesis.apps.comm.model.vo.UserVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.genesis.apps.room.ResultCallback;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.gson.Gson;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import lombok.Data;

import static com.genesis.apps.comm.model.constants.VariableType.MAIN_VEHICLE_TYPE_0000;
import static com.genesis.apps.comm.model.constants.VariableType.MAIN_VEHICLE_TYPE_CV;
import static com.genesis.apps.comm.model.constants.VariableType.MAIN_VEHICLE_TYPE_OV;

public @Data
class LGNViewModel extends ViewModel {


    private final LGNRepo repository;
    private final STORepo stoRepo;
    private final DBVehicleRepository dbVehicleRepository;
    private final DBUserRepo dbUserRepo;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<LGN_0001.Response>> RES_LGN_0001;
    private MutableLiveData<NetUIResponse<LGN_0002.Response>> RES_LGN_0002;
    private MutableLiveData<NetUIResponse<LGN_0003.Response>> RES_LGN_0003;
    private MutableLiveData<NetUIResponse<LGN_0004.Response>> RES_LGN_0004;
    private MutableLiveData<NetUIResponse<LGN_0005.Response>> RES_LGN_0005;

    private MutableLiveData<NetUIResponse<LGN_0006.Response>> RES_LGN_0006;
    private MutableLiveData<NetUIResponse<LGN_0007.Response>> RES_LGN_0007;

    public final LiveData<List<VehicleVO>> carVO = Transformations.map(RES_LGN_0001, input -> input.data.getOwnVhclList());


    private MutableLiveData<NetUIResponse<STO_1001.Response>> RES_STO_1001;
    private MutableLiveData<NetUIResponse<STO_1002.Response>> RES_STO_1002;

    private Double[] position = new Double[2];
    
    
//    public final LiveData<VehicleVO> carVO =
//            Transformations.switchMap(RES_LGN_0001, new Function<NetUIResponse<LGN_0001.Response>, LiveData<VehicleVO>>() {
//                @Override
//                public LiveData<VehicleVO> apply(NetUIResponse<LGN_0001.Response> input) {
//                    return input.data.getCarVO(); repo에서 getcarvo로 가저올수있는.. 다른걸 요청 가능
//                }
//            });

    @ViewModelInject
    LGNViewModel(
            LGNRepo repository,
            STORepo stoRepo,
            DBVehicleRepository dbVehicleRepository,
            DBUserRepo dbUserRepo,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.repository = repository;
        this.stoRepo = stoRepo;
        this.dbVehicleRepository = dbVehicleRepository;
        this.dbUserRepo = dbUserRepo;
        this.savedStateHandle = savedStateHandle;

        RES_LGN_0001 = repository.RES_LGN_0001;
        RES_LGN_0002 = repository.RES_LGN_0002;
        RES_LGN_0003 = repository.RES_LGN_0003;
        RES_LGN_0004 = repository.RES_LGN_0004;
        RES_LGN_0005 = repository.RES_LGN_0005;

        RES_LGN_0006 = repository.RES_LGN_0006;
        RES_LGN_0007 = repository.RES_LGN_0007;
        
        RES_STO_1001 = stoRepo.RES_STO_1001;
        RES_STO_1002 = stoRepo.RES_STO_1002;
    }

    public void reqLGN0001(final LGN_0001.Request reqData){
        repository.REQ_LGN_0001(reqData);
    }

    public void reqLGN0002(final LGN_0002.Request reqData){
        repository.REQ_LGN_0002(reqData);
    }

    public void reqLGN0003(final LGN_0003.Request reqData){
        repository.REQ_LGN_0003(reqData);
    }

    public void reqLGN0004(final LGN_0004.Request reqData){
        repository.REQ_LGN_0004(reqData);
    }

    public void reqLGN0005(final LGN_0005.Request reqData){
        repository.REQ_LGN_0005(reqData);
    }

    public void reqLGN0006(final LGN_0006.Request reqData){
        repository.REQ_LGN_0006(reqData);
    }

    public void reqLGN0007(final LGN_0007.Request reqData){
        repository.REQ_LGN_0007(reqData);
    }

    public void reqSTO1001(final STO_1001.Request reqData){
        stoRepo.REQ_STO_1001(reqData);
    }

    public void reqSTO1002(final STO_1002.Request reqData){
        stoRepo.REQ_STO_1002(reqData);
    }


    public void setLGN0001ToDB(LGN_0001.Response data, ResultCallback callback){
        ExecutorService es = new ExecutorService("");
        Futures.addCallback(es.getListeningExecutorService().submit(() -> {

            boolean isSuccess=false;
            try {
                isSuccess = dbVehicleRepository.setVehicleList(data.getOwnVhclList(), MAIN_VEHICLE_TYPE_OV)
                        &&dbVehicleRepository.setVehicleList(data.getCtrctVhclList(), MAIN_VEHICLE_TYPE_CV)
                        &&dbVehicleRepository.setVehicleList(data.getDftVhclInfo(), MAIN_VEHICLE_TYPE_0000)
                        &&dbUserRepo.setUserVO(new Gson().fromJson(new Gson().toJson(data),UserVO.class));
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

    public VehicleVO getMainVehicleFromDB() throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<VehicleVO> future = es.getListeningExecutorService().submit(()->{
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
        }finally {
            //todo 테스트 필요
            es.shutDownExcutor();
        }
    }

    public UserVO getUserInfoFromDB() throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<UserVO> future = es.getListeningExecutorService().submit(()->{
            UserVO userVO = null;
            try {
                userVO = dbUserRepo.getUserVO();
            } catch (Exception ignore) {
                ignore.printStackTrace();
                userVO = null;
            }
            return userVO;
        });

        try {
            return future.get();
        }finally {
            //todo 테스트 필요
            es.shutDownExcutor();
        }
    }

    public void setPosition(double x, double y){
        position[0] = x;
        position[1] = y;
    }

    public Double[] getPosition(){
        return position;
    }


}