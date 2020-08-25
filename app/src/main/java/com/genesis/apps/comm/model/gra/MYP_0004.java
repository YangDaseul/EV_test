package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_MYP_0004
 * @Brief 사용자의 마케팅 수신동의 정보를 변경한다.
 */
public class MYP_0004 extends BaseData {
    /**
     * @author hjpark
     * @brief MYP_0004 요청 항목
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
     * @see #mrktYn 동의여부
     * Y:  동의  N:미동의
     * @see #mrktCd 동의채널코드
     * 동의 한 경우 :
     *  - 1 -> 동의채널  0-> 미동의채널
     *  - SMS(1) + 이메일(1) + 우편(1) + 전화(1)
     *    ex) 1010 : SMS와 우편은 동의했지만 그 외는 미동의
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
        @Expose
        @SerializedName("mrktYn")
        private String mrktYn;
        @Expose
        @SerializedName("mrktCd")
        private String mrktCd;
    }

    /**
     * @author hjpark
     * @brief MYP_0004 응답 항목
     * @see #rtCd 결과코드
     * @see #rtMsg 결과메세지
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
    }
}
