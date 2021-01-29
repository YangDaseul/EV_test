package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.ContentsVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_CTT_1001
 * @Brief contents + 컨텐츠조회
 */
public class CTT_1001 extends BaseData {
    /**
     * @author hjpark
     * @brief CTT_1001 요청 항목
     * @see #catCd 카테고리코드
     * @see #keywd 검색어
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("catCd")
        private String catCd;
        @Expose
        @SerializedName("keywd")
        private String keywd;

        public Request(String menuId, String catCd, String keywd){
            this.catCd = catCd;
            this.keywd = keywd;
            setData(APIInfo.GRA_CTT_1001.getIfCd(),menuId);
        }
    }

    /**
     * @author hjpark
     * @brief CTT_1001 응답 항목
     * @see #ttlList 제목리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("ttlList")
        private List<ContentsVO> ttlList;
    }
}
