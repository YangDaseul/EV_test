package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.MessageVO;
import com.genesis.apps.comm.model.vo.SOSDriverVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_IST_1005
 * @Brief 인사이트 – 인사이트 3 영역
 */
public class IST_1005 extends BaseData {
    /**
     * @author hjpark
     * @brief IST_1005 요청 항목
     * @see #lgrCatAreaCd 대분류영역코드
     * 홈 : HOME, 인사이트 : INSGT
     * @see #smlCatAreaCd 소분류영역코드
     * 상단 : TOP, 하단 : BTM
     * CBK : 차계부영역,  INS-01 :인사이트1영역, INS-02 : 인사이트2영역, INS-03 :인사이트3영역
     * @see #addrX 고객위치주소 x좌표
     * @see #addrY 고객위치주소 y좌표
     * @see #vin 차대번호
     * @see #mdlNm 차량모델명
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        @Expose
        @SerializedName("lgrCatAreaCd")
        private String lgrCatAreaCd;
        @Expose
        @SerializedName("smlCatAreaCd")
        private String smlCatAreaCd;
        @Expose
        @SerializedName("addrX")
        private String addrX;
        @Expose
        @SerializedName("addrY")
        private String addrY;
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("mdlNm")
        private String mdlNm;

        public Request(String menuId, String lgrCatAreaCd, String smlCatAreaCd, String addrX, String addrY, String vin, String mdlNm){
            this.lgrCatAreaCd = lgrCatAreaCd;
            this.smlCatAreaCd = smlCatAreaCd;
            this.addrX = addrX;
            this.addrY = addrY;
            this.vin = vin;
            this.mdlNm = mdlNm;
            setData(APIInfo.GRA_IST_1005.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief IST_1005 응답 항목
     * @see #msgList 메시지리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("msgList")
        private List<MessageVO> msgList;
    }
}
