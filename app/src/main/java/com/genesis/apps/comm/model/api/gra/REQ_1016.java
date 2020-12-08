package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.SurveyItemVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_REQ_1016
 * @Brief service + 정비소예약 - 사전문진조회
 */
public class REQ_1016 extends BaseData {
    /**
     * @brief REQ_1016 요청 항목
     * @see #svyDivCd 설문분류코드
     * @see #svyMgmtNo 설문관리번호
     * @see #svyPrvsNo 설문항목번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("svyDivCd")
        private String svyDivCd;
        @Expose
        @SerializedName("svyMgmtNo")
        private String svyMgmtNo;
        @Expose
        @SerializedName("svyPrvsNo")
        private String svyPrvsNo;

        public Request(String menuId, String svyDivCd, String svyMgmtNo, String svyPrvsNo){
            this.svyDivCd = svyDivCd;
            this.svyMgmtNo = svyMgmtNo;
            this.svyPrvsNo = svyPrvsNo;
            setData(APIInfo.GRA_REQ_1016.getIfCd(), menuId);
        }
    }

    /**
     * @brief REQ_1016 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("svyDivCd")
        private String svyDivCd;
        @Expose
        @SerializedName("svyPrvsList")
        private List<SurveyItemVO> svyPrvsList;
    }


}
