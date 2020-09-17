package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.RepairReserveVO;
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
    class Request extends BaseRequest{
        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String menuId, String vin){
            this.vin = vin;
            setData(APIInfo.GRA_REQ_1013.getIfCd(), menuId);
        }
    }

    /**
     * @brief REQ_1013 응답 항목
     * @see #rsvtStatList 예약현황리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("rsvtStatList")
        private List<ReserveList> rsvtStatList;
    }


    @EqualsAndHashCode(callSuper = false)
    public @Data
    class ReserveList extends BaseData{
        @Expose
        @SerializedName("autoRsvtStat")
        private RepairReserveVO autoRsvtStat;
        @Expose
        @SerializedName("hthRsvtStat")
        private RepairReserveVO hthRsvtStat;
        @Expose
        @SerializedName("rpshRsvtStat")
        private RepairReserveVO rpshRsvtStat;
    }
}
