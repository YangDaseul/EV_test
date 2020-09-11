package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.ContentsDetailVO;
import com.genesis.apps.comm.model.vo.ContentsVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_CTT_1004
 * @Brief contents + 컨텐츠조회
 */
public class CTT_1004 extends BaseData {
    /**
     * @author hjpark
     * @brief CTT_1004 요청 항목
     * @see #listSeqNo 목록일련번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        @Expose
        @SerializedName("listSeqNo")
        private String listSeqNo;

        public Request(String menuId, String listSeqNo){
            this.listSeqNo = listSeqNo;
            setData(APIInfo.GRA_CTT_1004.getIfCd(),menuId);
        }
    }

    /**
     * @author hjpark
     * @brief CTT_1004 응답 항목
     *
     * @see #listSeqNo 목록일련번호
     * @see #ttl 제목
     * @see #readCnt 읽음수
     * @see #starCnt 평점(별수)
     * '= 평가별수합 / 평가고객수 (소수점절삭)
     * @see #dtlViewCd 상세뷰코드
     * 1000: 통이미지  2000: 이미지+텍스트  3000: HTML
     * @see #evalYn 평가여부
     * Y: 평가 가능 컨텐츠
     * N: 평가 없는 컨텐츠
     * @see #evalQst 평가질문서
     * 평가시 질의문
     * @see #dtlList 상세리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{


        @Expose
        @SerializedName("listSeqNo")
        private String listSeqNo;
        @Expose
        @SerializedName("ttl")
        private String ttl;
        @Expose
        @SerializedName("readCnt")
        private String readCnt;
        @Expose
        @SerializedName("starCnt")
        private String starCnt;
        @Expose
        @SerializedName("dtlViewCd")
        private String dtlViewCd;
        @Expose
        @SerializedName("evalYn")
        private String evalYn;
        @Expose
        @SerializedName("evalQst")
        private String evalQst;
        @Expose
        @SerializedName("dtlList")
        private List<ContentsDetailVO> dtlList;
    }
}
