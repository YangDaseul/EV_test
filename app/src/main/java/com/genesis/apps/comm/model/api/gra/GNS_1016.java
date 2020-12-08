package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.RentStatusVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_GNS_1016
 * @Brief My차고 + 렌트/리스 특화상품 조회 (대상차량인 경우만 : G80 G90)
 */
public class GNS_1016 extends BaseData {

    /**
     * @brief GNS_1016 요청 항목
     * @see #ctrctNo 계약번호
     * @see #mdlNm 차량모델명
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        @Expose
        @SerializedName("ctrctNo")
        private String ctrctNo;
        @Expose
        @SerializedName("mdlNm")
        private String mdlNm;

        public Request(String menuId, String ctrctNo, String mdlNm){
            this.ctrctNo = ctrctNo;
            this.mdlNm = mdlNm;
            setData(APIInfo.GRA_GNS_1016.getIfCd(),menuId);
        }
    }

    /**
     * @brief GNS_1016 응답 항목
     * @see #svcIntroUri 서비스 안내 uri
     * 외부 웹뷰 이동
     * EQ900 : https://www.genesis.com/kr/ko/members/genesis-membership/privilege/eq900.html
     * G80 : https://www.genesis.com/kr/ko/members/genesis-membership/privilege/g80.html
     * G90 : https://www.genesis.com/kr/ko/members/genesis-membership/privilege/g90.html
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("svcIntroUri")
        private String svcIntroUri;
        @Expose
        @SerializedName("godsList")
        private List<RentStatusVO> godsList;
    }
}
