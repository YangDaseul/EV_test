package com.genesis.apps.comm.model.constants;

public interface StoreInfo {
//    String[] STORE_LIST_INFO = {"http://devagenesisproduct.auton.kr/ko/main", "https://agenesisproduct.auton.kr/ko/main"};
//    String[] STORE_SEARCH_INFO = {"http://devagenesisproduct.auton.kr/search/gen/ko/list", "https://agenesisproduct.auton.kr/search/gen/ko/list"};
//    String[] STORE_CART_INFO = {"http://devagenesisproduct.auton.kr/cart/gen/ko/cart_list", "https://agenesisproduct.auton.kr/cart/gen/ko/cart_list"};
//    String[] STORE_PURCHASE_INFO = {"http://devagenesisproduct.auton.kr/mypage/gen/ko/purchase/list", "https://agenesisproduct.auton.kr/mypage/gen/ko/purchase/list"};
    String[] STORE_LIST_INFO = {"https://agenesisproduct.auton.kr/ko/main", "https://agenesisproduct.auton.kr/ko/main"};
    String[] STORE_SEARCH_INFO = {"https://agenesisproduct.auton.kr/search/gen/ko/list", "https://agenesisproduct.auton.kr/search/gen/ko/list"};
    String[] STORE_CART_INFO = {"https://agenesisproduct.auton.kr/cart/gen/ko/cart_list", "https://agenesisproduct.auton.kr/cart/gen/ko/cart_list"};
    String[] STORE_PURCHASE_INFO = {"https://agenesisproduct.auton.kr/mypage/gen/ko/purchase/list", "https://agenesisproduct.auton.kr/mypage/gen/ko/purchase/list"};
    String STORE_LIST_URL = STORE_LIST_INFO[GAInfo.SERVER_TYPE];
    String STORE_SEARCH_URL = STORE_SEARCH_INFO[GAInfo.SERVER_TYPE];
    String STORE_CART_URL = STORE_CART_INFO[GAInfo.SERVER_TYPE];
    String STORE_PURCHASE_URL = STORE_PURCHASE_INFO[GAInfo.SERVER_TYPE];
}
