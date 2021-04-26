package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.ReviewVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @file GRA_EPT_1003
 * @Brif E-PIT 충전소 상세 리뷰
 */
public class EPT_1003 extends BaseData {
    /**
     * @brief EPT_1003 요청 항목
     * @see #espid EPIT-기관ID
     * E-PIT 발급한 ID
     * @see #ecsid EPIT-충전소ID
     * E-PIT 발급한 ID
     * @see #pgNo 페이지번호
     * @see #pgCnt 페이지당출력수
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {
        @Expose
        @SerializedName("espid")
        private String espid;
        @Expose
        @SerializedName("ecsid")
        private String ecsid;
        @Expose
        @SerializedName("pgNo")
        private String pgNo;
        @Expose
        @SerializedName("pgCnt")
        private String pgCnt;

        public Request(String menuId, String espid, String ecsid, String pgNo, String pgCnt) {
            this.espid = espid;
            this.ecsid = ecsid;
            this.pgNo = pgNo;
            this.pgCnt = pgCnt;
            setData(APIInfo.GRA_EPT_1003.getIfCd(), menuId);
        }
    }

    /**
     * @brief EPT_1003 응답 항목
     * @see #revwList 충전소 리뷰 목록
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Response extends BaseResponse {
        @Expose
        @SerializedName("revwList")
        private List<ReviewVO> revwList;
    }
} // end of class EPT_1003
