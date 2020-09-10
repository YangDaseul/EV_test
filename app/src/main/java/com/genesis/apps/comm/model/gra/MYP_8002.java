//package com.genesis.apps.comm.model.gra;
//
//import com.genesis.apps.comm.model.BaseData;
//import com.genesis.apps.comm.model.vo.TermVO;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
///**
// * @author hjpark
// * @file GRA_MYP_8002
// * @Brief MyG+ 개인정보처리방침
// */
//public class MYP_8002 extends BaseData {
//    /**
//     * @brief MYP_8002 요청 항목
//     */
//    @EqualsAndHashCode(callSuper = true)
//    public @Data
//    static
//    class Request extends BaseRequest{
//        public Request(){
//            setData(APIInfo.GRA_MYP_8002.getIfCd());
//        }
//    }
//
//    /**
//     * @brief MYP_8002 응답 항목
//     * @see #termVO 약관정보
//     */
//    @EqualsAndHashCode(callSuper = true)
//    public @Data
//    class Response extends BaseResponse{
//        @Expose
//        @SerializedName("termVO")
//        private TermVO termVO;
//    }
//}
