package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.CarVO;
import com.genesis.apps.comm.model.vo.MainHistVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_LGN_0003
 * @Brief 차량 보유 시 섭비스 로그인 및 Main 화면 정보 요청, 주행거리 요청 (차량 정보/주행 정보/소모품 정보)
 */
public class LGN_0003 extends BaseData {
    /**
     * @author hjpark
     * @brief LGN_0003 요청 항목
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
     * @author hjpark
     * @brief LGN_0003 응답 항목
     * @see #rtCd 결과코드
     * @see #rtMsg 결과메세지
     * @see #vrn 차량번호
     * @see #mainDt 정비일자
     * (형식 YYYY-MM-DD)
     * @see #mainNetNm 정비망명
     * @see #mainWorkHist 점검내역
     * @see #mainStatsCd 정비상태코드
     * HSW 시스템 정의
     * @see #mainHistList 정비이력정보
     * 리스트 정보
     * @see #butlNm 버틀러 성명
     * @see #butlSpotNm 버틀러 지점명
     * @see #butlSubsDt 버틀러 신칭일
     * (형식 YYYY-MM-DD)
     * @see #virtRecptNo 가접수번호
     * 긴급출동 신청 가점수번호
     * @see #newNotiCnt 새 알림목록 개수
     * 읽지 않은 새 알림목로 개수
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
        @SerializedName("vrn")
        private String vrn;
        @Expose
        @SerializedName("mainDt")
        private String mainDt;
        @Expose
        @SerializedName("mainNetNm")
        private String mainNetNm;
        @Expose
        @SerializedName("mainWorkHist")
        private String mainWorkHist;
        @Expose
        @SerializedName("mainStatsCd")
        private String mainStatsCd;

        @Expose
        @SerializedName("mainHistList")
        private List<MainHistVO> mainHistList;

        @Expose
        @SerializedName("butlNm")
        private String butlNm;
        @Expose
        @SerializedName("butlSpotNm")
        private String butlSpotNm;
        @Expose
        @SerializedName("butlSubsDt")
        private String butlSubsDt;

        @Expose
        @SerializedName("virtRecptNo")
        private String virtRecptNo;

        @Expose
        @SerializedName("newNotiCnt")
        private String newNotiCnt;
    }
}
