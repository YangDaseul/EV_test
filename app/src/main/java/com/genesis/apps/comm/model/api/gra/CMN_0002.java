package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.AlarmMsgTypeVO;
import com.genesis.apps.comm.model.vo.CatTypeVO;
import com.genesis.apps.comm.model.vo.DownMenuVO;
import com.genesis.apps.comm.model.vo.QuickMenuVO;
import com.genesis.apps.comm.model.vo.WeatherVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    static class Request extends BaseRequest {
        public Request(String menuId){
            setData(APIInfo.GRA_CMN_0002.getIfCd(), menuId);
        }

    }

    /**
     * @brief CMN_0002의 응답 항목
     * @author hjpark
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("menu0000")
        private MenuInfo menu0000;
        @Expose
        @SerializedName("menuNV")
        private MenuInfo menuNV;
        @Expose
        @SerializedName("menuOV")
        private MenuInfo menuOV;
        @Expose
        @SerializedName("menuCV")
        private MenuInfo menuCV;
        @Expose
        @SerializedName("wthrInsgtList")
        private List<WeatherVO> wthrInsgtList;
        @Expose
        @SerializedName("msgTypCd")
        private List<AlarmMsgTypeVO> msgTypCd;
        @Expose
        @SerializedName("catTypCd")
        private List<CatTypeVO> catTypCd;
    }


    public @Data
    class MenuInfo{
        @Expose
        @SerializedName("qckMenuList")
        private List<QuickMenuVO> qckMenuList;
        @Expose
        @SerializedName("downMenuList")
        private List<DownMenuVO> downMenuList;
    }

}
