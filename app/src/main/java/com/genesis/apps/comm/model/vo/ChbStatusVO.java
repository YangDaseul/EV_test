package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 픽업앤충전
 * @author hjpark
 * @see #prvcyInfoAgmtYn 개인정보동의여부
 * 개인정보 동의여부 (Y: 동의 N:미동의)
 * @see #scrnCntn 정보제공동의화면내용
 * 개인정보 동의 내용 화면 html 데이터
 * @see #status 서비스 상태 코드
 * 1000:예약완료
 * 2000:픽업중
 * 3000:서비스 중
 * 4000:딜리버리 중
 * 5000:이용완료
 * 6000:예약취소
 * @see #statusNm 서비스 상태명
 * 예약완료 (뺏지표시)
 * 픽업중 (뺏지표시)
 * 서비스중 (뺏지표시)
 * 딜리버리 중 (뺏지표시)
 * 이용완료 (뺏지표시 안함)
 * 예약취소  (뺏지표시 안함)
 * @see #stMbrYn 에스트래픽 회원 여부
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ChbStatusVO extends BaseData {
    @Expose
    @SerializedName("prvcyInfoAgmtYn")
    private String prvcyInfoAgmtYn;
    @Expose
    @SerializedName("scrnCntn")
    private String scrnCntn;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("statusNm")
    private String statusNm;
    @Expose
    @SerializedName("stMbrYn")
    private String stMbrYn;
}
