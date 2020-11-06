package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.PositionVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_DDS_1004
 * @Brief Genesis + 대리운전 취소 요청
 */
public class DDS_1004 extends BaseData {
    public static final String CANCEL = "01";
    public static final String CANCEL_CAUSE_NO_DRIVER = "02";

    /**
     * @author hjpark
     * @brief DDS_1004 요청 항목
     * @see #mbrMgmtNo 회원관리번호
     * 제네시스 CRM에서 발급되는 고객관리번호
     * @see #vin 차대번호
     * @see #transId 트랜젝션ID
     * @see #cnclType 취소 유형
     * 1) 일반 사용자 취소 : 01
     *   => 사용자 취소(1400) 상태로 DB 업데이트
     * 2) 기사 배정 실패에 의한 사용자 취소 : 02
     *   => 기사 미배정 취소(1420) 상태로 DB 업데이트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("transId")
        private String transId;
        @Expose
        @SerializedName("cnclType")
        private String cnclType;

        public Request(String menuId, String vin, String transId, String cnclType) {
            this.vin = vin;
            this.transId = transId;
            this.cnclType = cnclType;
            setData(APIInfo.GRA_DDS_1004.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief DDS_1004 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
