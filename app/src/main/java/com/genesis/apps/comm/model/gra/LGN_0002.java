package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.PartVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
     * @see #vin 차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String vin){
            this.vin = vin;
            setData(APIInfo.GRA_LGN_0002.getIfCd());
        }
    }

    /**
     * @brief LGN_0001 응답 항목
     * @author hjpark
     *
     *
     * @see #carGbCd 차량구분코드
     * 차량구분코드 (주차량/계약차량/디폴트차량)
     * 주차량: 0001, 계약차량: 0002, 디폴트차량: 0000
     * @see #vin 차대번호
     * @see #carRgstNo 차량번호
     * @see #mdlNm 차량모델명
     * @see #mdlCd 차량모델코드명
     * @see #saleMdlCd 판매모델코드
     * @see #saleMdlNm 판매모델명
     * @see #xrclCtyNo 차량외장컬러상품번호
     * @see #ieclCtyNo 차량내장컬러상품번호
     * @see #ctrctNo 계약번호
     * @see #vhclImgUri 차량이미지URI
     *
     * @see #totalDrivDist 총 주행거리(KM)
     * @see #lastDrivDist 최근 주행거리(KM)
     * @see #canDrivDist 주행 가능거리(KM)
     *
     * @see #partList 소모품 정보 리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        //TODO VehicleVO 쓸것
        @Expose
        @SerializedName("carGbCd")
        private String carGbCd;
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("carRgstNo")
        private String carRgstNo;
        @Expose
        @SerializedName("mdlNm")
        private String mdlNm;
        @Expose
        @SerializedName("mdlCd")
        private String mdlCd;
        @Expose
        @SerializedName("saleMdlCd")
        private String saleMdlCd;
        @Expose
        @SerializedName("saleMdlNm")
        private String saleMdlNm;
        @Expose
        @SerializedName("xrclCtyNo")
        private String xrclCtyNo;
        @Expose
        @SerializedName("ieclCtyNo")
        private String ieclCtyNo;
        @Expose
        @SerializedName("ctrctNo")
        private String ctrctNo;
        @Expose
        @SerializedName("vhclImgUri")
        private String vhclImgUri;

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
        @SerializedName("partList")
        private List<PartVO> partList;
    }
}
