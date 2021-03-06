package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_DDS_1005
 * @Brief Genesis + 대리운전 평가 등록
 */
public class DDS_1005 extends BaseData {
    /**
     * @author hjpark
     * @brief DDS_1005 요청 항목
     * 제네시스 CRM에서 발급되는 고객관리번호
     * @see #transId 트랜젝션ID
     * @see #userRate 사용자평점
     * 사용자평점(별점)은 1~5 의 값.
     * @see #userComment 사용자후기
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("transId")
        private String transId;
        @Expose
        @SerializedName("userRate")
        private String userRate;
        @Expose
        @SerializedName("userComment")
        private String userComment;

        public Request(String menuId, String transId, String userRate, String userComment){
            this.transId = transId;
            this.userRate = userRate;
            this.userComment = userComment;
            setData(APIInfo.GRA_DDS_1005.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief DDS_1005 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
    }
}
