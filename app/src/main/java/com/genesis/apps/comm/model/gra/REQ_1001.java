package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_REQ_1001
 * @Brief service + 정비 - 서비스메인
 */
public class REQ_1001 extends BaseData {
    /**
     * @brief REQ_1001 요청 항목
     * @see #vin 차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String menuId, String vin){
            this.vin = vin;
            setData(APIInfo.GRA_REQ_1001.getIfCd(), menuId);
        }
    }

    /**
     * @brief REQ_1001 응답 항목
     * @see #rsvtStusCd 정비상태코드
     * 예약상태코드	홈투홈	에어포트	오토케어	정비소
     * 예약신청	    1100	2100	3100	4100
     * 예약완료	    1200	2200	3200	4200
     * 픽업대기	    1300	2300	3300
     * 픽업중	    1400	2400	3400
     * 정비소도착	1500	2500	3500
     * 정비대기중	4600	4600	4600	4600
     * 정비진행중	4700	4700	4700	4700
     * 정비완료	    4800	4800	4800	4800
     * 딜리버리대기	6300	7300	8300
     * 딜리버리중	6400	7400	8400
     * 딜리버리완료	6500	7500	8500
     * 예약취소	    6800	7800	8800	9800
     * @see #pgrsStusCd 긴급출동상태코드
     * 진행상태 - (R:신청, -> W:접수,-> S:출동,-> E:완료, C:취소)
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("rsvtStusCd")
        private String rsvtStusCd;
        @Expose
        @SerializedName("pgrsStusCd")
        private String pgrsStusCd;
    }
}
