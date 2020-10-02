package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.NotiVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_MYP_8005
 * @Brief 알림센터 알림목록 요청
 */
public class MYP_8005 extends BaseData {
    /**
     * @author hjpark
     * @brief MYP_8005 요청 항목
     * @see #pageNo 페이지번호
     * @see #searchCnt 조회 요청 개수
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("pageNo")
        private String pageNo;
        @Expose
        @SerializedName("searchCnt")
        private String searchCnt;

        public Request(String menuId, String pageNo, String searchCnt){
            this.pageNo = pageNo;
            this.searchCnt = searchCnt;
            setData(APIInfo.GRA_MYP_8005.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief MYP_8005 응답 항목
     * @see #notiList 공지리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("notiList")
        private List<NotiVO> notiList;
    }
}
