package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.TermVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_MYP_8004
 * @Brief MyG+ 버전정보
 */
public class MYP_8004 extends BaseData {
    /**
     * @brief MYP_8004 요청 항목
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
    @EqualsAndHashCode(callSuper = false)
    @AllArgsConstructor
    public @Data
    class Request {
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
    }

    /**
     * @brief MYP_8004 응답 항목
     * @see #rtCd 결과코드
     * @see #rtMsg 결과메세지
     * @see #ver 버전정보
     */
    @EqualsAndHashCode(callSuper = false)
    public @Data
    class Response {
        @Expose
        @SerializedName("rtCd")
        private String rtCd;
        @Expose
        @SerializedName("rtMsg")
        private String rtMsg;
        @Expose
        @SerializedName("ver")
        private String ver;
    }
}
