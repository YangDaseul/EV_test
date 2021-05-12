package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.carlife.BookingDateVO;
import com.genesis.apps.comm.model.vo.carlife.OptionVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1008 extends BaseData {
    /**
     * @brief CHB_1008 요청 항목
     * @see #startDt 시작 일자  YYYYMMDD
     * @see #endDt 종료 일자    YYYYMMDD
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("startDt")
        private String startDt;
        @Expose
        @SerializedName("endDt")
        private String endDt;

        public Request(String menuId, String startDt, String endDt){
            this.startDt = startDt;
            this.endDt = endDt;
            setData(APIInfo.GRA_CHB_1008.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1008 응답 항목
     * @see #dailyBookingSlotList 일자별 리스트
     * @see #optionList 옵션 리스트
     * 세차 서비스 표시하는데 이용
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("dailyBookingSlotList")
        private List<BookingDateVO> dailyBookingSlotList;
        @Expose
        @SerializedName("optionList")
        private List<OptionVO> optionList;
    }
}
