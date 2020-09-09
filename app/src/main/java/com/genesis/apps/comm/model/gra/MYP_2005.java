package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.MembershipPointVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_MYP_2002
 * @Brief MyG+ 멤버쉽 포인트 사용내역
 */
public class MYP_2005 extends BaseData {
    /**
     * @author hjpark
     * @brief MYP_2002 요청 항목
     * @see #currPwd 현재비밀번호
     * @see #newPwd 신규비밀번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        @Expose
        @SerializedName("currPwd")
        private String currPwd;
        @Expose
        @SerializedName("newPwd")
        private String newPwd;


        public Request(String currPwd, String newPwd){
            this.currPwd = currPwd;
            this.newPwd = newPwd;
            setData(APIInfo.GRA_MYP_2005.getIfCd());
        }
    }

    /**
     * @author hjpark
     * @brief MYP_2002 응답 항목
     * @see #blueMbrYn 블루멤버스 회원 여부
     * 회원 : Y, 비회원 : N
     * @see #pwdChgRslt 비밀번호변경 처리 결과
     * Y : 성공 / N : 실패
     * @see #errMsg 오류메시지
     * 블루멤버스에 받은 오류메시지
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("blueMbrYn")
        private String blueMbrYn;
        @Expose
        @SerializedName("pwdChgRslt")
        private String pwdChgRslt;
        @Expose
        @SerializedName("errMsg")
        private String errMsg;
    }
}
