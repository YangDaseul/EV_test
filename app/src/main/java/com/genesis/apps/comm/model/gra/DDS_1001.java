package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.PositionVO;
import com.genesis.apps.comm.model.vo.TermVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_DDS_1001
 * @Brief Genesis + 대리운전 진행 상태 확인
 */
public class DDS_1001 extends BaseData {
    /**
     * @author hjpark
     * @brief DDS_1001 요청 항목
     * @see #mbrMgmtNo 회원관리번호
     * 제네시스 CRM에서 발급되는 고객관리번호
     * @see #vin 차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        @Expose
        @SerializedName("mbrMgmtNo")
        private String mbrMgmtNo;
        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String menuId, String mbrMgmtNo, String vin){
            this.mbrMgmtNo = mbrMgmtNo;
            this.vin = vin;
            setData(APIInfo.GRA_DDS_1001.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief DDS_1001 응답 항목
     * @see #transId    트랜젝션ID
     * @see #vin	    차대번호
     * @see #carRegNo	차량번호
     * @see #mdlNm	    모델명
     * @see #reqDivCd	신청구분코드
     * @see #svcStusCd	서비스상태코드
     * @see #rsvDt	    예약일시
     * @see #driverNm	기사성명
     * @see #driverMdn	기사전화번호
     * @see #posInfo	위치정보
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("transId")
        private String transId;
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("carRegNo")
        private String carRegNo;
        @Expose
        @SerializedName("mdlNm")
        private String mdlNm;
        @Expose
        @SerializedName("reqDivCd")
        private String reqDivCd;
        @Expose
        @SerializedName("svcStusCd")
        private String svcStusCd;
        @Expose
        @SerializedName("rsvDt")
        private String rsvDt;
        @Expose
        @SerializedName("driverNm")
        private String driverNm;
        @Expose
        @SerializedName("driverMdn")
        private String driverMdn;
        @Expose
        @SerializedName("posInfo")
        private List<PositionVO> posInfo;
    }
}
