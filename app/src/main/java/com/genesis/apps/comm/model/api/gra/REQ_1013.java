package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.RepairReserveVO;
import com.genesis.apps.comm.model.vo.RepairVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_REQ_1013
 * @Brief service + 정비예약 현황
 */
public class REQ_1013 extends BaseData {
    /**
     * @brief REQ_1013 요청 항목
     * @see #vin 차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String menuId, String vin) {
            this.vin = vin;
            setData(APIInfo.GRA_REQ_1013.getIfCd(), menuId);
        }
    }

    /**
     * @brief REQ_1013 응답 항목
     * @see #stusCd 차량현재상태코드
     * 상태값이 없을 경우 '0000'
     * 예약상태코드	홈투홈	에어포트	오토케어	정비소
     * 예약신청	    1100	2100	3100	4100
     * 예약완료(예약중)1200	2200	3200	4200
     * 픽업대기	    1300	2300	3300
     * 픽업중	    1400	2400	3400
     * 정비소도착	1500	2500	3500
     * 정비 -작업대기4610	4610	4610	4610
     * 정비 -차량점검4720	4720	4720
     * 정비 -작업중	4730	4730	4730
     * 정비 -최종점검4740	4740	4740
     * 정비 -작업완료4850	4850	4850	4850
     * 딜리버리대기	6300	7300	8300
     * 딜리버리중	6400	7400	8400
     * 딜리버리완료	6500	7500	8500
     * 예약취소	    6800	7800	8800	9800
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("stusCd")
        private String stusCd;

        @Expose
        @SerializedName("rparStatus")
        private RepairVO rparStatus;

        @Expose
        @SerializedName("rsvtStatList")
        private List<RepairReserveVO> rsvtStatList;
    }

}
