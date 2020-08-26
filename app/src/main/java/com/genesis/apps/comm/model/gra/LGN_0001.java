package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.CarVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @file GRA_LGN_0001
 * @Brief 대표앱 로그인시 PUSH ID 가 이미 등록되어 있고, 사용자가 변경 요청을 할 경우에 사용됨.
 * @author hjpark
 */
public class LGN_0001 extends BaseData {

    /**
     * @brief LGN_0001 요청 항목
     * @author hjpark
     * @see #appVer 앱버전
     * 설치된 앱 버전
     * @see #etrmOsDivCd 단말OS구분코드
     * A: 안드로이드  I:아이폰 : E:기타
     * @see #etrmOsVer 단말OS버전
     * @see #etrmMdlNm 단말모델명
     * @see #vin 차대번호
     *
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Request extends BaseRequest{
        @Expose
        @SerializedName("appVer")
        private String appVer;
        @Expose
        @SerializedName("etrmOsDivCd")
        private String etrmOsDivCd;
        @Expose
        @SerializedName("etrmOsVer")
        private String etrmOsVer;
        @Expose
        @SerializedName("etrmMdlNm")
        private String etrmMdlNm;
        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String appVer, String etrmOsDivCd, String etrmOsVer, String etrmMdlNm, String vin){
            this.appVer = appVer;
            this.etrmOsDivCd = etrmOsDivCd;
            this.etrmOsVer = etrmOsVer;
            this.etrmMdlNm = etrmMdlNm;
            this.vin = vin;
            setData(APIInfo.GRA_LGN_0001.getIfCd());
        }

    }

    /**
     * @brief LGN_0001 응답 항목
     * @author hjpark
     * @see #custNo 회원관리번호
     * 대표앱에서 생성한 회원 관리 번호
     * 값이 '0000' 경우는 미로그인 상태
     * @see #custGbCd 고객구분코드
     * 차량소유고객/계약한고객/차량이없는고객
     * 미로그인: 0000, OV : 소유차량고객,  CV : 차량계약고객,  NV : 미소유차량고객
     * @see #pushIdChgYn PushID 변경확인
     * Y:  기등록된 PushID가 있는 경우 변경여부를 확인 요청
     * N:  기등록된 PushID를 사용함 (등록된 Push ID와 동일)
     * @see #custMgmtNo 고객관리번호
     * CRM 발급한 고객관리번호
     * @see #custNm 성명
     * 제네시스앱에 가입한 고객의 성명
     * @see #carVO 차량정보
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("custNo")
        private String custNo;
        @Expose
        @SerializedName("custGbCd")
        private String custGbCd;
        @Expose
        @SerializedName("pushIdChgYn")
        private String pushIdChgYn;
        //TODO 고객관리번호와 성명이 차량을 따라가야하느지 확인 필요  2020-08-26 인터페이스 상으로는 차량정보에 포함됨..
        @Expose
        @SerializedName("custMgmtNo")
        private String custMgmtNo;
        @Expose
        @SerializedName("custNm")
        private String custNm;

        @Expose
        @SerializedName("carVO")
        private CarVO carVO;
    }
}
