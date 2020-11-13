package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.RepairHistVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_REQ_1014
 * @Brief service + 정비 이력
 */
public class REQ_1014 extends BaseData {
    /**
     * @brief REQ_1014 요청 항목
     * @see #vin 차대번호
     * @see #pageNo 페이지 번호
     * 최근 순으로 페이지 번호
     * @see #searchCnt 조회 건수
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("pageNo")
        private String pageNo;
        @Expose
        @SerializedName("searchCnt")
        private String searchCnt;

        public Request(String menuId, String vin, String pageNo, String searchCnt){
            this.vin = vin;
            this.pageNo = pageNo;
            this.searchCnt = searchCnt;
            setData(APIInfo.GRA_REQ_1014.getIfCd(), menuId);
        }
    }

    /**
     * @brief REQ_1014 응답 항목
     * @see #totCnt 총 건수
     * @see #rsvtStatList 이력리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("totCnt")
        private String totCnt;
        @Expose
        @SerializedName("rsvtStatList")
        private List<RepairHistVO> rsvtStatList;
    }
}
