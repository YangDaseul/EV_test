package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.CCSVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.PackageUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    static
    class Request extends BaseRequest {
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

        public Request(String menuId, String appVer, String vin){
            this.appVer = PackageUtil.changeVersionToServerFormat(appVer);
            this.etrmOsDivCd = VariableType.OS_DIVICE_CODE;
            this.etrmOsVer = DeviceUtil.getAndroidOS();
            this.etrmMdlNm = DeviceUtil.getModel();
            this.vin = vin;
            setData(APIInfo.GRA_LGN_0001.getIfCd(), menuId);
//            if(!TextUtils.isEmpty(custGbCd)&& //파라미터 값이 유효하고
//                    (getCustGbCd().equalsIgnoreCase("0000")||(getCustGbCd().equalsIgnoreCase("")))){ //UserVO DB의 값이 0000혹은 빈값일 때
//                setCustGbCd(custGbCd); //파라미터 값 사용 (LGN-0001에서는 엑세스 토큰이 있을때 nv로 요청해야함)
//            }
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
     * @see #celphNo 핸드폰번호
     * 제네시스앱 가입시 등록된 전화번호
     * @see #ownVhclList 소유 차량 정보
     * @see #ctrctVhclList 계약 차량 정보
     * @see #dftVhclInfo 기본 차량 정보
     * @see #ccsUserInfo ccsp 사용자 정보
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("custNo")
        private String custNo;
        @Expose
        @SerializedName("custGbCd")
        private String custGbCd;
        @Expose
        @SerializedName("pushIdChgYn")
        private String pushIdChgYn;


        @Expose
        @SerializedName("custMgmtNo")
        private String custMgmtNo;
        @Expose
        @SerializedName("custNm")
        private String custNm;

        @Expose
        @SerializedName("celphNo")
        private String celphNo;

        @Expose
        @SerializedName("ownVhclList")
        private List<VehicleVO> ownVhclList;
        @Expose
        @SerializedName("ctrctVhclList")
        private List<VehicleVO> ctrctVhclList;
        @Expose
        @SerializedName("dftVhclInfo")
        private VehicleVO dftVhclInfo;
        @Expose
        @SerializedName("ccsUserInfo")
        private CCSVO ccsUserInfo;


    }
}
