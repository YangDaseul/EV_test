package com.genesis.apps.comm.model.vo.carlife;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 픽업앤충전 신청 이력 정보 VO
 * @author ljeun
 * @since 2021. 4. 15.
 *
 * @see #orderId    주문 ID
 * @see #category   서비스 카테고리
 * DRIVER: 대리
 * DELIVERY: 딜리버리(탁송)
 * PUCHRG: 픽업앤충전
 * PUWASH: 픽업앤세차
 * VSWASH: 방문세차
 *
 * @see #status 서비스 상태 코드
 * 1000 : 예약완료
 * 2000 : 픽업중
 * 3000 : 서비스중
 * 4000 : 딜리버리중
 * 5000 : 이용완료
 * 6000 : 예약취소
 *
 * @see #statusNm   서비스 상태명
 * @see #bookingDt  서비스 예약 시간
 * @see #finishDt   서비스 완료/취소 시간
 * @see #carNo  차량 번호
 * @see #address    주소
 * @see #addressDetail  상세주소
 * @see #buildingName   빌딩명
 * @see #productName    상품명
 * @see #optionCount    옵션 리스트 갯수
 * @see #optionNameList 옵션 상품명 리스트
 * @see #serviceViewLink    차량 상태 확인 링크
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class BookingVO extends BaseData {
    @Expose
    @SerializedName("orderId")
    private String orderId;
    @Expose
    @SerializedName("category")
    private String category;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("statusNm")
    private String statusNm;
    @Expose
    @SerializedName("bookingDt")
    private String bookingDt;
    @Expose
    @SerializedName("finishDt")
    private String finishDt;
    @Expose
    @SerializedName("carNo")
    private String carNo;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("addressDetail")
    private String addressDetail;
    @Expose
    @SerializedName("buildingName")
    private String buildingName;
    @Expose
    @SerializedName("productName")
    private String productName;
    @Expose
    @SerializedName("optionCount")
    private int optionCount;
    @Expose
    @SerializedName("optionNameList")
    private List<String> optionNameList;
    @Expose
    @SerializedName("serviceViewLink")
    private String serviceViewLink;

    public BookingVO() {
        this.orderId = "";
    }

    private WorkerVO workerVO;
    private OrderVO orderVO;
}
