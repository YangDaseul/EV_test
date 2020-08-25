package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.TermVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_MYP_8001
 * @Brief MyG+ 이용약관
 */
public class MYP_8001 extends BaseData {
    /**
     * @author hjpark
     * @brief MYP_8001 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Request extends BaseRequest{
        public Request(){
            setData(APIInfo.GRA_MYP_8001.getIfCd());
        }
    }

    /**
     * @author hjpark
     * @brief MYP_8001 응답 항목
     * @see #termVO 약관정보
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("termVO")
        private TermVO termVO;
    }
}
