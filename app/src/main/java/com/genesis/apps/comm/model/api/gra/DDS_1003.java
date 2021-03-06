package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.DriveServiceVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_DDS_1003
 * @Brief Genesis + 대리운전 신청 내용 리스트
 */
public class DDS_1003 extends BaseData {
    /**
     * @author hjpark
     * @brief DDS_1003 요청 항목
     * @see #pageNo 페이지번호
     * @see #searchCnt 조회건수
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

        public Request(String menuId,  String pageNo, String searchCnt){
            this.pageNo = pageNo;
            this.searchCnt = searchCnt;
            setData(APIInfo.GRA_DDS_1003.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief DDS_1003 응답 항목
     * @see #totalCnt 전체건수
     * @see #listCnt 조회목록수
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("totalCnt")
        private String totalCnt;
        @Expose
        @SerializedName("listCnt")
        private String listCnt;
        @Expose
        @SerializedName("svcInfo")
        private List<DriveServiceVO> svcInfo;
    }
}
