package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.PrivilegeVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_MYP_1005
 * @Brief MyG+ 프리빌리지
 */
public class MYP_1005 extends BaseData {
    /**
     * @author hjpark
     * @brief MYP_1005 요청 항목
     * @see #vin 차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        public Request(String menuId, String vin){
            this.vin = vin;
            setData(APIInfo.GRA_MYP_1005.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief MYP_1005 응답 항목
     * @see #mbrshJoinYn 멤버쉽가입여부
     * Y : 가입 N:미가입
     * - 프리빌리지는 멤버쉽 가입자만 가능하다고 함
     * @see #pvilList 프리빌리지 List
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("mbrshJoinYn")
        private String mbrshJoinYn;
        @Expose
        @SerializedName("pvilList")
        private List<PrivilegeVO> pvilList;
    }
}
