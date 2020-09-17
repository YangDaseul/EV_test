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
     * @see #carRgstNo 차량번호
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
        @SerializedName("carRgstNo")
        private String carRgstNo;
        @Expose
        @SerializedName("rparTypCd")
        private String rparTypCd;

        public Request(String menuId, String vin, String carRgstNo, String rparTypCd){
            this.vin = vin;
            this.carRgstNo = carRgstNo;
            this.rparTypCd = rparTypCd;
            setData(APIInfo.GRA_REQ_1005.getIfCd(), menuId);
        }
    }

    /**
     * @brief REQ_1005 응답 항목
     *
     * @see #carCareCpnList 카케어쿠폰리스트
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
