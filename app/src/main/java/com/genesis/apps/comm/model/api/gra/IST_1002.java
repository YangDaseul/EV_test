package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.ISTAmtVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_IST_1002
 * @Brief 차계부영역
 */
public class IST_1002 extends BaseData {
    /**
     * @author hjpark
     * @brief IST_1002 요청 항목
     * @see #lgrCatAreaCd 대분류영역코드
     * 홈 : HOME, 인사이트 : INSGT
     * @see #smlCatAreaCd 소분류영역코드
     * 상단 : TOP, 하단 : BTM
     * CBK : 차계부영역,  INS-01 :인사이트1영역, INS-02 : 인사이트2영역, INS-03 :인사이트3영역
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("lgrCatAreaCd")
        private String lgrCatAreaCd;
        @Expose
        @SerializedName("smlCatAreaCd")
        private String smlCatAreaCd;
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("evCd")
        private String evCd;


        public Request(String menuId, String lgrCatAreaCd, String smlCatAreaCd, String vin, String evCd){
            this.lgrCatAreaCd = lgrCatAreaCd;
            this.smlCatAreaCd = smlCatAreaCd;
            this.vin = vin;
            this.evCd = evCd;
            setData(APIInfo.GRA_IST_1002.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief IST_1002 응답 항목
     * @see #currMthAmt 금월사용금액
     * @see #prvsMthAmt 전월사용금액  //TIP 서버 설계오류. List 형태일 필요가 없는데 List로 설계뙴
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("currMthAmt")
        private List<ISTAmtVO> currMthAmt;
        @Expose
        @SerializedName("prvsMthAmt")
        private List<ISTAmtVO> prvsMthAmt;
    }
}
