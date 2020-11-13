package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.CardVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_MYP_2001
 * @Brief MyG+ 멤버쉽정보(사용가능포인트, 소멸예정포인트,보유카드수)
 */
public class MYP_2001 extends BaseData {
    /**
     * @author hjpark
     * @brief MYP_2001 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        public Request(String menuId){
            setData(APIInfo.GRA_MYP_2001.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief MYP_2001 응답 항목
     * @see #blueMbrYn 블루멤버스 회원여부
     * @see #bludMbrPoint 블루멤버스 사용가능포인트
     * @see #usedBlueMbrPoint 사용한 포인트
     * @see #savgPlanPont 적립예정 포인트
     *
     * @see #extncDtm 소멸일자
     * Today 기준의 소멸 내역 중에서 미래에 가장 가까운 일자(YYYYMDD)
     * @see #extncPont 소멸일자포인트
     * Today 기준의 소멸 내역 중에서 미래에 가장 가까운 일자의 소멸포인트
     * @see #extncPont6mm 6개월내소멸포인트
     * Today 기준의 소멸 내역 중에서 6개월내의 소멸포인트 합
     * @see #blueMbrCrdCnt 블루멤버스 카드 수
     * 0 :  정상, 발급중인 카드수 없음
     * @see #blueMbrCrdList 블루멤버스 카드 목록
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("blueMbrYn")
        private String blueMbrYn;
        @Expose
        @SerializedName("bludMbrPoint")
        private String bludMbrPoint;
        @Expose
        @SerializedName("usedBlueMbrPoint")
        private String usedBlueMbrPoint;
        @Expose
        @SerializedName("savgPlanPont")
        private String savgPlanPont;

        @Expose
        @SerializedName("extncDtm")
        private String extncDtm;
        @Expose
        @SerializedName("extncPont")
        private String extncPont;
        @Expose
        @SerializedName("extncPont6mm")
        private String extncPont6mm;
        @Expose
        @SerializedName("blueMbrCrdCnt")
        private String blueMbrCrdCnt;

        @Expose
        @SerializedName("blueMbrCrdList")
        private List<CardVO> blueMbrCrdList;
    }
}
