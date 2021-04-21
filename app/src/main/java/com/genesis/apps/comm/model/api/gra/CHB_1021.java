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

public class CHB_1021 extends BaseData {
    /**
     * @brief CHB_1021 요청 항목
     * @see #vin        차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String menuId, String vin){
            this.vin = vin;
            setData(APIInfo.GRA_CHB_1021.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1021 응답 항목
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
        @SerializedName("bookingDt")
        private String bookingDt;
        @Expose
        @SerializedName("cancelableYN")
        private String cancelableYN;
        @Expose
        @SerializedName("keyTransferType")
        private String keyTransferType;
        @Expose
        @SerializedName("workerAssignYN")
        private String workerAssignYN;
        @Expose
        @SerializedName("carInfo")
        private CarVO carInfo;
        @Expose
        @SerializedName("sameLocationYN")
        private String sameLocationYN;
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
        @SerializedName("paymentInfo")
        private PaymtCardVO paymentInfo;
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
