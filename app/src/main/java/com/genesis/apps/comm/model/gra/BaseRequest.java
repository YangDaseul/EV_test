package com.genesis.apps.comm.model.gra;

import android.app.Application;
import android.text.TextUtils;

import com.genesis.apps.comm.MyApplication;
import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.DeviceDTO;
import com.genesis.apps.comm.model.vo.UserVO;
import com.genesis.apps.comm.net.ga.CCSP;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.room.DatabaseHolder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import dagger.hilt.EntryPoint;
import dagger.hilt.EntryPoints;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import lombok.Data;

/**
 * @author hjpark
 * @brief 기본 요청 항목
 * @see #ifCd 전문코드
 * @see #custNo 회원관리번호
 * 대표앱에서 생성한 회원 관리 번호
 * 값이 '0000' 경우는 미로그인 상태
 * @see #appGbCd 앱구분코드
 * 대표앱/ 파트너앱
 * GRA : 대표앱    GRP : 파드너앱   GRW: 서드파티웹
 * @see #mdn 휴대폰번호
 * 접속한 휴대폰 번호
 * 미로그인 상태인 경우 필수 아님
 * @see #pushId PUSHID
 * FCM PUSH 전송 용 토큰
 * 미로그인 상태인 경우 필수 아님
 * @see #deviceId 디바이스ID
 * 접속한 휴대폰 고유 아이디
 * @see #custGbCd 고객구분코드
 * 차량소유고객/계약한고객/차량이없는고객
 * 값이  '0000'  경우는 미로그인 상태
 * OV : 소유차량고객,  CV : 차량계약고객,  NV : 미소유차량고객
 */
public @Data
class BaseRequest extends BaseData {
    @Expose
    @SerializedName("ifCd")
    private String ifCd;
    @Expose
    @SerializedName("custNo")
    private String custNo;
    @Expose
    @SerializedName("appGbCd")
    private String appGbCd;
    @Expose
    @SerializedName("mdn")
    private String mdn;
    @Expose
    @SerializedName("pushId")
    private String pushId;
    @Expose
    @SerializedName("deviceId")
    private String deviceId;
    @Expose
    @SerializedName("custGbCd")
    private String custGbCd;
    @Expose
    @SerializedName("menuID")
    private String menuID;


    @EntryPoint
    @InstallIn(ApplicationComponent.class)
    interface CompInterface {
        DeviceDTO getDeviceDTO();
        DatabaseHolder getDB();
    }

    public void setData(String ifCd, String menuID){
        CompInterface compInterface = EntryPoints.get(MyApplication.getInstance(), CompInterface.class);
        UserVO userVO = compInterface.getDB().getDatabase().userDao().select();
        if(userVO==null|| TextUtils.isEmpty(userVO.getCustNo()) || TextUtils.isEmpty(userVO.getCustGbCd())){
            userVO = (userVO == null ? new UserVO() : userVO);
            userVO.setCustNm("0000");
            userVO.setCustGbCd("0000");
        }

        this.ifCd = ifCd;
        this.menuID = menuID;
        this.appGbCd = "GRA";

        this.custNo = userVO.getCustNo();
        this.custGbCd = userVO.getCustGbCd();

        this.mdn = compInterface.getDeviceDTO().getMdn();
        this.pushId = compInterface.getDeviceDTO().getPushId();
        this.deviceId = compInterface.getDeviceDTO().getDeviceId();
        //TODO menuID 추가 필요.. ?
    }
}
