package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.TermVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @file GRA_CMN_0003
 * @Brief 대표앱 서비스 가입시 노출되는 약관 목록 조회
 * @author hjpark
 */
public class CMN_0003 extends BaseData {
    /**
     * @brief CMN_0003의 요청 항목
     * @author hjpark
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {
        public Request(String menuId){
            setData(APIInfo.GRA_CMN_0003.getIfCd(), menuId);
        }
    }

    /**
     * @brief CMN_0003의 응답 항목
     * @author hjpark
     * @see #termList 제네시스 앱 약관리스트
     * @see #blueTermList 블루멤버스 약관리스트
     * @see #mypgTermList 마이제네시스 약관리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("termList")
        private List<TermVO> termList;

        @Expose
        @SerializedName("blueTermList")
        private List<TermVO> blueTermList;

        @Expose
        @SerializedName("mypgTermList")
        private List<TermVO> mypgTermList;
    }

}
