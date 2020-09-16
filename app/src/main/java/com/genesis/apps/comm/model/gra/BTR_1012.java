package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.BtrCnslVO;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_BTR_1012
 * @Brief Genesis + 버틀러 상담 이력조회
 */
public class BTR_1012 extends BaseData {

    /**
     * @brief BTR_1012 요청 항목
     * @see #pageNo 페이지번호
     * @see #searchCnt 조회 건수
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{

        @Expose
        @SerializedName("pageNo")
        private String pageNo;
        @Expose
        @SerializedName("searchCnt")
        private String searchCnt;

        public Request(String menuId,String pageNo, String searchCnt){
            this.pageNo = pageNo;
            this.searchCnt = searchCnt;
            setData(APIInfo.GRA_BTR_1012.getIfCd(),menuId);
        }
    }

    /**
     * @brief BTR_1012 응답 항목
     * @see #totCnt 전체이력수
     * @see #cnsltList 상담이력리스트
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("totCnt")
        private String totCnt;
        @Expose
        @SerializedName("cnsltList")
        private List<BtrCnslVO> cnsltList;
    }
}
