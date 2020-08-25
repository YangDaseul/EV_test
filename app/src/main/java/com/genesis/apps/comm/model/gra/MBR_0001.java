package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.CarVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @file GRA_MBR_0001
 * @Brief 서비스 가입 요청
 * @author hjpark
 */
public class MBR_0001 extends BaseData {
    /**
     * @brief MBR_0001의 요청 항목
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
     * @see #tokenCode 토큰코드
     * GA 회원 가입 요청 api(GA-COM-030) 호출 할때 사용하는 값.
     * @see #authUuid 본인인증UUID
     * GA 회원 가입 요청 api(GA-COM-030) 호출 할때 사용하는 값.
     * @see #terms 약관정보
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
        @SerializedName("tokenCode")
        private String tokenCode;
        @Expose
        @SerializedName("authUuid")
        private String authUuid;
        @Expose
        @SerializedName("terms")
        private List<Term> terms;

        /**
         * @brief 약관정보
         * @author hjpark
         * @see #termCode 약관구분코드
         * 앱 이용약관 : 1000
         * 개인정보처리방침 : 2000
         * 개인정보 수집/이용 : 3000
         * 광고성 정보 수신동의 : 4000
         * 제네시스 멤버십 가입 약관  : 5000
         * @see #agreeYn 동의
         * Y : 동의, 그외 : N
         * @see #termName 동의약관명
         * @see #agreeDate 동의일자
         * YYYYMMDDHH24MMSS
         * @see #agreeMeans 동의수단
         * @see #agreeSms 약관구분코드
         * Y : 동의, 그외 : N
         * @see #agreeEmail 약관구분코드
         * Y : 동의, 그외 : N
         * @see #agreePost 약관구분코드
         * Y : 동의, 그외 : N
         * @see #agreeTel 약관구분코드
         * Y : 동의, 그외 : N
         */

        //TODO TermVO 와 통합될듯 ?
        @EqualsAndHashCode(callSuper = false)
        public @Data
        class Term {
            @Expose
            @SerializedName("termCode")
            private String termCode;
            @Expose
            @SerializedName("agreeYn")
            private String agreeYn;
            @Expose
            @SerializedName("termName")
            private String termName;
            @Expose
            @SerializedName("agreeDate")
            private String agreeDate;
            @Expose
            @SerializedName("agreeMeans")
            private String agreeMeans;
            @Expose
            @SerializedName("agreeSms")
            private String agreeSms;
            @Expose
            @SerializedName("agreeEmail")
            private String agreeEmail;
            @Expose
            @SerializedName("agreePost")
            private String agreePost;
            @Expose
            @SerializedName("agreeTel")
            private String agreeTel;
        }

    }

    /**
     * @brief MBR_0001의 응답 항목
     * @author hjpark
     * @see #rtCd 결과코드
     * @see #rtMsg 결과메세지
     * @see #custNo 결과메세지
     * 대표앱에서 생성한 회원 관리 번호(가입 정상 처리 시 필수)
     * @see #custGbCd 결과메세지
     * 차량소유고객/계약한고객/차량이없는고객
     * 미로그인: 0000, OV : 소유차량고객,  CV : 차량계약고객,  NV : 미소유차량고객
     * @see #carVO 차량 정보
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
        @SerializedName("carVO")
        private CarVO carVO;
    }

}
