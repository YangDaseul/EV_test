package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.TermVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_VOC_1004
 * @Brief Genesis + 하자재발 약관조회
 */
public class VOC_1004 extends BaseData {
    /**
     * @brief VOC_1004 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        public Request(String menuId){
            setData(APIInfo.GRA_VOC_1004.getIfCd(), menuId);
        }
    }

    /**
     * @brief VOC_1004 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("termList")
        private List<TermVO> termList; //termCont는 1005에서 요청
    }
}
