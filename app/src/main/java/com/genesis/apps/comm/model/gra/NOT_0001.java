package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.NotiInfoVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_NOT_0001
 * @Brief 알림센터 알림목록 요청
 */
public class NOT_0001 extends BaseData {
    /**
     * @author hjpark
     * @brief NOT_0001 요청 항목
     * @see #pageNo 페이지번호
     * 최근 순으로 페이지 번호
     * @see #searchCnt 조회 건수
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        @Expose
        @SerializedName("cateCd")
        private String cateCd;
        @Expose
        @SerializedName("pageNo")
        private String pageNo;
        @Expose
        @SerializedName("searchCnt")
        private String searchCnt;

        public Request(String menuId, String pageNo, String searchCnt){
            this.pageNo = pageNo;
            this.searchCnt = searchCnt;
            setData(APIInfo.GRA_NOT_0001.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief NOT_0001 응답 항목
     * @see #notiInfoList 알림정보 리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("notiInfoList")
        private List<NotiInfoVO> notiInfoList;
    }
}
