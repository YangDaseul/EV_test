package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.RepairReserveVO;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_REQ_1007
 * @Brief service + 정비예약 - 오토케어 예약신청
 */
public class REQ_1007 extends BaseData {
    /**
     * @brief REQ_1007 요청 항목
     * @see #vin 차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        @Expose
        @SerializedName("rsvtTypCd")
        private String rsvtTypCd;
        @Expose
        @SerializedName("rparTypCd")
        private String rparTypCd;
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("carRgstNo")
        private String carRgstNo;
        @Expose
        @SerializedName("mdlCd")
        private String mdlCd;
        @Expose
        @SerializedName("mdlNm")
        private String mdlNm;
        @Expose
        @SerializedName("rsvtHopeDt")
        private String rsvtHopeDt;
        @Expose
        @SerializedName("autoAmpmCd")
        private String autoAmpmCd;
        @Expose
        @SerializedName("hpNo")
        private String hpNo;
        @Expose
        @SerializedName("pckpZip")
        private String pckpZip;
        @Expose
        @SerializedName("pckpAddr")
        private String pckpAddr;
        @Expose
        @SerializedName("dlvryZip")
        private String dlvryZip;
        @Expose
        @SerializedName("dlvryAddr")
        private String dlvryAddr;
        @Expose
        @SerializedName("autoSvc1")
        private String autoSvc1;
        @Expose
        @SerializedName("autoSvc2")
        private String autoSvc2;
        @Expose
        @SerializedName("autoSvc3")
        private String autoSvc3;
        @Expose
        @SerializedName("autoSvc4")
        private String autoSvc4;
        @Expose
        @SerializedName("rqrm")
        private String rqrm;

        public Request(String menuId){
            //TODO RepairReserveVO repairReserveVO를 DEEP COPY
            //            REQ_1007.Request cpy = new Gson().fromJson(new Gson().toJson(repairReserveVO), REQ_1007.Request.class);
            //            try {
            //                원본데이터 = (REQ_1007.Request) cpy.clone();
            //            }catch ( Exception e){
            //
            //            }
            setData(APIInfo.GRA_REQ_1007.getIfCd(), menuId);
        }
    }

    /**
     * @brief REQ_1007 응답 항목
     * @see #rsvtNo 예약번호
     * 홈투홈신청ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("rsvtNo")
        private String rsvtNo;
    }
}
