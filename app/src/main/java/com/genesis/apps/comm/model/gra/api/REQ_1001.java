package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_REQ_1001
 * @Brief service + 정비 - 서비스메인
 */
public class REQ_1001 extends BaseData {
    /**
     * @brief REQ_1001 요청 항목
     * @see #vin 차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String menuId, String vin){
            this.vin = vin;
            setData(APIInfo.GRA_REQ_1001.getIfCd(), menuId);
        }
    }

    /**
     * @brief REQ_1001 응답 항목
     * @see #stusCd 차량현재상태코드
     * 코드값이 4610 ~ 4850 인 경우 "정비중" 표시. 그외 표시 안함.
     * @see #pgrsStusCd 긴급출동상태코드
     * 진행상태 - (R:신청, -> W:접수,-> S:출동,-> E:완료, C:취소)
     * 긴급출동 신청건이 없을경우 "0000"
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("stusCd")
        private String stusCd;
        @Expose
        @SerializedName("pgrsStusCd")
        private String pgrsStusCd;
    }
}
