package com.genesis.apps.comm.model.constants;

public interface StoreInfo {
    String[] STORE_LIST_INFO = {"http://devagenesisproduct.auton.kr/ko/main", "https://devagenesisproduct.auton.kr/ko/main"};
    String[] STORE_SEARCH_INFO = {"http://devagenesisproduct.auton.kr/gen/ko/search", "https://devagenesisproduct.auton.kr/gen/ko/search"};
    String[] STORE_CART_INFO = {"http://devagenesisproduct.auton.kr/cart/gen/ko/cart_list", "https://devagenesisproduct.auton.kr/cart/gen/ko/cart_list"};
    String[] STORE_PURCHASE_INFO = {"http://devagenesisproduct.auton.kr/mypage/gen/ko/purchase/list", "https://devagenesisproduct.auton.kr/mypage/gen/ko/purchase/list"};
    String STORE_LIST_URL = STORE_LIST_INFO[GAInfo.SERVER_TYPE];
    String STORE_SEARCH_URL = STORE_SEARCH_INFO[GAInfo.SERVER_TYPE];
    String STORE_CART_URL = STORE_CART_INFO[GAInfo.SERVER_TYPE];
    String STORE_PURCHASE_URL = STORE_PURCHASE_INFO[GAInfo.SERVER_TYPE];
}
