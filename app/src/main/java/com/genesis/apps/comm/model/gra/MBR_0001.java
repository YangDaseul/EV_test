package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.AgreeMeansVO;
import com.genesis.apps.comm.model.vo.AgreeTermVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @file GRA_MBR_0001
 * @Brief 서비스 가입 요청
 * @author hjpark
 */
public class MBR_0001 extends BaseData {
    /**
     * @brief MBR_0001의 요청 항목
     * @author hjpark
     * @see #tokenCode 토큰코드
     * GA 회원 가입 요청 api(GA-COM-030) 호출 할때 사용하는 값.
     * @see #authUuid 본인인증UUID
     * GA 회원 가입 요청 api(GA-COM-030) 호출 할때 사용하는 값.
     * @see #terms 약관정보
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest{
        @Expose
        @SerializedName("tokenCode")
        private String tokenCode;
        @Expose
        @SerializedName("authUuid")
        private String authUuid;
        @Expose
        @SerializedName("terms")
        private List<AgreeTermVO> terms;

        public Request(String menuId, String tokenCode, String authUuid, List<AgreeTermVO> terms){
            this.tokenCode = tokenCode;
            this.authUuid = authUuid;
            this.terms = terms;

            setData(APIInfo.GRA_MBR_0001.getIfCd(), menuId);
        }
    }

    /**
     * @brief MBR_0001의 응답 항목
     * @author hjpark
     * @see #custNo 결과메세지
     * 대표앱에서 생성한 회원 관리 번호(가입 정상 처리 시 필수)
     * @see #custGbCd 결과메세지
     * 차량소유고객/계약한고객/차량이없는고객
     * 미로그인: 0000, OV : 소유차량고객,  CV : 차량계약고객,  NV : 미소유차량고객
     * @see #ownVhclList 소유 차량 정보
     * @see #ctrctVhclList 계약 차량 정보
     * @see #dftVhclInfo 기본 차량 정보
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
        @SerializedName("ownVhclList")
        private List<VehicleVO> ownVhclList;
        @Expose
        @SerializedName("ctrctVhclList")
        private List<VehicleVO> ctrctVhclList;
        @Expose
        @SerializedName("dftVhclInfo")
        private List<VehicleVO> dftVhclInfo;
    }

}
