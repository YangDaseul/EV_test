package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.AddressZipVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_PUB_1001
 * @Brief Home + 시도조회
 */
public class PUB_1001 extends BaseData {
    /**
     * @brief PUB_1001 요청 항목
     * @see #keywd 검색어
     * @see #pageNo 출력할 페이지 번호
     * @see #searchCnt 페이지당 출력수
     * 페이지당 출력될 개수를 지정(최대50)
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        @Expose
        @SerializedName("keywd")
        private String keywd;
        @Expose
        @SerializedName("pageNo")
        private String pageNo;
        @Expose
        @SerializedName("searchCnt")
        private String searchCnt;

        public Request(String menuId, String keywd, String pageNo, String searchCnt){
            this.keywd = keywd;
            this.pageNo = pageNo;
            this.searchCnt = searchCnt;

            setData(APIInfo.GRA_PUB_1001.getIfCd(), menuId);
        }
    }
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("totPageNo")
        private String totPageNo;
        @Expose
        @SerializedName("curPageNo")
        private String curPageNo;
        @Expose
        @SerializedName("zipList")
        private List<AddressZipVO> zipList;
    }
}
