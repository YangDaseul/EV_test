package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @file GRA_CMN_0002
 * @Brief 앱에서 사용할 컨텐츠성 파일들을 압축(zip)하여 제공한다.
 * @author hjpark
 */
public class CMN_0002 extends BaseData {

    /**
     * @brief CMN_0002의 요청 항목
     * @author hjpark
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Request extends BaseRequest {
        public Request(){
            setData(APIInfo.GRA_CMN_0002.getIfCd());
        }

    }

    /**
     * @brief CMN_0002의 응답 항목
     * @author hjpark
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        //TODO 인터페이스 설계 중
    }

}
