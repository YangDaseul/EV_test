package com.genesis.apps.comm.model.repo;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.TestCode;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.api.NOT_0001;
import com.genesis.apps.comm.model.gra.api.NOT_0002;
import com.genesis.apps.comm.model.gra.api.NOT_0003;
import com.genesis.apps.comm.model.vo.NotiInfoVO;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.room.DatabaseHolder;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class NOTRepo {
    private DatabaseHolder databaseHolder;
    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<NOT_0001.Response>> RES_NOT_0001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<NOT_0002.Response>> RES_NOT_0002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<NOT_0003.Response>> RES_NOT_0003 = new MutableLiveData<>();
    
    @Inject
    public NOTRepo(NetCaller netCaller, DatabaseHolder databaseHolder) {
        this.netCaller = netCaller;
        this.databaseHolder = databaseHolder;
    }

    public MutableLiveData<NetUIResponse<NOT_0001.Response>> REQ_NOT_0001(final NOT_0001.Request reqData) {
        RES_NOT_0001.setValue(NetUIResponse.loading(null));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_NOT_0001.setValue(NetUIResponse.success(new Gson().fromJson(object, NOT_0001.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
//                RES_NOT_0001.setValue(NetUIResponse.error(e.getMseeage(), null));
                RES_NOT_0001.setValue(NetUIResponse.success(TestCode.NOT_0001));
            }

            @Override
            public void onError(NetResult e) {
                RES_NOT_0001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_NOT_0001, reqData);

        return RES_NOT_0001;
    }


    public MutableLiveData<NetUIResponse<NOT_0002.Response>> REQ_NOT_0002(final NOT_0002.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_NOT_0002.setValue(NetUIResponse.success(new Gson().fromJson(object, NOT_0002.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_NOT_0002.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_NOT_0002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_NOT_0002, reqData);

        return RES_NOT_0002;
    }


    public MutableLiveData<NetUIResponse<NOT_0003.Response>> REQ_NOT_0003(final NOT_0003.Request reqData) {

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_NOT_0003.setValue(NetUIResponse.success(new Gson().fromJson(object, NOT_0003.Response.class)));
                //TODO SINGLETON VO에 값 저장?
            }

            @Override
            public void onFail(NetResult e) {
                RES_NOT_0003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_NOT_0003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_NOT_0003, reqData);

        return RES_NOT_0003;
    }


    public boolean updateNotiInfoToDB(List<NotiInfoVO> list){
        boolean isUpdate=false;
        try{
            databaseHolder.getDatabase().notiInfoDao().insertAndDeleteInTransaction(list);
            isUpdate=true;
        }catch (Exception e){
            isUpdate=false;
        }

        return isUpdate;
    }

    public List<NotiInfoVO> getNotiInfoListAll(){
        return databaseHolder.getDatabase().notiInfoDao().selectAll();
    }

    public List<NotiInfoVO> getNotiInfoList(String cateCd){
        return databaseHolder.getDatabase().notiInfoDao().select(cateCd);
    }

    public List<NotiInfoVO> searchNotiInfoList(String search){
        return databaseHolder.getDatabase().notiInfoDao().selectSearch(search);
    }

}
