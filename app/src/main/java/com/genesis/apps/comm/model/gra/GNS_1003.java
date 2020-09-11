package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_GNS_1003
 * @Brief My차고 + 차량삭제
 */
public class GNS_1003 extends BaseData {
    public static final String DEL_RSN_SELL="!";//매각
    public static final String DEL_RSN_SCRAPPED="2";//폐차
    public static final String DEL_RSN_DEVOLVE="4";//양도
    public static final String DEL_RSN_ETC="7";//기타

    /**
     * @brief GNS_1003 요청 항목
     * @see #vin 차대번호
     * @see #delRsnCd 삭제 사유 코드
     * 1: 매각  2: 폐차  4: 양도 7: 기타
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{

        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("delRsnCd")
        private String delRsnCd;

        public Request(String menuId, String vin, String delRsnCd){
            this.vin = vin;
            this.delRsnCd = delRsnCd;
            setData(APIInfo.GRA_GNS_1003.getIfCd(),menuId);
        }
    }

    /**
     * @brief GNS_1003 응답 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
    }
}
