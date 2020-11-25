package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_VOC_1002
 * @Brief Genesis + 하자재발 신청
 */
public class VOC_1002 extends BaseData {
    /**
     * @brief VOC_1002 요청 항목
     * @see #vin 차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("csmrNm")
        private String csmrNm;
        @Expose
        @SerializedName("csmrTymd")
        private String csmrTymd;
        @Expose
        @SerializedName("emlAdr")
        private String emlAdr;
        @Expose
        @SerializedName("rdwNmZip")
        private String rdwNmZip;
        @Expose
        @SerializedName("rdwNmAdr")
        private String rdwNmAdr;

        @Expose
        @SerializedName("rdwNmDtlAdr")
        private String rdwNmDtlAdr;
        @Expose
        @SerializedName("regnTn")
        private String regnTn;
        @Expose
        @SerializedName("frtDgtTn")
        private String frtDgtTn;
        @Expose
        @SerializedName("realDgtTn")
        private String realDgtTn;
        @Expose
        @SerializedName("recvDt")
        private String recvDt;
        @Expose
        @SerializedName("carNm")
        private String carNm;
        @Expose
        @SerializedName("crnVehlCd")
        private String crnVehlCd;
        @Expose
        @SerializedName("mdYyyy")
        private String mdYyyy;
        @Expose
        @SerializedName("whotYmd")
        private String whotYmd;
        @Expose
        @SerializedName("trvgDist")
        private String trvgDist;
        @Expose
        @SerializedName("carNo")
        private String carNo;
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("wpa")
        private String wpa;
        @Expose
        @SerializedName("admz")
        private String admz;
        @Expose
        @SerializedName("flawCd")
        private String flawCd;
        @Expose
        @SerializedName("wkr1Nm")
        private String wkr1Nm;
        @Expose
        @SerializedName("wk1StrtDt")
        private String wk1StrtDt;
        @Expose
        @SerializedName("wk1Dt")
        private String wk1Dt;
        @Expose
        @SerializedName("wk1TrvgDist")
        private String wk1TrvgDist;
        @Expose
        @SerializedName("wk1Caus")
        private String wk1Caus;
        @Expose
        @SerializedName("wk1Dtl")
        private String wk1Dtl;

        @Expose
        @SerializedName("wkr2Nm")
        private String wkr2Nm;
        @Expose
        @SerializedName("wk2StrtDt")
        private String wk2StrtDt;
        @Expose
        @SerializedName("wk2Dt")
        private String wk2Dt;
        @Expose
        @SerializedName("wk2TrvgDist")
        private String wk2TrvgDist;
        @Expose
        @SerializedName("wk2Caus")
        private String wk2Caus;
        @Expose
        @SerializedName("wk2Dtl")
        private String wk2Dtl;

        @Expose
        @SerializedName("wkr3Nm")
        private String wkr3Nm;
        @Expose
        @SerializedName("wk3StrtDt")
        private String wk3StrtDt;
        @Expose
        @SerializedName("wk3Dt")
        private String wk3Dt;
        @Expose
        @SerializedName("wk3TrvgDist")
        private String wk3TrvgDist;
        @Expose
        @SerializedName("wk3Caus")
        private String wk3Caus;
        @Expose
        @SerializedName("wk3Dtl")
        private String wk3Dtl;
        @Expose
        @SerializedName("wkCntFth")
        private String wkCntFth;
        @Expose
        @SerializedName("wkCnt")
        private String wkCnt;
        @Expose
        @SerializedName("wkPeriod")
        private String wkPeriod;
        @Expose
        @SerializedName("prnInfoAgreeFlg")
        private String prnInfoAgreeFlg;
        @Expose
        @SerializedName("carMgmtAgreeFlg")
        private String carMgmtAgreeFlg;


        public Request(String menuId){
            //TODO 생성자가 없는 대신.. 직접 생성해서 데이터 넣을 것
            setData(APIInfo.GRA_VOC_1002.getIfCd(), menuId);
        }
    }

    /**
     * @brief VOC_1002 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
