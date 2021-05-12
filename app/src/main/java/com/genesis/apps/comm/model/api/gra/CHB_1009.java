package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.carlife.BlueInfoVO;
import com.genesis.apps.comm.model.vo.carlife.LotVO;
import com.genesis.apps.comm.model.vo.carlife.OptionVO;
import com.genesis.apps.comm.model.vo.carlife.PaymtCardVO;
import com.genesis.apps.comm.model.vo.carlife.StrafficInfoVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1009 extends BaseData {
    /**
     * @brief CHB_1009 요청 항목
     * @see #vin        차대번호
     * @see #carCode    차종코드
     * @see #bookingDtm 예약일시    YYYYMMDDHH24MISS
     * @see #locationInfo 위치 정
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("carCode")
        private String carCode;
        @Expose
        @SerializedName("bookingDtm")
        private String bookingDtm;
        @Expose
        @SerializedName("locationInfo")
        private LotVO locationInfo;

        public Request(String menuId, String vin, String carCode, String bookingDtm, LotVO locationInfo){
            this.vin = vin;
            this.carCode = carCode;
            this.bookingDtm = bookingDtm;
            this.locationInfo = locationInfo;
            setData(APIInfo.GRA_CHB_1009.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1009 응답 항목
     * @see #txid           트랜잭션ID(주문ID)
     * @see #expireDtm      만료시간    YYYYMMDDHH24MISS
     * @see #stMbrYn        에스트래픽 회원 여부
     * Y:가입회원 N:미회원
     *
     * @see #productCode    상품 코드
     * @see #productName    상품명
     * @see #productPrice   상품금액
     * @see #optionList     옵션 리스트
     * @see #strafficInfo   에스트래픽 선불 정보
     * @see #blueInfo       블루멤버스 정보
     * @see #signInYN       간편결제 회원 가입 여부
     * Y:가입, N:미가입
     *
     * @see #cardList       간편 결제 수단 리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("txid")
        private String txid;
        @Expose
        @SerializedName("expireDtm")
        private String expireDtm;
        @Expose
        @SerializedName("stMbrYn")
        private String stMbrYn;
        @Expose
        @SerializedName("productCode")
        private String productCode;
        @Expose
        @SerializedName("productName")
        private String productName;
        @Expose
        @SerializedName("productPrice")
        private int productPrice;
        @Expose
        @SerializedName("optionList")
        private List<OptionVO> optionList;
        @Expose
        @SerializedName("strafficInfo")
        private StrafficInfoVO strafficInfo;
        @Expose
        @SerializedName("blueInfo")
        private BlueInfoVO blueInfo;
        @Expose
        @SerializedName("signInYN")
        private String signInYN;
        @Expose
        @SerializedName("cardList")
        private List<PaymtCardVO> cardList;
    }
}
