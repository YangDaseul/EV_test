package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.WashReserveVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @file GRA_WSH_1004
 * @Brief service + 소낙스 세차예약 내역
 * @author hjpark
 */
public class WSH_1004 extends BaseData {
    public static final String RESERVE_VALID = "1000";      //예약됨
    public static final String RESERVE_COMPLETED = "2000";  //이용완료
    public static final String RESERVE_CANCELED = "9000";   //예약취소

    public static final String PAY_CASH = "1000";           //현금결제

    /**
     * @brief WSH_1004의 요청 항목
     * @author hjpark
     * @see #cmpyCd 업체코드            소낙스  : SONAX
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {
        @Expose
        @SerializedName("cmpyCd")
        private String cmpyCd;

        public Request(String menuId, String cmpyCd){
            this.cmpyCd = cmpyCd;
            setData(APIInfo.GRA_WSH_1004.getIfCd(), menuId);
        }
    }

    /**
     * @brief WSH_1004의 응답 항목
     * @author hjpark
     * @see #rsvtList 예약리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("rsvtList")
        private List<WashReserveVO> rsvtList;
    }
}
