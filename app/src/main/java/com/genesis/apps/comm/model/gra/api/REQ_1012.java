package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_REQ_1012
 * @Brief service + 정비예약 - 정비소 예약신청
 */
public class REQ_1012 extends BaseData {
    /**
     * @brief REQ_1012 요청 항목
     * @see #vin 차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
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
        @SerializedName("rsvtHopeTm")
        private String rsvtHopeTm;
        @Expose
        @SerializedName("hpNo")
        private String hpNo;
        @Expose
        @SerializedName("acps1Cd")
        private String acps1Cd;
        @Expose
        @SerializedName("rqrm")
        private String rqrm;


        //TODO 아래는 REPAIR VO와 REPAIRGROUP VO를 넣어줘야함
        @Expose
        @SerializedName("asnCd")
        private String asnCd;
        @Expose
        @SerializedName("asnNm")
        private String asnNm;
        @Expose
        @SerializedName("repTn")
        private String repTn;
        @Expose
        @SerializedName("pbzAdr")
        private String pbzAdr;
        @Expose
        @SerializedName("rpshGrpCd")
        private String rpshGrpCd;
        @Expose
        @SerializedName("rpshGrpNm")
        private String rpshGrpNm;


        public Request(String menuId){
            //TODO RepairReserveVO repairReserveVO, REPAIR VO와 REPAIRGROUP VO를 DEEP COPY
            //            REQ_1012.Request cpy = new Gson().fromJson(new Gson().toJson(repairReserveVO), REQ_1012.Request.class);
            //            try {
            //                원본데이터 = (REQ_1012.Request) cpy.clone();
            //            }catch ( Exception e){
            //
            //            }



            setData(APIInfo.GRA_REQ_1012.getIfCd(), menuId);
        }
    }

    /**
     * @brief REQ_1012 응답 항목
     * @see #rsvtNo 예약번호
     * 예약코드번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("rsvtNo")
        private String rsvtNo;
    }
}
