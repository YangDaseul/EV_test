package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.SOSStateVO;
import com.genesis.apps.comm.model.vo.TermVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_SOS_3011
 * @Brief 긴급충전출동 약관목록
 */
public class SOS_3011 extends BaseData {
    /**
     * @brief SOS_3011 요청 항목
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        public Request(String menuId){
            setData(APIInfo.GRA_SOS_3011.getIfCd(), menuId);
        }
    }

    /**
     * @brief SOS_3011 응답 항목
     * @see #trmsAgmtYn 약관동의여부
     * GRA0100:개인정보수집이용동의
     * GRA0101:위치기반서비스이용안내
     * 두 약관을 모두 동의한 경우에만 Y, 그외 N
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("trmsAgmtYn")
        private String trmsAgmtYn;
        @Expose
        @SerializedName("sosList")
        private List<TermVO> termList;
    }
}
