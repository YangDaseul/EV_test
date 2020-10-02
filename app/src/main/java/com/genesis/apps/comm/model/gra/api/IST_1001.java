package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.MessageVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_IST_1001
 * @Brief 홈상단 영역
 */
public class IST_1001 extends BaseData {
    /**
     * @author hjpark
     * @brief IST_1001 요청 항목
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

        public Request(String menuId, String lgrCatAreaCd, String smlCatAreaCd){
            this.lgrCatAreaCd = lgrCatAreaCd;
            this.smlCatAreaCd = smlCatAreaCd;
            setData(APIInfo.GRA_IST_1001.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief IST_1001 응답 항목
     * @see #admMsgList 어드민메시지리스트
     * @see #bnrMsgList 배너메시지리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("admMsgList")
        private List<MessageVO> admMsgList;
        @Expose
        @SerializedName("bnrMsgList")
        private List<MessageVO> bnrMsgList;
    }
}
