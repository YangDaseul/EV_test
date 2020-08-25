package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.NotiInfoVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_NOT_0001
 * @Brief 알림센터 알림목록 요청
 */
public class NOT_0001 extends BaseData {
    /**
     * @author hjpark
     * @brief NOT_0001 요청 항목
     * @see #lastNotiNo 마지막 일련번호
     * 알림목록 조회 요청한 마지막 일련번호
     * 0 값이면 1page, 즉 최근목록 요청 의미
     * @see #searchCnt 조회 건수
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Request extends BaseRequest{
        @Expose
        @SerializedName("lastNotiNo")
        private String lastNotiNo;
        @Expose
        @SerializedName("searchCnt")
        private String searchCnt;

        public Request(String lastNotiNo, String searchCnt){
            this.lastNotiNo = lastNotiNo;
            this.searchCnt = searchCnt;
            setData(APIInfo.GRA_NOT_0001.getIfCd());
        }
    }

    /**
     * @author hjpark
     * @brief NOT_0001 응답 항목
     * @see #notiInfoList 알림정보 리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("notiInfoList")
        private List<NotiInfoVO> notiInfoList;
    }
}
