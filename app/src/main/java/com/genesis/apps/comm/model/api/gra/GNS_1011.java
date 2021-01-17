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
 * @file GRA_GNS_1011
 * @Brief My차고 + 모빌리티케어 쿠폰
 */
public class GNS_1011 extends BaseData {

    /**
     * @brief GNS_1011 요청 항목
     * @see #vin 차대번호
     * @see #csmrScnCd 실 운행자고객구분코드
     * 1 : 개인(법인임직원) = 법인 렌트리스
     * 14 : 개인(리스/렌트 이용개인) = 개인렌트리스
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("csmrScnCd")
        private String csmrScnCd;

        public Request(String menuId, String vin, String csmrScnCd){
            this.vin = vin;
            this.csmrScnCd = csmrScnCd;
            setData(APIInfo.GRA_GNS_1011.getIfCd(), menuId);
        }
    }

    /**
     * @brief GNS_1011 응답 항목
     * @see #rgstPsblYn 등록가능여부
     * 차량렌트리스 정보 등록된 차량인지 확인
     * Y: 등록 N:미등록(렌트리스 차량이 아님)
     * @see #ctrctNo 계약번호
     * 렌트리스 등록 가능한 차량인 경우 필수
     * @see #mdlNm 차량모델명
     * 프리빌리지 특화상품 신청가능한 차량인 경우 필수(ex : G90, G80)
     * @see #svcIntroUri 서비스안내Uri
     * 외부 웹뷰 이동
     * EQ900 : https://www.genesis.com/kr/ko/members/genesis-membership/privilege/eq900.html
     * G80 : https://www.genesis.com/kr/ko/members/genesis-membership/privilege/g80.html
     * G90 : https://www.genesis.com/kr/ko/members/genesis-membership/privilege/g90.html
     * @see #godsList 상품리스트
     * 프리빌리지 특화상품 신청가능한 차량인 경우 필수
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("rgstPsblYn")
        private String rgstPsblYn;
        @Expose
        @SerializedName("ctrctNo")
        private String ctrctNo;
        @Expose
        @SerializedName("mdlNm")
        private String mdlNm;
        @Expose
        @SerializedName("svcIntroUri")
        private String svcIntroUri;
        @Expose
        @SerializedName("godsList")
        private List<RentStatusVO> godsList;
    }
}
