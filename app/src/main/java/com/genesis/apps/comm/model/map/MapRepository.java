package com.genesis.apps.comm.model.map;

import android.text.TextUtils;

import com.genesis.apps.R;
import com.genesis.apps.comm.net.HttpRequest;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetStatusCode;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hmns.playmap.network.PlayMapRestApi;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class MapRepository {

    ExecutorService es;
    PlayMapRestApi playMapRestApi;

    @Inject
    public MapRepository(ExecutorService es, PlayMapRestApi playMapRestApi){
        this.es = es;
        this.playMapRestApi = playMapRestApi;
    }

    public MutableLiveData<NetUIResponse<JsonObject>> findPathDataJson(final FindPathReqVO findPathReqVO){
        final MutableLiveData<NetUIResponse<JsonObject>> data=new MutableLiveData<>();
        Futures.addCallback(es.getListeningExecutorService().submit(() -> {
            JsonObject jsonObject = null;
            try {
                    jsonObject = playMapRestApi.findPathDataJson(findPathReqVO.getRouteOption(), findPathReqVO.getFeeOption(), findPathReqVO.getRoadOption(), findPathReqVO.getCoordType(), findPathReqVO.getCarType(), findPathReqVO.getStartPoint(), findPathReqVO.getViaPoint(), findPathReqVO.getGoalPoint());
                if (jsonObject != null && !TextUtils.isEmpty(jsonObject.toString())) {
                    return new NetResult(NetStatusCode.SUCCESS, 0, jsonObject);
                } else {
                    return new NetResult(NetStatusCode.ERR_DATA_NULL, R.string.error_msg_1, null);
                }
            } catch (HttpRequest.HttpRequestException e) {
                e.printStackTrace();
                return new NetResult(NetStatusCode.ERR_EXCEPTION_HTTP, R.string.error_msg_2, null);
            } catch (Exception e1) {
                e1.printStackTrace();
                return new NetResult(NetStatusCode.ERR_EXCEPTION_UNKNOWN, R.string.error_msg_3, null);
            }

        }), new FutureCallback<NetResult>() {
            @Override
            public void onSuccess(@NullableDecl NetResult result) {
                switch (result.getCode()) {
                    case SUCCESS:
//                        beanReqParm.getCallback().onSuccess(((JsonObject) result.getData()).toString());
                        데이터구체화필요
                        S receiveData =  new Gson().fromJson(result.getData().toString(),  class);
                        data.setValue(NetUIResponse.success(receiveData));
                        break;
                    case ERR_EXCEPTION_DKC:
                    case ERR_EXCEPTION_HTTP:
                    case ERR_EXCEPTION_UNKNOWN:
                    case ERR_DATA_NULL:
                    case ERR_ISSUE_SOURCE:
                    case ERR_DATA_INCORRECT:
                    default:
                        data.setValue(NetUIResponse.error(result.getMseeage(),null));
                        break;
                }
                es.shutDownExcutor();
            }

            @Override
            public void onFailure(Throwable t) {
//                beanReqParm.getCallback().onError(new NetResult(NetStatusCode.ERR_ISSUE_SOURCE, R.string.error_msg_4, t));
                data.setValue(NetUIResponse.error(R.string.error_msg_4,null));
                es.shutDownExcutor();
            }
        }, es.getUiThreadExecutor());
        return data;
    }


}
