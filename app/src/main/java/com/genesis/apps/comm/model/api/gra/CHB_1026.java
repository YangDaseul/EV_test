package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.carlife.CarVO;
import com.genesis.apps.comm.model.vo.carlife.LotVO;
import com.genesis.apps.comm.model.vo.carlife.OrderVO;
import com.genesis.apps.comm.model.vo.carlife.PaymtCardVO;
import com.genesis.apps.comm.model.vo.carlife.VendorVO;
import com.genesis.apps.comm.model.vo.carlife.WorkerVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1026 extends BaseData {
    /**
     * @brief CHB_1026 요청 항목
     * @see #orderId    주문ID
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("orderId")
        private String orderId;

        public Request(String menuId, String orderId) {
            this.orderId = orderId;
            setData(APIInfo.GRA_CHB_1026.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1026 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("orderId")
        private String orderId;
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("statusNm")
        private String statusNm;
        @Expose
        @SerializedName("requestDt")
        private String requestDt;
        @Expose
        @SerializedName("finishDt")
        private String finishDt;
        @Expose
        @SerializedName("carInfo")
        private CarVO carInfo;
        @Expose
        @SerializedName("locationCount")
        private int locationCount;
        @Expose
        @SerializedName("locationList")
        private List<LotVO> locationList;
        @Expose
        @SerializedName("orderInfo")
        private OrderVO orderInfo;
        @Expose
        @SerializedName("workerCount")
        private int workerCount;
        @Expose
        @SerializedName("workerList")
        private List<WorkerVO> workerList;
        @Expose
        @SerializedName("vendorInfo")
        private VendorVO vendorInfo;
    }
}
