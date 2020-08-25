package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.CarVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @file GRA_LGN_0002
 * @Brief 차량 보유 시 서비스 로그인 및 Main 화면 정보 요청, 정비현황 요청 (정비상태/정비이력/마이버틀러/긴급출동/알림 새로운 메시지  개수)
 * @author hjpark
 */
public class LGN_0002 extends BaseData {

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
     * @see #vin 차대번호
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
        @SerializedName("vin")
        private String vin;
    }

    /**
     * @brief LGN_0001 응답 항목
     * @author hjpark
     *
     * @see #rtCd 결과코드
     * @see #rtMsg 결과메세지
     *
     * @see #carVO 차량 정보
     *
     * @see #totalDrivDist 총 주행거리(KM)
     * @see #lastDrivDist 최근 주행거리(KM)
     * @see #canDrivDist 주행 가능거리(KM)
     *
     * @see #partCd 소모품 코드
     * HSW 시스템 정의 코드
     * @see #partNm 소모품 명
     * HSW 시스템 정의 코드
     * @see #nextExchgDrivDist 다음 교환시점 주행거리(KM)
     * @see #exchgDrivDist 교환시점 주행거리(KM)
     * @see #alertYn 경고 여부
     * 경고 여부, 교환 시점 경고 여부 ?
     * 경고 : Y, 정상 : N
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
        @SerializedName("carVO")
        private CarVO carVO;

        @Expose
        @SerializedName("totalDrivDist")
        private String totalDrivDist;
        @Expose
        @SerializedName("lastDrivDist")
        private String lastDrivDist;
        @Expose
        @SerializedName("canDrivDist")
        private String canDrivDist;

        @Expose
        @SerializedName("partCd")
        private String partCd;
        @Expose
        @SerializedName("partNm")
        private String partNm;
        @Expose
        @SerializedName("nextExchgDrivDist")
        private String nextExchgDrivDist;
        @Expose
        @SerializedName("exchgDrivDist")
        private String exchgDrivDist;
        @Expose
        @SerializedName("alertYn")
        private String alertYn;
    }
}
