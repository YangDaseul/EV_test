package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @file GRA_LGN_0004
 * @Brief 대표앱 로그인시 PUSH ID 가 이미 등록되어 있고, 사용자가 변경 요청을 할 경우에 사용됨.
 * @author hjpark
 */
public class LGN_0004 extends BaseData {
    /**
     * @brief LGN_0004의 요청 항목
     * @author hjpark
     * @see #termOSGbCd 단말OS구분코드
     * 단말 OS 구분 코드
     * A: 안드로이드  I:아이폰 : E:기타
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Request extends BaseRequest{
        @Expose
        @SerializedName("termOSGbCd")
        private String termOSGbCd;
        public Request(String termOSGbCd){
            this.termOSGbCd = termOSGbCd;
            setData(APIInfo.GRA_LGN_0004.getIfCd());
        }
    }

    /**
     * @brief LGN_0004의 응답 항목
     * @author hjpark
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{

    }
}
