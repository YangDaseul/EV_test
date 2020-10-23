package com.genesis.apps.comm.viewmodel;

import com.genesis.apps.comm.model.gra.api.GNS_1001;
import com.genesis.apps.comm.model.gra.api.GNS_1002;
import com.genesis.apps.comm.model.gra.api.GNS_1003;
import com.genesis.apps.comm.model.gra.api.GNS_1004;
import com.genesis.apps.comm.model.gra.api.GNS_1005;
import com.genesis.apps.comm.model.gra.api.GNS_1006;
import com.genesis.apps.comm.model.gra.api.GNS_1007;
import com.genesis.apps.comm.model.gra.api.GNS_1008;
import com.genesis.apps.comm.model.gra.api.GNS_1009;
import com.genesis.apps.comm.model.gra.api.GNS_1010;
import com.genesis.apps.comm.model.repo.DBUserRepo;
import com.genesis.apps.comm.model.repo.DBVehicleRepository;
import com.genesis.apps.comm.model.repo.GNSRepo;
import com.genesis.apps.comm.model.repo.GNSRepo;
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

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import lombok.Data;

import static com.genesis.apps.comm.model.constants.VariableType.MAIN_VEHICLE_TYPE_0000;
import static com.genesis.apps.comm.model.constants.VariableType.MAIN_VEHICLE_TYPE_CV;
import static com.genesis.apps.comm.model.constants.VariableType.MAIN_VEHICLE_TYPE_OV;

public @Data
class GNSViewModel extends ViewModel {

    private final GNSRepo repository;
    private final DBVehicleRepository dbVehicleRepository;
    private final DBUserRepo dbUserRepo;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<GNS_1001.Response>> RES_GNS_1001;
    private MutableLiveData<NetUIResponse<GNS_1002.Response>> RES_GNS_1002;
    private MutableLiveData<NetUIResponse<GNS_1003.Response>> RES_GNS_1003;
    private MutableLiveData<NetUIResponse<GNS_1004.Response>> RES_GNS_1004;
    private MutableLiveData<NetUIResponse<GNS_1005.Response>> RES_GNS_1005;

    private MutableLiveData<NetUIResponse<GNS_1006.Response>> RES_GNS_1006;
    private MutableLiveData<NetUIResponse<GNS_1007.Response>> RES_GNS_1007;

    private MutableLiveData<NetUIResponse<GNS_1008.Response>> RES_GNS_1008;
    private MutableLiveData<NetUIResponse<GNS_1009.Response>> RES_GNS_1009;

    private MutableLiveData<NetUIResponse<GNS_1010.Response>> RES_GNS_1010;

//    public final LiveData<List<VehicleVO>> carVO = Transformations.map(RES_GNS_1001, input -> input.data.getOwnVhclList());


//    public final LiveData<VehicleVO> carVO =
//            Transformations.switchMap(RES_GNS_1001, new Function<NetUIResponse<GNS_1001.Response>, LiveData<VehicleVO>>() {
//                @Override
//                public LiveData<VehicleVO> apply(NetUIResponse<GNS_1001.Response> input) {
//                    return input.data.getCarVO(); repo에서 getcarvo로 가저올수있는.. 다른걸 요청 가능
//                }
//            });

    @ViewModelInject
    GNSViewModel(
            GNSRepo repository,
            DBVehicleRepository dbVehicleRepository,
            DBUserRepo dbUserRepo,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.repository = repository;
        this.dbVehicleRepository = dbVehicleRepository;
        this.dbUserRepo = dbUserRepo;
        this.savedStateHandle = savedStateHandle;

        RES_GNS_1001 = repository.RES_GNS_1001;
        RES_GNS_1002 = repository.RES_GNS_1002;
        RES_GNS_1003 = repository.RES_GNS_1003;
        RES_GNS_1004 = repository.RES_GNS_1004;
        RES_GNS_1005 = repository.RES_GNS_1005;

        RES_GNS_1006 = repository.RES_GNS_1006;
        RES_GNS_1007 = repository.RES_GNS_1007;

        RES_GNS_1008 = repository.RES_GNS_1008;
        RES_GNS_1009 = repository.RES_GNS_1009;


        RES_GNS_1010 = repository.RES_GNS_1010;
    }

    public void reqGNS1001(final GNS_1001.Request reqData){
        repository.REQ_GNS_1001(reqData);
    }

    public void reqGNS1002(final GNS_1002.Request reqData){
        repository.REQ_GNS_1002(reqData);
    }

    public void reqGNS1003(final GNS_1003.Request reqData){
        repository.REQ_GNS_1003(reqData);
    }

    public void reqGNS1004(final GNS_1004.Request reqData){
        repository.REQ_GNS_1004(reqData);
    }

    public void reqGNS1005(final GNS_1005.Request reqData){
        repository.REQ_GNS_1005(reqData);
    }

    public void reqGNS1006(final GNS_1006.Request reqData){
        repository.REQ_GNS_1006(reqData);
    }

    public void reqGNS1007(final GNS_1007.Request reqData){
        repository.REQ_GNS_1007(reqData);
    }


    public void reqGNS1008(final GNS_1008.Request reqData){
        repository.REQ_GNS_1008(reqData);
    }
    public void reqGNS1009(final GNS_1009.Request reqData){
        repository.REQ_GNS_1009(reqData);
    }

    public void reqGNS1010(final GNS_1010.Request reqData){
        repository.REQ_GNS_1010(reqData);
    }

    public void setGNS1001ToDB(GNS_1001.Response data, ResultCallback callback){
        ExecutorService es = new ExecutorService("");
        Futures.addCallback(es.getListeningExecutorService().submit(() -> {

            boolean isSuccess=false;
            try {
                isSuccess = dbVehicleRepository.setVehicleList(data.getOwnVhclList(), MAIN_VEHICLE_TYPE_OV)
                        &&dbVehicleRepository.setVehicleList(data.getCtrctVhclList(), MAIN_VEHICLE_TYPE_CV);
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

    public List<VehicleVO> getVehicleList() throws ExecutionException, InterruptedException{
        ExecutorService es = new ExecutorService("");
        Future<List<VehicleVO>> future = es.getListeningExecutorService().submit(()->{
            List<VehicleVO> list = new ArrayList<>();
            try {
                list = dbVehicleRepository.getVehicleList();

                if(list.size()>0){
                    //삭제를 하면 서버에서.. 주이용차량제외 및 할당을 다 처리 해준다는 가정하에 로직 X
//                    VehicleVO vehicleFirst = list.get(0);
//
//                    if(vehicleFirst.get)



                }


            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            return list;
        });

        try {
            return future.get();
        }finally {
            //todo 테스트 필요
            es.shutDownExcutor();
        }
    }

    public VehicleVO getVehicle(String vin) throws ExecutionException, InterruptedException{
        ExecutorService es = new ExecutorService("");
        Future<VehicleVO> future = es.getListeningExecutorService().submit(()->{
           VehicleVO vehicleVO = null;
            try {
                vehicleVO = dbVehicleRepository.getVehicle(vin);
            } catch (Exception ignore) {
                ignore.printStackTrace();
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
}