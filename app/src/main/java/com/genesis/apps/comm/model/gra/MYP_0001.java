package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_MYP_0001
 * @Brief 사용자 정보를 조회한다.
 */
public class MYP_0001 extends BaseData {
    /**
     * @author hjpark
     * @brief MYP_0001 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {
        public Request(String menuId){
            setData(APIInfo.GRA_MYP_0001.getIfCd(),menuId);
        }
    }

    /**
     * @author hjpark
     * @brief MYP_0001 응답 항목
     * @see #mbrStustCd 회원상태코드
     * 0000: 미가입
     * 1000: 신규가입-대표앱신규
     * 2000: 휴면 - ccsp 휴면고객
     * 3000: 중지 - ccsp 중지고객
     * 9000: 서비스해제 - 대표앱서비스 탈퇴
     * 9009: 회원탈퇴 - ccSP 회원탈퇴
     * @see #ccspEmail 이메일
     * ccsp 회원가입 이메일
     * @see #mbrNm 회원성명
     * @see #brtdy 생년월일
     * @see #sexDiv 성별
     * F : 여성 M : 남성 E :기타
     * @see #celphNo 휴대전화번호
     * @see #mrktYn 광고수신동의여부
     * Y:  동의  N:미동의
     * @see #mrktDate 광고수신동의일시
     * 수신동의 한 경우 필수
     * yyyyMMddHHmmss
     * @see #mrktCd 광고수신채널코드
     * 수신동의 한 경우 필수
     * 형식 :  1 -> 동의채널  0-> 미동의채널
     * SMS(1) + 이메일(1) + 우편(1) + 전화(1)
     * ex) 1010 : SMS와 우편은 동의했지만 그 외는 미동의
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("mbrStustCd")
        private String mbrStustCd;
        @Expose
        @SerializedName("ccspEmail")
        private String ccspEmail;
        @Expose
        @SerializedName("mbrNm")
        private String mbrNm;
        @Expose
        @SerializedName("brtdy")
        private String brtdy;
        @Expose
        @SerializedName("sexDiv")
        private String sexDiv;
        @Expose
        @SerializedName("celphNo")
        private String celphNo;
        @Expose
        @SerializedName("mrktYn")
        private String mrktYn;
        @Expose
        @SerializedName("mrktDate")
        private String mrktDate;
        @Expose
        @SerializedName("mrktCd")
        private String mrktCd;
    }
}
