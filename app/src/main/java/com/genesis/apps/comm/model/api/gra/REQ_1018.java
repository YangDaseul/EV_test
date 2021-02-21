package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.RepImageVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_REQ_1018
 * @Brief service + 정비소예약내역 - 수리 전중후이미지조회
 */
public class REQ_1018 extends BaseData {
    /**
     * @brief REQ_1018 요청 항목
     * @see #asnCd 정비망코드
     * @see #imgDivCd 이미지구분코드
     * @see #wrhsNo 입고번호
     * @see #vhclInoutNo 차량입출고번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("asnCd")
        private String asnCd;
        @Expose
        @SerializedName("imgDivCd")
        private String imgDivCd;
        @Expose
        @SerializedName("wrhsNo")
        private String wrhsNo;
        @Expose
        @SerializedName("vhclInoutNo")
        private String vhclInoutNo;

        public Request(String menuId, String asnCd, String imgDivCd, String wrhsNo, String vhclInoutNo){
            this.asnCd = asnCd;
            this.imgDivCd = imgDivCd;
            this.wrhsNo = wrhsNo;
            this.vhclInoutNo = vhclInoutNo;
            setData(APIInfo.GRA_REQ_1018.getIfCd(), menuId);
        }
    }

    /**
     * @brief REQ_1018 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("imgList")
        private List<RepImageVO> imgList;
    }
}
