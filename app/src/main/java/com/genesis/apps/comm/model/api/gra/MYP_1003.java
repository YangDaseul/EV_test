package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.CreditVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_MYP_1003
 * @Brief MyG + 블루멤버스 정보
 */
public class MYP_1003 extends BaseData {
    /**
     * @author hjpark
     * @brief MYP_1003 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {

        public Request(String menuId){
            setData(APIInfo.GRA_MYP_1003.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief MYP_1003 응답 항목
     * @see #blueMbrCrdCnt 블루멤버스 카드수
     * @see #bludMbrPoint 블루멤버스 사용가능포인트
     * @see #blueMbrYn 블루멤버스 회원여부
     * 회원 : Y, 비회원 : N
     * @see #cretPntTot 크레딧포인트 합계
     * @see #cretPntList 크레딧 포인트리스트
     * @see #stMbrYn 에스트래픽회원여부
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("blueMbrCrdCnt")
        private String blueMbrCrdCnt;
        @Expose
        @SerializedName("bludMbrPoint")
        private String bludMbrPoint;
        @Expose
        @SerializedName("blueMbrYn")
        private String blueMbrYn;
        @Expose
        @SerializedName("cretPntTot")
        private String cretPntTot;
        @Expose
        @SerializedName("cretPntList")
        private List<CreditVO> cretPntList;
        @Expose
        @SerializedName("stMbrYn")
        private String stMbrYn;
    }
}
