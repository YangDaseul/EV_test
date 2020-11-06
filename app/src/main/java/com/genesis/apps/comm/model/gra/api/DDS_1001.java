package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
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
    //신청 현황 화면이 나와야됨
    public static final String STATUS_DRIVER_MATCH_WAIT = "1100";//기사 배정 준비중
    public static final String STATUS_RESERVE_SUCC = "1110";
    public static final String STATUS_DRIVER_MATCHED = "1200";
    public static final String STATUS_DRIVER_REMATCHED = "1210";
    public static final String STATUS_DRIVE_NOW = "1300";
    public static final String STATUS_NO_DRIVER = "1410";//기사 미배정

    //신청하기 화면으로 진행함
    public static final String STATUS_REQ = "1000";//신청중
    public static final String STATUS_SERVICE_COMPLETE = "1310";
    public static final String STATUS_CANCEL_BY_USER = "1400";
    public static final String STATUS_CANCEL_CAUSE_NO_DRIVER = "1420";
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
    class Request extends BaseRequest {
        @Expose
        @SerializedName("mbrMgmtNo")
        private String mbrMgmtNo;
        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String menuId, String mbrMgmtNo, String vin) {
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
    class Response extends BaseResponse {
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
