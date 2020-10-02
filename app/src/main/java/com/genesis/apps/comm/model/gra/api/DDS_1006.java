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
 * @file GRA_DDS_1006
 * @Brief Genesis + 수동 배정 요청
 */
public class DDS_1006 extends BaseData {
    /**
     * @author hjpark
     * @brief DDS_1006 요청 항목
     * @see #mbrMgmtNo 회원관리번호
     * 제네시스 CRM에서 발급되는 고객관리번호
     * @see #vin 차대번호
     * @see #transId 트랜젝션ID
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
        @Expose
        @SerializedName("transId")
        private String transId;

        public Request(String menuId, String mbrMgmtNo, String vin, String transId){
            this.mbrMgmtNo = mbrMgmtNo;
            this.vin = vin;
            this.transId = transId;
            setData(APIInfo.GRA_DDS_1006.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief DDS_1006 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
