package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_RMT_1001
 * @Brief 원격진단신청가능여부
 */
public class RMT_1001 extends BaseData {
    /**
     * @brief RMT_1001 요청 항목
     * @see #vin 차대번호
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
            setData(APIInfo.GRA_RMT_1001.getIfCd(), menuId);
        }
    }

    /**
     * @brief RMT_1001 응답 항목
     * @see #rmtExitYn 원격신청존재여부
     * Y: 신청건이 있음
     * N: 신청건이 없음
     * ※ 차대번호 기준 앱 원격진단 신청건 + 전화 원격진단 신청건 통합 체크 후 최근 신청건 정보 리턴 (진행중인 건만)
     *    1) 최근 신청 건이 <앱 원격진단 신청> 인 경우    ==> EXIST_YN : Y /
     *                                        가 접수 번호 : 존재    / 접수번호 : 존재 or 미존재
     *    2) 최근 신청 건이 <전화 원격진단 신청> 인 경우 ==> EXIST_YN : Y /
     *                                        가 접수 번호 : 미존재 / 접수번호 : 존재
     * @see #carRgstNo 차량번호
     * 차량정보에 차량번호가 있는 경우 설정
     * @see #celphNo 휴대전화번호
     * 고객정보의 휴대폰전화번호
     * @see #sosStusCd 긴급출동상태코드
     * 긴급출동 신청했고 진행 중인 경우
     *  - R:신청, W:접수, S:출동  (E:완료, C:취소 인경우 설정하지 않음)
     * @see #tmpAcptNo 가접수번호
     * 긴급출동진행상태가 ( R:신청, W:접수, S:출동) 인 경우 설정
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("rmtExitYn")
        private String rmtExitYn;
        @Expose
        @SerializedName("carRgstNo")
        private String carRgstNo;
        @Expose
        @SerializedName("celphNo")
        private String celphNo;
        @Expose
        @SerializedName("sosStusCd")
        private String sosStusCd;
        @Expose
        @SerializedName("tmpAcptNo")
        private String tmpAcptNo;

    }
}
