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
// * @file GRA_MYP_8003
// * @Brief MyG+ 오픈소스라이센스
// */
//public class MYP_8003 extends BaseData {
//    /**
//     * @brief MYP_8003 요청 항목
//     */
//    @EqualsAndHashCode(callSuper = true)
//    public @Data
//    static
//    class Request extends BaseRequest{
//        public Request(){
//            setData(APIInfo.GRA_MYP_8003.getIfCd());
//        }
//    }
//
//    /**
//     * @brief MYP_8003 응답 항목
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
