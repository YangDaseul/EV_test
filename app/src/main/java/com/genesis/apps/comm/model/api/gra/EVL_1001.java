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
 * @file GRA_EVL_1001
 * @Brief service + 소낙스 예약취소
 * @author hjpark
 */
public class EVL_1001 extends BaseData {
    /**
     * @brief EVL_1001의 요청 항목
     * @author hjpark
     * @see #evlScnUri 평가화면 .URI
     * 알람 메시지로 전달받은 대리/세차 평가 URI
     * 대리 : genesisapp://menu?id=SM_REVIEW01_P03&PI=123456
     * 세차 : genesisapp://menu?id=SM_REVIEW01_P01&PI=456789
     * 대리와 세차는 고정된 id 값으로 구분
     * 평가지 구분은 PI 일련번호로 구분
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {
        @Expose
        @SerializedName("evlScnUri")
        private String evlScnUri;

        public Request(String menuId, String evlScnUri){
            this.evlScnUri = evlScnUri;
            setData(APIInfo.GRA_EVL_1001.getIfCd(), menuId);
        }
    }

    /**
     * @brief EVL_1001의 응답 항목
     * @author hjpark
     * @see #evlFinYn Y:  평가완료, N: 평가전
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("evlFinYn")
        private String evlFinYn;
    }
}
