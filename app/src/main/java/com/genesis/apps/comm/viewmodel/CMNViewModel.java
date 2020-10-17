package com.genesis.apps.comm.viewmodel;

import android.text.TextUtils;

import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.constants.WeatherCodes;
import com.genesis.apps.comm.model.gra.api.BAR_1001;
import com.genesis.apps.comm.model.gra.api.CMN_0001;
import com.genesis.apps.comm.model.gra.api.CMN_0002;
import com.genesis.apps.comm.model.gra.api.CMN_0003;
import com.genesis.apps.comm.model.gra.api.CMN_0004;
import com.genesis.apps.comm.model.gra.api.LGN_0005;
import com.genesis.apps.comm.model.gra.api.NOT_0001;
import com.genesis.apps.comm.model.gra.api.NOT_0002;
import com.genesis.apps.comm.model.repo.BARRepo;
import com.genesis.apps.comm.model.repo.CMNRepo;
import com.genesis.apps.comm.model.repo.CardRepository;
import com.genesis.apps.comm.model.repo.DBContentsRepository;
import com.genesis.apps.comm.model.repo.DBGlobalDataRepository;
import com.genesis.apps.comm.model.repo.NOTRepo;
import com.genesis.apps.comm.model.vo.CardVO;
import com.genesis.apps.comm.model.vo.FloatingMenuVO;
import com.genesis.apps.comm.model.vo.MessageVO;
import com.genesis.apps.comm.model.vo.NotiInfoVO;
import com.genesis.apps.comm.model.vo.NotiVO;
import com.genesis.apps.comm.model.vo.QuickMenuVO;
import com.genesis.apps.comm.model.vo.WeatherVO;
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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

import static com.genesis.apps.comm.model.constants.VariableType.MAIN_VEHICLE_TYPE_0000;
import static com.genesis.apps.comm.model.constants.VariableType.MAIN_VEHICLE_TYPE_CV;
import static com.genesis.apps.comm.model.constants.VariableType.MAIN_VEHICLE_TYPE_NV;
import static com.genesis.apps.comm.model.constants.VariableType.MAIN_VEHICLE_TYPE_OV;

public @Data
class CMNViewModel extends ViewModel {

    private final NOTRepo notRepo;
    private final CMNRepo repository;
    private final CardRepository cardRepository;
    private final BARRepo barRepo;
    private final DBGlobalDataRepository dbGlobalDataRepository;
    private final DBContentsRepository dbContentsRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<CMN_0001.Response>> RES_CMN_0001;
    private MutableLiveData<NetUIResponse<CMN_0002.Response>> RES_CMN_0002;
    private MutableLiveData<NetUIResponse<CMN_0003.Response>> RES_CMN_0003;
    private MutableLiveData<NetUIResponse<CMN_0004.Response>> RES_CMN_0004;

    private MutableLiveData<NetUIResponse<BAR_1001.Response>> RES_BAR_1001;

    private MutableLiveData<NetUIResponse<NOT_0001.Response>> RES_NOT_0001;
    private MutableLiveData<NetUIResponse<NOT_0002.Response>> RES_NOT_0002;

    @ViewModelInject
    CMNViewModel(
            NOTRepo notRepo,
            CMNRepo repository,
            CardRepository cardRepository,
            BARRepo barRepo,
            DBGlobalDataRepository dbGlobalDataRepository,
            DBContentsRepository dbContentsRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.notRepo = notRepo;
        this.repository = repository;
        this.cardRepository = cardRepository;
        this.barRepo = barRepo;
        this.dbGlobalDataRepository = dbGlobalDataRepository;
        this.dbContentsRepository = dbContentsRepository;
        this.savedStateHandle = savedStateHandle;

        RES_CMN_0001 = repository.RES_CMN_0001;
        RES_CMN_0002 = repository.RES_CMN_0002;
        RES_CMN_0003 = repository.RES_CMN_0003;
        RES_CMN_0004 = repository.RES_CMN_0004;

        RES_BAR_1001 = barRepo.RES_BAR_1001;

        RES_NOT_0001 = notRepo.RES_NOT_0001;
        RES_NOT_0002 = notRepo.RES_NOT_0002;
    }

    public void reqCMN0001(final CMN_0001.Request reqData) {
        repository.REQ_CMN_0001(reqData);
    }

    public void reqCMN0002(final CMN_0002.Request reqData) {
        repository.REQ_CMN_0002(reqData);
    }

    public void reqCMN0003(final CMN_0003.Request reqData) {
        repository.REQ_CMN_0003(reqData);
    }

    public void reqCMN0004(final CMN_0004.Request reqData) {
        repository.REQ_CMN_0004(reqData);
    }

    public void reqBAR1001(final BAR_1001.Request reqData) {
        barRepo.REQ_BAR_1001(reqData);
    }


    public void reqNOT0001(final NOT_0001.Request reqData) {
        notRepo.REQ_NOT_0001(reqData);
    }

    public void reqNOT0002(final NOT_0002.Request reqData) {
        notRepo.REQ_NOT_0002(reqData);
    }

    public void updateNotiDt(String notiDt) {
        dbGlobalDataRepository.update(KeyNames.KEY_NAME_DB_GLOBAL_DATA_NOTIDT, notiDt);
    }

    public boolean setWeatherList(List<WeatherVO> list) {
        return dbContentsRepository.setWeatherList(list);
    }

    public boolean setQuickMenu(List<QuickMenuVO> list, String type) {
        return dbContentsRepository.setQuickMenu(list, type);
    }

    public boolean setFloatingMenu(List<FloatingMenuVO> list, String type) {
        return dbContentsRepository.setFloatingMenu(list, type);
    }

    public List<FloatingMenuVO> getFloatingMenuList(String custGbCd){
        return dbContentsRepository.getFloatingMenu(custGbCd);
    }

    public void setContents(CMN_0002.Response data, ResultCallback callback) {
        ExecutorService es = new ExecutorService("");
        Futures.addCallback(es.getListeningExecutorService().submit(() -> {

            boolean isSuccess = false;

            try {
                isSuccess = setWeatherList(data.getWthrInsgtList())
                        && setQuickMenu(data.getMenu0000().getQckMenuList(), MAIN_VEHICLE_TYPE_0000)
                        && setFloatingMenu(data.getMenu0000().getMenuList(), MAIN_VEHICLE_TYPE_0000)
                        && setQuickMenu(data.getMenuCV().getQckMenuList(), MAIN_VEHICLE_TYPE_CV)
                        && setFloatingMenu(data.getMenuCV().getMenuList(), MAIN_VEHICLE_TYPE_CV)
                        && setQuickMenu(data.getMenuNV().getQckMenuList(), MAIN_VEHICLE_TYPE_NV)
                        && setFloatingMenu(data.getMenuNV().getMenuList(), MAIN_VEHICLE_TYPE_NV)
                        && setQuickMenu(data.getMenuOV().getQckMenuList(), MAIN_VEHICLE_TYPE_OV)
                        && setFloatingMenu(data.getMenuOV().getMenuList(), MAIN_VEHICLE_TYPE_OV);
            } catch (Exception e1) {
                e1.printStackTrace();
                isSuccess = false;
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


    public NotiVO getNoti(List<NotiVO> list) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");

        Future<NotiVO> future = es.getListeningExecutorService().submit(()->{
            NotiVO notiVO = null;

            try {
                //NOTI 우선순위 결정
                for (int i = 0; i < list.size(); i++) {
                    switch (list.get(i).getNotiCd()) {
                        case VariableType.NOTI_CODE_EMGR:
                            notiVO = list.get(i);
                            break;
                        case VariableType.NOTI_CODE_ANNC:
                            if (notiVO == null
                                    || !notiVO.getNotiCd().equalsIgnoreCase(VariableType.NOTI_CODE_EMGR)) {
                                notiVO = list.get(i);
                            }
                            break;
                        default:
                            break;
                    }
                }
            } catch (Exception ignore) {
                ignore.printStackTrace();
                notiVO = null;
            }

            return notiVO;
        });
        try {
            return future.get();
        }finally {
            es.shutDownExcutor();
        }
    }

    public MessageVO getHomeWeatherInsight(WeatherCodes weatherCodes) throws ExecutionException, InterruptedException {

        ExecutorService es = new ExecutorService("");
        Future<MessageVO> future = es.getListeningExecutorService().submit(()->{
            MessageVO weather = null;
            try {
                WeatherVO weatherVO = dbContentsRepository.getWeatherRandom(weatherCodes.getDbCode());
                weather = new Gson().fromJson((new Gson().toJson(weatherVO)), MessageVO.class);
                weather.setWeatherCodes(weatherCodes);
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            return weather;
        });
        try {
            return future.get();
        }finally {
            es.shutDownExcutor();
        }
    }


    public MessageVO getTmpInsight(){
        MessageVO insight = new MessageVO(VariableType.MAIN_HOME_INSIGHT_IML,"","","","","","https://www.genesis.com/kr/ko/support/genesis-events/detail.html?seq=0507","",null);
        return insight;
    }




    public List<CardVO> getCardVO(List<CardVO> list) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<List<CardVO>> future = es.getListeningExecutorService().submit(()->{
            List<CardVO> cardVOList = new ArrayList<>();
            try {
                cardVOList = cardRepository.insertCardVO(list);
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            return cardVOList;
        });
        try {
            return future.get();
        }finally {
            es.shutDownExcutor();
        }
    }


    public boolean changeCardOrder(List<CardVO> list) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<Boolean> future = es.getListeningExecutorService().submit(()->{
            boolean isChange=false;
            try {
                isChange = cardRepository.changeCardOrder(list);
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            return isChange;
        });
        try {
            return future.get();
        }finally {
            es.shutDownExcutor();
        }
    }




    public boolean updateNotiList(List<NotiInfoVO> list) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<Boolean> future = es.getListeningExecutorService().submit(()->{
            boolean isUpdate=false;
            try {
                isUpdate = notRepo.updateNotiInfoToDB(list);
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            return isUpdate;
        });
        try {
            return future.get();
        }finally {
            es.shutDownExcutor();
        }
    }

    public List<NotiInfoVO> getNotiInfoFromDB(String cateCd, String search) throws ExecutionException, InterruptedException {

        ExecutorService es = new ExecutorService("");
        Future<List<NotiInfoVO>> future = es.getListeningExecutorService().submit(()->{
            List<NotiInfoVO> list = new ArrayList<>();
            try {
                if(!TextUtils.isEmpty(cateCd))
                    list = notRepo.getNotiInfoList(cateCd);
                else if(!TextUtils.isEmpty(search))
                    list = notRepo.searchNotiInfoList(search);
                else
                    list = notRepo.getNotiInfoListAll();

                if(list==null) list = new ArrayList<>();
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            return list;
        });
        try {
            return future.get();
        }finally {
            es.shutDownExcutor();
        }
    }


}