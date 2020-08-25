package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.CarVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @file GRA_LGN_0001
 * @Brief 대표앱 로그인시 PUSH ID 가 이미 등록되어 있고, 사용자가 변경 요청을 할 경우에 사용됨.
 * @author hjpark
 */
public class LGN_0001 extends BaseData {

    /**
     * @brief LGN_0001 요청 항목
     * @author hjpark
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
     * @see #appVer 앱버전
     * 설치된 앱 버전
     * @see #etrmOsDivCd 단말OS구분코드
     * A: 안드로이드  I:아이폰 : E:기타
     * @see #etrmOsVer 단말OS버전
     * @see #etrmMdlNm 단말모델명
     * @see #vin 차대번호
     *
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
        @SerializedName("appVer")
        private String appVer;
        @Expose
        @SerializedName("etrmOsDivCd")
        private String etrmOsDivCd;
        @Expose
        @SerializedName("etrmOsVer")
        private String etrmOsVer;
        @Expose
        @SerializedName("etrmMdlNm")
        private String etrmMdlNm;
        @Expose
        @SerializedName("vin")
        private String vin;
    }

    /**
     * @brief LGN_0001 응답 항목
     * @author hjpark
     * @see #rtCd 결과코드
     * @see #rtMsg 결과메세지
     *
     * @see #custNo 회원관리번호
     * 대표앱에서 생성한 회원 관리 번호
     * 값이 '0000' 경우는 미로그인 상태
     * @see #custGbCd 고객구분코드
     * 차량소유고객/계약한고객/차량이없는고객
     * 미로그인: 0000, OV : 소유차량고객,  CV : 차량계약고객,  NV : 미소유차량고객
     * @see #pushIdChgYn PushID 변경확인
     * Y:  기등록된 PushID가 있는 경우 변경여부를 확인 요청
     * N:  기등록된 PushID를 사용함 (등록된 Push ID와 동일)
     *
     * @see #carVO 차량정보
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
        @SerializedName("custNo")
        private String custNo;
        @Expose
        @SerializedName("custGbCd")
        private String custGbCd;
        @Expose
        @SerializedName("pushIdChgYn")
        private String pushIdChgYn;
        @Expose
        @SerializedName("carVO")
        private CarVO carVO;
    }
}
