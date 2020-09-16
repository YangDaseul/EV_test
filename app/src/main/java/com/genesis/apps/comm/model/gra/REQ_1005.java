package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.CouponVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_REQ_1005
 * @Brief service + 정비예약 - 정비1단계
 */
public class REQ_1005 extends BaseData {
    /**
     * @brief REQ_1005 요청 항목
     * @see #vin 차대번호
     * @see #vrn 차량번호
     * 차량번호가 없는 경우 정비예약 중복확인 불가할 수 있음
     * @see #rparTypCd 정비내용코드
     * 부품계통코드 --> 코드값 문의 필요
     * null 값이 아니면 부품계통 정비가 가능한 정비소 찾기
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("vrn")
        private String vrn;
        @Expose
        @SerializedName("rparTypCd")
        private String rparTypCd;

        public Request(String menuId, String vin, String vrn, String rparTypCd){
            this.vin = vin;
            this.vrn = vrn;
            this.rparTypCd = rparTypCd;
            setData(APIInfo.GRA_REQ_1005.getIfCd(), menuId);
        }
    }

    /**
     * @brief REQ_1005 응답 항목
     *
     * @see #carCareCpnList 카케어쿠폰리스트
     *
     *
     * @see #autoRsvtStusCd 오토케어 예약상태 코드
     * @see #arptRsvtStusCd 에어포트예약상태코드
     * @see #hthRsvtStusCd 홈투홈예약상태코드
     * @see #rpshRsvtStusCd 정비소예약상태코드
     * 예약상태코드	홈투홈	에어포트	오토케어	정비소
     * 예약신청	    1100	2100	3100	4100
     * 예약완료	    1200	2200	3200	4200
     * 픽업대기	    1300	2300	3300
     * 픽업중	    1400	2400	3400
     * 정비소도착	1500	2500	3500
     * 정비대기중	4600	4600	4600	4600
     * 정비진행중	4700	4700	4700	4700
     * 정비완료	    4800	4800	4800	4800
     * 딜리버리대기	6300	7300	8300
     * 딜리버리중	6400	7400	8400
     * 딜리버리완료	6500	7500	8500
     * 예약취소	    6800	7800	8800	9800
     * @see #rparStusCd 정비소실시간상태코드
     * 정비대기중/정비진행중 //TODO 각 코드 확인 필요
     * @see #autoRsvtPsblYn 오토케어예약가능여부
     * Y:신청가능 N:신청불가
     * @see #arptRsvtPsblYn 에어포트예약가능여부
     * Y:신청가능 N:신청불가
     * @see #hthRsvtPsblYn 홈투홈예약가능여부
     * Y:신청가능 N:신청불가
     * @see #rpshRsvtPsblYn 정비소예약가능여부
     * Y:신청가능 N:신청불가
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("carCareCpnList")
        private List<CouponVO> carCareCpnList;
        @Expose
        @SerializedName("autoRsvtStusCd")
        private String autoRsvtStusCd;
        @Expose
        @SerializedName("arptRsvtStusCd")
        private String arptRsvtStusCd;
        @Expose
        @SerializedName("hthRsvtStusCd")
        private String hthRsvtStusCd;
        @Expose
        @SerializedName("rpshRsvtStusCd")
        private String rpshRsvtStusCd;
        @Expose
        @SerializedName("rparStusCd")
        private String rparStusCd;
        @Expose
        @SerializedName("autoRsvtPsblYn")
        private String autoRsvtPsblYn;
        @Expose
        @SerializedName("arptRsvtPsblYn")
        private String arptRsvtPsblYn;
        @Expose
        @SerializedName("hthRsvtPsblYn")
        private String hthRsvtPsblYn;
        @Expose
        @SerializedName("rpshRsvtPsblYn")
        private String rpshRsvtPsblYn;
    }
}
