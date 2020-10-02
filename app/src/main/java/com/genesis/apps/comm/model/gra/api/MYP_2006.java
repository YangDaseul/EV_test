package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.MembershipPointVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_MYP_2006
 * @Brief MyG+ 멤버쉽 카드 안내 요청
 */
public class MYP_2006 extends BaseData {
    /**
     * @author hjpark
     * @brief MYP_2006 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {

        public Request(String menuId){
            setData(APIInfo.GRA_MYP_2006.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief MYP_2006 응답 항목
     * @see #blueMbrYn 블루멤버스 회원여부
     * 블루멤버스 회원여부  (회원 : Y, 비회원 : N)
     * @see #extncPlanCnt 소멸예정건수
     * 0 : 6개월내 소멸내역이 없음
     * @see #extncPlanList 소멸예정목록
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("blueMbrYn")
        private String blueMbrYn;
        @Expose
        @SerializedName("extncPlanCnt")
        private String extncPlanCnt;
        @Expose
        @SerializedName("extncPlanList")
        private List<MembershipPointVO> extncPlanList;
    }
}
